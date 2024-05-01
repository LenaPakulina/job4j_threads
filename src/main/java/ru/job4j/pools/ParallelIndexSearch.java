package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T, P> extends RecursiveTask<Integer> {
    private final T[] array;
    private final P target;

    public ParallelIndexSearch(T[] array, P target) {
        this.array = array;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            return lineSearchIndexOf();
        }
        int middle = (array.length) / 2;
        ParallelIndexSearch left = new ParallelIndexSearch(Arrays.copyOfRange(array, 0, middle), target);
        ParallelIndexSearch right = new ParallelIndexSearch(Arrays.copyOfRange(array, middle + 1, array.length), target);
        left.fork();
        right.fork();
        int leftResult = (int) left.join();
        int rightResult = (int) right.join();
        if (rightResult != -1) {
            rightResult = rightResult + middle + 1;
        }
        return Math.max(leftResult, rightResult);
    }

    public Integer indexOf() {
        if (array == null || array.length == 0 || target == null) {
            return null;
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(this);
    }

    private Integer lineSearchIndexOf() {
        for (int i = 0; i < array.length; i++) {
            if (target.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
