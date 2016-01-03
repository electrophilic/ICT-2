package com.laborbond.user;


import com.laborbond.company.ComRowMapperComInfo;
import com.laborbond.employee.EmRowMapperEmExp;
import com.laborbond.employee.EmRowMapperEmInfo;
import com.laborbond.job.Job;
import com.laborbond.company.ComInfo;
import com.laborbond.employee.EmExp;
import com.laborbond.employee.EmInfo;
import static java.sql.JDBCType.NULL;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Fu
 */
public class UserServiceImpl implements UserService{
    
    @Autowired
    public DataSource dataSource;
    
    public String checkEmail(String email){
        List<User> userListCom = new ArrayList<User>();
        List<User> userListEm = new ArrayList<User>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        String sql = "";
        sql = "select * from employee_id where em_email= ?";  
        userListEm = jdbcTemplate.query(sql,new Object[]{email}, new UserRowMapperEmId());
        sql = "select * from company_id where com_email= ?";  
        userListCom = jdbcTemplate.query(sql,new Object[]{email}, new UserRowMapperComId());
        
        if (userListEm.isEmpty()) {
        } else {
            return "employee";
        }
        if (userListCom.isEmpty()) {

        } else {
            return "company";
        }
        return "free";
    }

    @Override
    public boolean register(User user, String password) {
        if(checkEmail(user.getEmail()).equals("free")){
            String userId , userInfo;
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            if(user.getType().equals("company")){
                userId = "INSERT INTO `company_id` (`com_email`, `com_password`) VALUES(?,PASSWORD(?))";
                userInfo = "INSERT INTO `company_info` (`com_name`,`com_u_fname`, `com_u_lname`) VALUES(?,?,?)";
                jdbcTemplate.update(userId, new Object[] { user.email,password});
                jdbcTemplate.update(userInfo, new Object[] { user.comname, user.fname,user.lname});
            } else{
                userId = "INSERT INTO `employee_id` (`em_email`, `em_password`) VALUES(?,PASSWORD(?))";
                userInfo = "INSERT INTO `employee_info` (`em_fname`, `em_lname`) VALUES(?,?)";
                jdbcTemplate.update(userId, new Object[] { user.email,password});
                jdbcTemplate.update(userInfo, new Object[] { user.fname,user.lname});
            }

            return true;
        }else return false;
    }

    @Override
    public User login(User user,String password) {
        String sql = "";
        String type = checkEmail(user.email);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
        User a=null;
        if(type.equals("company")){

            sql = "SELECT * FROM  `company_id` WHERE `com_email` = ? AND `com_password` = PASSWORD(?)";
            
            try{
            a = (User)jdbcTemplate.queryForObject(sql,new Object[]{user.email,password}, new UserRowMapperComId());   
            }catch(EmptyResultDataAccessException e){
                return null;
            }
            if (a.isValid()){
            return a;
            }
        }
        if(type.equals("employee")){
            sql = "SELECT * FROM  `employee_id` WHERE `em_email` =  ? AND `em_password` = PASSWORD(?)";
            try{
            a = (User) jdbcTemplate.queryForObject(sql,new Object[]{user.email,password}, new UserRowMapperEmId());
            }catch(EmptyResultDataAccessException e){
                return null;
            }
            if (a.isValid()){
            return a;
            }
        }

        
        return null;
    }

    @Override
    public User getUser(int id, String type) {
        String sql = "";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
        if(type.equals("company")){

            sql = "SELECT * FROM  `company_id` WHERE `com_id` = ? ";
            User a = (User)jdbcTemplate.queryForObject(sql,new Object[]{id}, new UserRowMapperComId());   
            return a;
        }else{
            sql = "SELECT * FROM  `employee_id` WHERE `em_id` =  ? ";
            User a = (User) jdbcTemplate.queryForObject(sql,new Object[]{id}, new UserRowMapperEmId());
            return a;
        }
     
    }
    
    @Override
    public boolean updateEmail(int id, String email, String type) {
        String sql = "";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String check = checkEmail(email);
        if(check.equals("free")){
        if(type.equals("company")){

            sql = "Update `company_id` SET `com_email` = ? WHERE `com_id` = ?";
            jdbcTemplate.update(sql, new Object[] { email,id});
            
        }else{
            sql = "Update `employee_id` SET `em_email` = ? WHERE `em_id` = ?";
            jdbcTemplate.update(sql, new Object[] { email,id});
        }
        return true;
        }
        else{
            return false;
        }
    }
        
        
    @Override
    public void updatePassword(int id, String password, String type) {
        String sql = "";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);  
        if(type.equals("company")){

            sql = "Update `company_id` SET `com_password` = PASSWORD(?) WHERE `com_id` = ?";
            jdbcTemplate.update(sql, new Object[] { password,id});
            
        }else{
            sql = "Update `employee_id` SET `em_password` = PASSWORD(?) WHERE `em_id` = ?";
            jdbcTemplate.update(sql, new Object[] { password,id});
        }
    }
    
   

}
