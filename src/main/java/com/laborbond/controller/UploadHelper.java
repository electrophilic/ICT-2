/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laborbond.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hy
 */
public class UploadHelper {
        
    public static void fileUpload(MultipartFile file, String name, File dir) throws Exception {
        if (!file.isEmpty()) {

            byte[] bytes = file.getBytes();
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + name);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } else {
            throw new FileNotFoundException("Invalid file");
        }
    }
}
