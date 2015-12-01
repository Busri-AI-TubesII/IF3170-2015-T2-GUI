/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * @param T
 * @author nim_13512501
 */
public class kFold extends OfflineLearningNominalDataClassifier {
    
    private int k;
    OfflineLearningNominalDataClassifier classifier;
    
    public kFold(int k, OfflineLearningNominalDataClassifier classifier){
        this.k = k;
        this.classifier=classifier.copy();
    }
    
    public kFold(kFold X){
        this.k=X.k;
        this.classifier=X.classifier.copy();
    }
    
    
    static void shuffleTrainingSet(int[][] inputArr, int[] outArr)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = inputArr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int [] a = inputArr[index];
            inputArr[index] = inputArr[i];
            inputArr[i] = a;
            int b = outArr[index];
            outArr[index]=outArr[i];
            outArr[i]=b;
        }
    }
    
    @Override
    public void train(int[] numInputCategory, int[][] inputCategory, int numOutputClass, int[] outputClass) throws Exception {
        shuffleTrainingSet(inputCategory, outputClass);
        OfflineLearningNominalDataClassifier[] candidate = new OfflineLearningNominalDataClassifier[k];
        double [] accuracy = new double[k];
        int selected = 0;
        for (int i=0;i<k;i++){
            candidate[i]=classifier.copy();
            
            //pisah set menjadi tr dan cv
            ArrayList<int[]> trInputCategory = new ArrayList<int[]>();
            ArrayList<Integer> trOutputClass = new ArrayList<Integer>();
            ArrayList<int[]> cvInputCategory = new ArrayList<int[]>();
            ArrayList<Integer> cvOutputClass = new ArrayList<Integer>();
            for (int j=0;j<i*inputCategory.length/k;j++){
                trInputCategory.add(inputCategory[j]);
                trOutputClass.add(outputClass[j]);
            }
            for (int j=i*inputCategory.length/k;j<(i+1)*inputCategory.length/k;j++){
                cvInputCategory.add(inputCategory[j]);
                cvOutputClass.add(outputClass[j]);
            }
            for (int j=(i+1)*inputCategory.length/k;j<inputCategory.length;j++){
                trInputCategory.add(inputCategory[j]);
                trOutputClass.add(outputClass[j]);
            }
            candidate[i].train(numInputCategory, (int[][])trInputCategory.toArray(new int[trInputCategory.size()][]),
                    numOutputClass, trOutputClass.stream().mapToInt(Integer::intValue).toArray());
            accuracy[i]=calculateAccuracy(candidate[i],
                    cvInputCategory.toArray(new int[cvInputCategory.size()][]),
                            cvOutputClass.stream().mapToInt(Integer::intValue).toArray());
            if (accuracy[i]>accuracy[selected])
                selected=i;
        }
        classifier=candidate[selected];
        
    }

    @Override
    public int predict(int[] inputCategory) throws Exception {
        return classifier.predict(inputCategory);
    }

    @Override
    public OfflineLearningNominalDataClassifier copy() {
        return new kFold(this);
    }

//TODO save dan load
    @Override
    public void writeHypothesis(OutputStream str) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadHypothesis(InputStream str) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }  
}
