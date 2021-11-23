package it.unibo.oop.lab.workers02;

import java.util.List;
import java.util.ArrayList;

public class MultiThreadedSumMatrix implements SumMatrix{
    private final int n;
    /**
     * @param n
     *         any positive integer
     */
    public MultiThreadedSumMatrix(final int n) {
        this.n = n;
    }
    
    private static class Worker extends Thread {
        private final double[][] matrix;
        private long res;
        private final int firstInd;
        private final int secondInd;
        private final int nElemFirstInd;
        private final int nElemSecondInd;
                
        /**
         * @param matrix
         *          the matrix where sum the element
         * @param firstInd
         *          the index of the row
         * @param secondInd
         *          the index of the column
         * @param nElemFirstInd
         *          the no. of the rows
         * @param nElemSecondInd
         *          the no. of the columns
         */
        Worker(final double[][] matrix, final int firstInd, final int secondInd, final int nElemFirstInd, final int nElemSecondInd) {
            super();
            this.matrix = matrix;
            this.firstInd = firstInd;
            this.secondInd = secondInd;
            this.nElemFirstInd = nElemFirstInd;
            this.nElemSecondInd = nElemSecondInd;
        }
        
        public void run() {
            System.out.println("Working from position > " + this.firstInd + " | " + this.secondInd + 
                                " to position > " + (this.firstInd + this.nElemFirstInd-1) + " | " + 
                                                    (this.secondInd+this.nElemSecondInd-1));  
            for(int i=this.firstInd; i<matrix.length && i<this.firstInd + this.nElemFirstInd; i++) {
                for(int j=this.secondInd; j<matrix.length && j<this.secondInd + this.nElemSecondInd; j++) {
                    this.res+=matrix[i][j];       
                }
            }
            
        }
        
        /**
         * @return the sum of every element in the matrix
         */
        public long getResult() {
            return this.res;
        }

    }
    
    @Override
    public double sum(final double matrix[][]) {
        final int size = matrix.length % n +  matrix.length / n;              

        final List<Worker> mat = new ArrayList<>(n);
        for (int start = 0; start < matrix.length; start += size) {
            for(int startSecond = 0; startSecond < matrix.length; startSecond += size) {
                mat.add(new Worker(matrix, start, startSecond, size, size));
            }
        }
        
        for (final Worker worker: mat) {
            worker.start();
        }
        
        long sum = 0;
        for (final Worker w : mat) {
            try {
                w.join();
                sum += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        return sum;
        
    }
    
}




