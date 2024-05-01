package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {
    @Test
    public void differentDataTypes() {
        String[] stringArray = {"apple", "banana", "auto", "big", "car"};
        ParallelIndexSearch indexSearchStr = new ParallelIndexSearch(stringArray, "auto");
        assertThat(indexSearchStr.indexOf()).isEqualTo(2);

        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4, 5.5};
        ParallelIndexSearch indexSearchDouble = new ParallelIndexSearch(doubleArray, 5.5);
        assertThat(indexSearchDouble.indexOf()).isEqualTo(4);
    }

    @Test
    public void smallArrayLinearSearch() {
        Integer[] smallArray = {1, 2, 3, 4, 5};
        ParallelIndexSearch indexSearcher = new ParallelIndexSearch(smallArray, 3);
        assertThat(indexSearcher.indexOf()).isEqualTo(2);
    }

    @Test
    public void largeArrayParallelSearch() {
        Integer[] largeArray = new Integer[100];
        for (int i = 0; i < 100; i++) {
            largeArray[i] = i + 1;
        }
        ParallelIndexSearch indexSearcher = new ParallelIndexSearch(largeArray, 75);
        assertThat(indexSearcher.indexOf()).isEqualTo(74);
    }

    @Test
    public void elementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ParallelIndexSearch indexSearcher = new ParallelIndexSearch(array, 11);
        assertThat(indexSearcher.indexOf()).isEqualTo(-1);
    }

    @Test
    public void wherDiffDataTypes() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ParallelIndexSearch indexSearcher = new ParallelIndexSearch(array, "1");
        assertThat(indexSearcher.indexOf()).isEqualTo(-1);
    }

    @Test
    public void whenCheckSmallArray() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ParallelIndexSearch indexSearcher = new ParallelIndexSearch(array, 9);
        assertThat(indexSearcher.indexOf()).isEqualTo(8);
    }
}