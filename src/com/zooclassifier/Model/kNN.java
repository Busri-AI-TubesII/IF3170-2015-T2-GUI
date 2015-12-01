/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zooclassifier.Model;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.min;
import java.util.Arrays;
import java.util.Comparator;
/**
 *
 * @author nim_13512501
 */
public class kNN extends OfflineLearningNominalDataClassifier{

    public kNN(int k){
        this.k = k;
    }
    
    public void setK(int k){
        this.k= k;
    }
    
    int getK(){return k;}
    
    private int k;
    private int [] exampleNumInputCategory = null;
    private int [][] exampleInputCategory = null;
    private int exampleNumOutputClass = 0;
    private int [] exampleOutputClass = null;
    private boolean trained = false;
    
    public boolean isTrained(){return trained;}
    
    @Override
    public void train(int[] numInputCategory, int[][] inputCategory, int numOutputClass, int[] outputClass) throws Exception{
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
        exampleNumInputCategory=numInputCategory.clone();
        exampleInputCategory = new int[inputCategory.length][];
        for (int i=0;i<inputCategory.length;i++){
            exampleInputCategory[i]=inputCategory[i].clone();
        }
        exampleNumOutputClass=numOutputClass;
        exampleOutputClass=outputClass.clone();
        trained=true;
    }

    private int distance(int exampleIdx, int[] input) throws Exception{
        if (exampleInputCategory[exampleIdx].length!=input.length)
            throw new Exception("input size mismatch");
        int differences=0;
        for (int i=0;i<exampleInputCategory[exampleIdx].length;i++){
            if (input[i]>=exampleNumInputCategory[i])
                throw new Exception("input category doesn't exist");
            if (input[i]!=exampleInputCategory[exampleIdx][i])
                differences++;
        }
        return differences;
    }

    @Override
    public OfflineLearningNominalDataClassifier copy() {
        kNN retval=new kNN(k);
        retval.trained = trained;
        if (exampleNumInputCategory!=null)
            retval.exampleNumInputCategory=exampleNumInputCategory.clone();
        if (exampleInputCategory!=null)
            for (int i=0;i<exampleInputCategory.length;i++)
                if (exampleInputCategory[i]!=null)
                    retval.exampleInputCategory[i]=exampleInputCategory[i].clone();
        retval.exampleNumOutputClass=exampleNumOutputClass;
        if (exampleOutputClass!=null)
            retval.exampleOutputClass=exampleOutputClass.clone();
        return retval;
    }
    
    private class NearestObject
    {
        public NearestObject(int position, int distance)
        {
             this.Position = position;
             this.Distance = distance;
        }
        public int Position = 0;
        public int Distance = 0;

    }

    private NearestObject[] SortDistance(NearestObject[] items)
    {
        Arrays.sort(items, new DistanceSort());
        return items;
    }

    private class DistanceSort implements Comparator<NearestObject>
    {
        public int compare(NearestObject o1, NearestObject o2)
        {
            return o1.Distance - o2.Distance;
        }
    }
    
    @Override
    public int predict(int[] inputCategory) throws Exception{
        if (!trained)
            throw new UnsupportedOperationException("Not trained yet."); //To change body of generated methods, choose Tools | Templates.
        NearestObject [] no = new NearestObject[exampleInputCategory.length];
        for (int i=0;i<exampleInputCategory.length;i++){
            no[i]=new NearestObject(i,distance(i,inputCategory));
        }
        no=SortDistance(no);
        int [] candidateClassCount = new int[exampleNumOutputClass];
        for (int i=0;i<exampleNumOutputClass;i++)candidateClassCount[i]=0;
        for (int i=0;i<min(k,exampleInputCategory.length);i++){
            candidateClassCount[exampleOutputClass[no[i].Position]]++;
        }
        
        int chosenClass=0;
        for (int i=0;i<candidateClassCount.length;i++){
            if (candidateClassCount[i]>candidateClassCount[chosenClass])
                chosenClass=i;
        }
        return chosenClass;
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
