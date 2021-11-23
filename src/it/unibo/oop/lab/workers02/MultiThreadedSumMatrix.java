package it.unibo.oop.lab.workers02;

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
        
    }

    @Override
    public double sum(double[][] matrix) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}