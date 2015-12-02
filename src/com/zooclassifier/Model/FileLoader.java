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
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 *
 * @author David Kwan
 */
public class FileLoader {
    // attributes[i][j] artinya mendapatkan attribut ke j dari instance i
    private String[][] attributes;
    private String[] labels;
    private String[][] attributesLegalValues;
    private String[] labelsLegalValues;
    
    public FileLoader(String filename) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        data.setClassIndex(data.numAttributes() - 1);
        
        attributes = new String[data.numInstances()][data.numAttributes()-1];
        labels = new String[data.numInstances()];
        
        for(int i=0; i<data.numInstances(); i++){
            Instance instance = data.instance(i);
            for(int j=0; j<instance.numAttributes()-1; j++){
               attributes[i][j] = instance.stringValue(j);
            }
            labels[i] = instance.stringValue(instance.numAttributes()-1);
        }
        
        attributesLegalValues = new String[data.numAttributes()-1][];
        for (int i=0; i<data.numAttributes()-1; i++){
            attributesLegalValues[i] = (String[]) Collections.list(data.attribute(i).enumerateValues()).toArray(new String[data.attribute(i).numValues()]);
        }
        
        labelsLegalValues = (String[]) Collections.list(data.attribute(data.numAttributes()-1).enumerateValues()).toArray(new String[data.attribute(data.numAttributes()-1).numValues()]);
    }
    
    public String[][] getAttributes(){
        return attributes;
    }
    
    public String[] getLabels(){
        return labels;
    }
    
    public String[][] getAttributesLegalValues(){return attributesLegalValues;}
    public String[] getLabelsLegalValues(){return labelsLegalValues;}
}
