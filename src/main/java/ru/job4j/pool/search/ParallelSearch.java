package ru.job4j.pool.search;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T obj;
    private final T[] array;
    private final int from;
    private final int to;

    public ParallelSearch(T obj, T[] array, int from, int to) {
        this.obj = obj;
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return obj.equals(array[from]) ? from : -1;
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(obj, array, from, middle);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(obj, array, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return left == -1 ? right : left;
    }

    public static <T> int search(T obj, T[] array) {
        if (obj == null || array == null || array.length == 0 || obj.getClass() != array[0].getClass()) {
            throw new IllegalArgumentException();
        }
        if (array.length <= 10) {
            int result = -1;
            for (int i = 0; i < array.length; i++) {
                if (obj.equals(array[i])) {
                    result = i;
                    break;
                }
            }
            return result;
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(obj, array, 0, array.length - 1));
    }
}
