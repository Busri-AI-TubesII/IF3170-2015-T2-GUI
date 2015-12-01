/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 *
 * @author nim_13512501
 */
public class ClassifierwithStringData {
    private OfflineLearningNominalDataClassifier classifier;
    private String [][] inputString;
    private String [] outputString;
    
    public ClassifierwithStringData (OfflineLearningNominalDataClassifier classifier){
        this.classifier = classifier;
    }
    
    public void setInputString(String[][] inputString){
        this.inputString = new String[inputString.length][];
        for (int i=0;i<inputString.length;i++){
            this.inputString[i]=inputString[i].clone();
        }
    }
    public void setOutputString(String[] outputString){
        this.outputString=outputString.clone();
    }
    public void setClassifier(OfflineLearningNominalDataClassifier classifier){
        this.classifier = classifier;
    }
    private int search(Object [] arr, Object key){
        for (int i=0;i<arr.length;i++){
            if (arr[i].equals(key))
                return i;
        }
        return -1;
    }
    private int [][] inputStringToInt(String [][]inputCategory){
        
        int [][] inputCategoryInt = new int[inputCategory.length][];
        for (int i=0;i<inputCategory.length;i++){
            inputCategoryInt[i] = new int [inputCategory[i].length];
            for (int j=0;j<inputCategory[i].length;j++){
                inputCategoryInt[i][j]=search(inputString[j], inputCategory[i][j]);
            }
        }
        return inputCategoryInt;
    }
    private int [] outputClassStringToInt(String [] outputClass){
        
        int [] outputClassInt = new int[outputClass.length];
        for (int i=0; i<outputClass.length;i++){
            outputClassInt[i]=search(outputString, outputClass[i]);
        }
        return outputClassInt;
    }
    public void train(String[][] inputCategory, String[] outputClass) throws Exception{
        //create array of dataset for training
        int [][] inputCategoryInt = inputStringToInt(inputCategory);
        int [] outputClassInt = outputClassStringToInt(outputClass);
        int [] numInputCategory = new int[inputString.length];
        for (int i=0;i<inputString.length;i++){
            numInputCategory[i] = inputString[i].length;
        }
        
        //train classifier
        classifier.train(numInputCategory,inputCategoryInt,outputString.length,outputClassInt);
    }
    
    public String predict(String [] inputString) throws Exception{
        int [] inputInt = new int[inputString.length];
        for (int i=0;i<inputString.length;i++){
            inputInt[i] = search(this.inputString[i], inputString[i]);
        }
        int classInt= classifier.predict(inputInt);
        return outputString[classInt];
    }
    
    public double accuracy(String [][] inputCategories, String [] outputClass) throws Exception{
        return classifier.calculateAccuracy(inputStringToInt(inputCategories), outputClassStringToInt(outputClass));
    }
    
    //TODO save dan load
    public void writeHypothesis(OutputStream str) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void loadHypothesis(InputStream str) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
