/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * sebuah OfflineLearningNominalDataClassifier.
 * Training dilakukan sekali (Offline).
 * tiap kali training, hipotesis/mesin ter-overwrite.
 * Abstraksi harus dilakukan di luar objek sedemikian hingga objek ini hanya
 * menerima dan mengeluarkan data nominal yang diwakili oleh bilangan 0,1,2, ...
 * @author nim_13512501
 */
public abstract class OfflineLearningNominalDataClassifier {
    /**
     * 
     * @param numInputCategory
     * @param inputCategory
     * @param numOutputClass
     * @param outputClass 
     * men-training. 
     * prekondisi (dicek di implementasi):
     * apabila numInputCategories berukuran N,
     * maka inputCategory harus berukuran MxN,
     * dan outputClass harus berukuran M.
     * 0<=inputCategory[i][j]<numInputCategories[j]
     * 0<=outputClass<numOutputClass
     */
    public abstract void train(int[] numInputCategory, int[][] inputCategory, int numOutputClass, int[] outputClass) throws Exception;
    public abstract int predict(int [] inputCategory) throws Exception;
    public abstract OfflineLearningNominalDataClassifier copy();
    public double calculateAccuracy(int [][] inputCategory, int [] OutputClass) throws Exception{
        return calculateAccuracy(this,inputCategory,OutputClass);
    }
    public double calculateAccuracy(OfflineLearningNominalDataClassifier cl,
            int [][] inputCategory, int [] OutputClass) throws Exception{
        int numFalse = 0;
        int numTrue = 0;
        for (int i=0;i<inputCategory.length;i++){
            if (cl.predict(inputCategory[i])==OutputClass[i])
                numTrue++;
            else
                numFalse++;
        }
        return ((double)numTrue/(double)(numFalse+numTrue));
    }
    
    public int [][] calculateConfusionMatrix(OfflineLearningNominalDataClassifier cl,
            int [][] inputCategory, int [] OutputClass, int numOutputClass) throws Exception{
        int [][] retval = new int[numOutputClass][];
        for (int i=0;i<retval.length;i++){
            retval[i] = new int[numOutputClass];
            for (int j=0;j<retval[i].length;j++){
                retval[i][j]=0;
            }
        }
        
        for (int i=0;i<inputCategory.length;i++){
            int predicted = cl.predict(inputCategory[i]);
            retval[OutputClass[i]][predicted]++;
        }
        return retval;
    }
    abstract public void writeHypothesis(OutputStream str);
    abstract public void loadHypothesis(Scanner str);
}
