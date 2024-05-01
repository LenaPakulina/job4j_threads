package ru.job4j.pools;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class RolColSumTest {
    private static int[][] array3 = new int[3][3];
    private static int[][] array500 = new int[500][500];

    @BeforeAll
    public static void init() {
        int tempValue = 1;
        for (int i = 0; i < array3.length; i++) {
            for (int j = 0; j < array3.length; j++) {
                array3[i][j] = tempValue++;
            }
        }
        tempValue = 1;
        for (int i = 0; i < array500.length; i++) {
            for (int j = 0; j < array500.length; j++) {
                array500[i][j] = tempValue++;
            }
        }
    }

    @Test
    public void whenCheckSimpleMatrixSync() {
        RolColSum rolColSum = new RolColSum();
        long startTime = System.nanoTime();
        RolColSum.Sums[] result = rolColSum.sum(array3);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.printf("Time sync array3 = %d%n", duration);
        assertThat(result[0].getColSum()).isEqualTo(12);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getColSum()).isEqualTo(15);
        assertThat(result[1].getRowSum()).isEqualTo(15);
        assertThat(result[2].getColSum()).isEqualTo(18);
        assertThat(result[2].getRowSum()).isEqualTo(24);
    }

    @Test
    public void whenCheckSimpleMatrixAsync() throws ExecutionException, InterruptedException {
        RolColSum rolColSum = new RolColSum();
        long startTime = System.nanoTime();
        RolColSum.Sums[] result = rolColSum.asyncSum(array3);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.printf("Time async array3 = %d%n", duration);
        assertThat(result[0].getColSum()).isEqualTo(12);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getColSum()).isEqualTo(15);
        assertThat(result[1].getRowSum()).isEqualTo(15);
        assertThat(result[2].getColSum()).isEqualTo(18);
        assertThat(result[2].getRowSum()).isEqualTo(24);
    }

    @Test
    public void whenCheckMatrixSync() {
        RolColSum rolColSum = new RolColSum();
        long startTime = System.nanoTime();
        RolColSum.Sums[] result = rolColSum.sum(array500);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.printf("Time sync array10 = %d%n", duration);
        assertThat(result[0].getRowSum()).isEqualTo(125250);
        assertThat(result[0].getColSum()).isEqualTo(62375500);
    }

    @Test
    public void whenCheckMatrixAsync() throws ExecutionException, InterruptedException {
        RolColSum rolColSum = new RolColSum();
        long startTime = System.nanoTime();
        RolColSum.Sums[] result = rolColSum.asyncSum(array500);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.printf("Time async array10 = %d%n", duration);
        assertThat(result[0].getRowSum()).isEqualTo(125250);
        assertThat(result[0].getColSum()).isEqualTo(62375500);
    }
}