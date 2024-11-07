package ru.job4j.thread;

public final class DCLSingleton {

    private static volatile DCLSingleton instance;

    private DCLSingleton() {

    }

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    DCLSingleton dclSingleton = new DCLSingleton();
                    instance = dclSingleton;
                }
            }
        }
        return instance;
    }
}
