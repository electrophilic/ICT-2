/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.company;

/**
 *
 * @author Fu
 */
public interface CompanyService {
    
    public void updateComInfo(ComInfo comInfo);
        
    public ComInfo getComInfo(int id);
}
