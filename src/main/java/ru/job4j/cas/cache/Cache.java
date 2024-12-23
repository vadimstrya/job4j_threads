package ru.job4j.cas.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public void update(Base model) throws OptimisticException {
        memory.computeIfPresent(model.id(), (k, v) -> {
            if (model.version() != v.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.id(), model.name(), model.version() + 1);
        });
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
