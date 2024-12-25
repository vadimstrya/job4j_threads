package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RowColSumTest {

    @Test
    public void whenSum() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        Sums[] expected = new Sums[]{
                new Sums(6, 3),
                new Sums(6, 6),
                new Sums(6, 9),
        };
        Sums[] actual = RowColSum.sum(matrix);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        Sums[] expected = new Sums[]{
                new Sums(6, 3),
                new Sums(6, 6),
                new Sums(6, 9),
        };
        Sums[] actual = RowColSum.asyncSum(matrix);
        assertThat(expected).isEqualTo(actual);
    }
}
