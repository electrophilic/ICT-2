/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.skill;

import java.util.List;

/**
 *
 * @author Fu
 */
public interface SkillService {
    
    public void addSkill(int id, List<Skill> skill, String type);
    
    public void addNewSkill(String skill);
    
    public List<Skill> getAllSkill();
    
    public List<Skill> getSkill(int id, String type);
    
    
}
