package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.computeIfPresent(account.id(), (k, v) -> account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account accountFrom = getById(fromId).orElse(null);
        Account accountTo = getById(toId).orElse(null);

        if (accountFrom != null && accountTo != null && accountFrom.amount() >= amount) {
            accountFrom = new Account(accountFrom.id(), accountFrom.amount() - amount);
            accountTo = new Account(accountTo.id(), accountTo.amount() + amount);
            return update(accountFrom) && update(accountTo);
        }
        return false;
    }
}
