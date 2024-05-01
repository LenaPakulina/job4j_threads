package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] result = new Sums[size];
        for (int i = 0; i < size; i++) {
            result[i] = amountByIndex(matrix, i);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        Sums[] result = new Sums[size];
        List<CompletableFuture<Sums>> futures = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            futures.add(asyncAmountByIndex(matrix, i));
        }
        for (int i = 0; i < size; i++) {
            result[i] = futures.get(i).get();
        }
        return result;
    }

    private static CompletableFuture<Sums> asyncAmountByIndex(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> amountByIndex(matrix, index));
    }

    private static Sums amountByIndex(int[][] matrix, int index) {
        Sums result = new Sums();
        result.setRowSum(amountByRow(matrix, index));
        result.setColSum(amountByColumn(matrix, index));
        return result;
    }

    private static int amountByRow(int[][] matrix, int index) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[index][i];
        }
        return result;
    }

    private static int amountByColumn(int[][] matrix, int index) {
        int result = 0;
        for (int[] ints : matrix) {
            result += ints[index];
        }
        return result;
    }
}
