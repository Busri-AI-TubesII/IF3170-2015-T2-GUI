package com.zooclassifier.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nim_13512501
 */
public class ZooFileLoader {
    
    String [][] attributes;
    String [] labels;
    String [][] attributesLegalValues;
    String [] labelsLegalValues;
    
    //helper
    private boolean arrayContains(String [] arr, String key){
        for (String s : arr){
            if (s.equals(key))
                return true;
        }
        return false;
    }
    
    public ZooFileLoader(String filename) throws FileNotFoundException, ParseException{
        
        //instantiate legal values (hardcoded)
        attributesLegalValues= new String[][]{
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"1","0"},
            {"0","2","4","5","6","8"},
            {"1","0"},
            {"1","0"},
            {"1","0"}            
        };
        labelsLegalValues = new String [] { "1","2","3","4","5","6","7" };
        
        int numAttrs = attributesLegalValues.length;
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        int offset=0;
        
        Scanner sc = new Scanner(reader);
        sc.useDelimiter(Pattern.compile("[,\\s]+"));
        
        List<String[]> tempattr = new LinkedList<String[]> ();
        List<String> templabel = new LinkedList<String> ();
                
        
        while (sc.hasNext()){
            String [] curData = new String[numAttrs];
            String curLabel = null;
            
            //reading
            String name /*dibuang*/ = sc.next();
            offset += name.length()+1;
            for (int i=0;i<numAttrs;i++){
                if (!sc.hasNext()) throw new ParseException("Attr not found", offset);
                curData[i]=sc.next();
                offset+=1;
                if (!arrayContains(attributesLegalValues[i],curData[i]))
                    throw new ParseException("Attribute outside Legal Values", offset);
                offset += curData[i].length();
            }
            if (!sc.hasNext()) throw new ParseException("Label not found", offset);
            curLabel = sc.next();
            offset+=1;
            if (!arrayContains(labelsLegalValues,curLabel))
                throw new ParseException("Label outside Legal Values", offset);
            offset += curLabel.length();
            
            tempattr.add(curData);
            templabel.add(curLabel);
        }
        
        attributes = tempattr.toArray(new String[tempattr.size()][] );
        labels = templabel.toArray(new String[templabel.size()]);
    }



    public String[][] getAttributes(){
        return attributes;
    }

    public String[] getLabels(){
        return labels;
    }

    public String[][] getAttributesLegalValues(){return attributesLegalValues;}

    public String[] getLabelsLegalValues(){return labelsLegalValues;}
    // attributes[i][j] artinya mendapatkan attribut ke j dari instance i
    
}
