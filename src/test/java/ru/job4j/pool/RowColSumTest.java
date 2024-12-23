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
        RowColSum.Sums[] sums = RowColSum.sum(matrix);
        assertThat(sums.length).isEqualTo(3);
        assertThat(sums[0].getRowSum()).isEqualTo(6);
        assertThat(sums[1].getRowSum()).isEqualTo(6);
        assertThat(sums[2].getRowSum()).isEqualTo(6);
        assertThat(sums[0].getColSum()).isEqualTo(3);
        assertThat(sums[1].getColSum()).isEqualTo(6);
        assertThat(sums[2].getColSum()).isEqualTo(9);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        RowColSum.Sums[] sums = RowColSum.asyncSum(matrix);
        assertThat(sums.length).isEqualTo(3);
        assertThat(sums[0].getRowSum()).isEqualTo(6);
        assertThat(sums[1].getRowSum()).isEqualTo(6);
        assertThat(sums[2].getRowSum()).isEqualTo(6);
        assertThat(sums[0].getColSum()).isEqualTo(3);
        assertThat(sums[1].getColSum()).isEqualTo(6);
        assertThat(sums[2].getColSum()).isEqualTo(9);
    }
}
