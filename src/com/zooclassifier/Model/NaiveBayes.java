/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author nim_13512501
 */
public class NaiveBayes extends OfflineLearningNominalDataClassifier{

    double [][][] pInGivenOut = null; //p(Xi=x|C=c) dinyatakan oleh pInGivenOut[i][x][c]
    double [][] pIn = null; //p(Xi=j) dinyatakan oleh pIn[i][j]
    double [] pOut = null;
    boolean trained = false;
    int numAttrs=0;
    int numOutputClass=0;
    
    
    
    //helper
    private int sum(int[] X){
        int sum=0;
        for (int xi : X){
            sum+=xi;
        }
        return sum;
    }
    
    private int sumCol(int[][]X, int colnum){
        int sum=0;
        for (int i=0;i<X.length;i++){
            sum+=X[i][colnum];
        }
        return sum;
    }
    
    private double product(double [] X){
        double product=1;
        for (double xi : X){
            product *= xi;
        }
        return product;
    }
    
    private double productcol(double [][] X, int colnum){
        double product = 1;
        for (int i=0;i<X.length;i++)
            product *=X[i][colnum];
        return product;
    }
    
    private int maxIdx(double [] X){
        int maxidx=0;
        for (int i=0;i<X.length;i++){
            if (X[i]>X[maxidx])
                maxidx=i;
        }
        return maxidx;
    }
    
    @Override
    public void train(int[] numInputCategory, int[][] inputCategory, int numOutputClass, int[] outputClass) throws Exception {
        if (outputClass.length!=inputCategory.length)
            throw new Exception ("size mismatch");
        for (int i=0;i<inputCategory.length;i++){
            if (numInputCategory.length!=inputCategory[i].length)
                throw new Exception ("size mismatch");
            for (int j=0;j<numInputCategory.length;j++){
                if (inputCategory[i][j]<0 || inputCategory[i][j]>=numInputCategory[j])
                    throw new Exception ("Category doesn't exist");
            }
            if (outputClass[i]<0 || outputClass[i]>=numOutputClass)
                throw new Exception ("Class doesn't exist");
        }
        
        //konstruksi array
        numAttrs = numInputCategory.length;
        pInGivenOut = new double[numInputCategory.length][][];
        for (int i=0;i<numAttrs;i++){
            pInGivenOut[i]=new double[numInputCategory[i]][];
            for (int j=0;j<numInputCategory[i];j++){
                pInGivenOut[i][j]=new double[numOutputClass];
            }
        }
        pIn=new double[numAttrs][];
        for (int i=0;i<numAttrs;i++)
            pIn[i]=new double[numInputCategory[i]];
        pOut=new double[numOutputClass];
        
        //perhitungan peluang
        int[] sumOut = new int[numOutputClass];
        for (int i=0;i<numOutputClass;i++){
            sumOut[i]=0;
            for (int j=0;j<outputClass.length;j++){
                if (outputClass[j]==i){
                    sumOut[i]++;
                }
            }
            pOut[i]=(double)sumOut[i]/(double)outputClass.length;
           // System.out.println("pOut[" + i + "] = " + pOut[i]);
        }
        
        for (int k=0;k<numOutputClass;k++){
            for (int i=0;i<numAttrs;i++){
                for (int j=0;j<numInputCategory[i];j++){
                    int curSum = 0;
                    for (int l=0;l<inputCategory.length;l++){
                        if (inputCategory[l][i]==j && outputClass[l]==k){
                            curSum++;
                        }
                    }
                    pInGivenOut[i][j][k]=(double)curSum/(double)sumOut[k];
                   // System.out.println("pInGivenOut["+i+"]["+j+"]["+k+"] = " + pInGivenOut[i][j][k]);
                }
            }
        }
        
        for (int i=0;i<numAttrs;i++){
            for (int j=0;j<numInputCategory[i];j++){
                int curSum=0;
                for (int [] x : inputCategory){
                    if (x[i]==j)
                        curSum++;
                }
                pIn[i][j]=(double)curSum/(double)numInputCategory.length;
            }
        }
        trained=true;
        this.numOutputClass=numOutputClass;
    }
    
    private double pInGivenOut( int [] inputCategory, int Out){
        double prod = 1;
        for (int i=0;i<pInGivenOut.length;i++){
          //  System.out.println(pInGivenOut[i][inputCategory[i]][Out]);
            prod*=pInGivenOut[i][inputCategory[i]][Out];
        }
        return prod;
    }
    
    private double pIn(int [] inputCategory){
        double prod=1;
        for (int i=0;i<pIn.length;i++){
            prod*=pIn[i][inputCategory[i]];
        }
        return prod;
    }

    private double pOutGivenIn(int Out, int [] inputCategory){
        return pOut[Out]*pInGivenOut(inputCategory,Out)/pIn(inputCategory);
    }
    
    @Override
    public int predict(int[] inputCategory) throws Exception {
        if (!trained)
            throw new UnsupportedOperationException("Not trained yet.");
        double [] pOutGivenIn = new double[numOutputClass];
        for (int i=0;i<numOutputClass;i++){
            pOutGivenIn[i]=pOutGivenIn(i,inputCategory);
          //  System.out.println("pOutGivenIn " + i + " = " + pOutGivenIn[i]);
        }
        return maxIdx(pOutGivenIn);
    }

    @Override
    public OfflineLearningNominalDataClassifier copy() {
        NaiveBayes copied = new NaiveBayes();
        if (this.pInGivenOut!=null){
            copied.pInGivenOut = new double[this.pInGivenOut.length][][];
            for (int i=0;i<this.pInGivenOut.length;i++){
                if (this.pInGivenOut[i]!=null){
                    copied.pInGivenOut[i]=new double[this.pInGivenOut[i].length][];
                    for (int j=0;j<this.pInGivenOut[i].length;j++)
                        if (this.pInGivenOut[i][j]!=null)
                            copied.pInGivenOut[i][j]=this.pInGivenOut[i][j].clone();
                }
            }
        }
        if (this.pIn!=null){
            copied.pIn=new double[this.pIn.length][];
            for (int i=0;i<this.pIn.length;i++){
                if (this.pIn[i]!=null)
                    copied.pIn[i]=this.pIn[i].clone();
            }
        }
        if (this.pOut!=null)
            copied.pOut=this.pOut.clone();
        copied.trained=this.trained;
        copied.numAttrs=this.numAttrs;
        copied.numOutputClass=this.numOutputClass;
        return copied;
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
