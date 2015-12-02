/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author nim_13512501
 */
public class  ClassifierwithStringData {
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
            outputClassInt[i]=search(getOutputString(), outputClass[i]);
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
        classifier.train(numInputCategory,inputCategoryInt,getOutputString().length,outputClassInt);
    }
    
    public String predict(String [] inputString) throws Exception{
        int [] inputInt = new int[inputString.length];
        for (int i=0;i<inputString.length;i++){
            inputInt[i] = search(this.inputString[i], inputString[i]);
        }
        int classInt= classifier.predict(inputInt);
        return getOutputString()[classInt];
    }
    
    public double accuracy(String [][] inputCategories, String [] outputClass) throws Exception{
        return classifier.calculateAccuracy(inputStringToInt(inputCategories), outputClassStringToInt(outputClass));
    }
    
    public int [][] calculateConfusionMatrix(String [][] inputCategories, String [] outputClass) throws Exception{
        return classifier.calculateConfusionMatrix(classifier, inputStringToInt(inputCategories), outputClassStringToInt(outputClass), outputString.length);
    }
    
    //TODO save dan load
    public void writeHypothesis(OutputStream str) throws IOException {
        
        PrintStream printStream = new PrintStream(str);
        
        // Print for input string
        printStream.println(inputString.length);
        for(int i=0; i<inputString.length; i++){
            printStream.println(inputString[i].length);
            for(int j=0; j<inputString[i].length; j++){
                printStream.println(inputString[i][j]);
            }
        }
        
        // Print for output string
        printStream.println(outputString.length);
        for (int i = 0; i < outputString.length; i++) {
            printStream.println(outputString[i]);
        }
        
        //save tipe dari classifier
        printStream.println(classifier.getClass().getName());
        printStream.flush();
        classifier.writeHypothesis(str);
        
    }

    public void loadHypothesis(InputStream str) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Scanner sc = new Scanner(str);
        int maxI = sc.nextInt();
        System.out.println(maxI);
        inputString = new String[maxI][];
        for (int i = 0; i < maxI; i++) {
            int maxJ = sc.nextInt();
            System.out.println(maxJ);
            sc.nextLine();
            inputString[i] = new String[maxJ];
            for (int j = 0; j < maxJ; j++) {
                inputString[i][j] = sc.nextLine();
                System.out.println(inputString[i][j]);
                
            }
        }
        
        maxI = sc.nextInt();
        System.out.println(maxI);
        sc.nextLine();
        outputString = new String[maxI];
        for (int i = 0; i < maxI; i++) {
            outputString[i] = sc.nextLine();
            System.out.println(outputString[i]);
        }
        
        String className = sc.nextLine();
        System.out.println(className);
        
        Class cl = Class.forName(className);
        classifier = (OfflineLearningNominalDataClassifier) cl.newInstance();
        classifier.loadHypothesis(sc);
    }

    /**
     * @return the outputString
     */
    public String[] getOutputString() {
        return outputString;
    }
}
