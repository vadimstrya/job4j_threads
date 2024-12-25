package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    /** Синхронный подсчет сумм */
    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] result = new Sums[n];

        for (int i = 0; i < n; i++) {
            result[i] = new Sums();
        }
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                result[row].setRowSum(result[row].getRowSum() + matrix[row][col]);
                result[col].setColSum(result[col].getColSum() + matrix[row][col]);
            }
        }
        return result;
    }

    /** Асинхронный подсчет сумм */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        Sums[] result = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();

        for (int i = 0; i < n; i++) {
            futures.put(i, getTask(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            result[key] = futures.get(key).get();
        }
        return result;
    }

    /** Получение задачи для подсчета сумм в указанной строке/столбце */
    public static CompletableFuture<Sums> getTask(int[][] matrix, int colRowNum) {
        return CompletableFuture.supplyAsync(() -> {
            int n = matrix.length;
            Sums sums = new Sums();

            for (int i = 0; i < n; i++) {
                sums.setColSum(sums.getColSum() + matrix[i][colRowNum]);
                sums.setRowSum(sums.getRowSum() + matrix[colRowNum][i]);
            }
            return sums;
        });
    }
}
