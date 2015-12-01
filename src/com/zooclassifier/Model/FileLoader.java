/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

/**
 *
 * @author nim_13512501
 */
public interface FileLoader {
    // attributes[i][j] artinya mendapatkan attribut ke j dari instance i
    
    public String[][] getAttributes();
    
    public String[] getLabels();
    
    public String[][] getAttributesLegalValues();
    public String[] getLabelsLegalValues();
}
