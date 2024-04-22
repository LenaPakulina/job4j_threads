package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        var firstAccount = getById(fromId);
        var secondAccount = getById(toId);
        boolean result = false;
        if (fromId != toId && firstAccount.isPresent() && secondAccount.isPresent()) {
            Account accFr = firstAccount.get();
            Account accTo = secondAccount.get();
            update(new Account(accFr.id(), accFr.amount() - amount));
            update(new Account(accTo.id(), accTo.amount() + amount));
            result = true;
        }
        return result;
    }
}
