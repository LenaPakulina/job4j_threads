package ru.job4j.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] result = new Sums[size];
        for (int i = 0; i < size; i++) {
            result[i] = amountByIndex(matrix, i);
        }
        return result;
    }

    public Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
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

    private CompletableFuture<Sums> asyncAmountByIndex(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> amountByIndex(matrix, index));
    }

    private Sums amountByIndex(int[][] matrix, int index) {
        return new Sums(amountByRow(matrix, index), amountByColumn(matrix, index));
    }

    private int amountByRow(int[][] matrix, int index) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[index][i];
        }
        return result;
    }

    private int amountByColumn(int[][] matrix, int index) {
        int result = 0;
        for (int[] ints : matrix) {
            result += ints[index];
        }
        return result;
    }
}
