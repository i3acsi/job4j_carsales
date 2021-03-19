package ru.job4j.carsales.repo;

import lombok.RequiredArgsConstructor;
import ru.job4j.carsales.model.Account;
import ru.job4j.carsales.model.Role;

import java.util.Date;

@RequiredArgsConstructor(staticName = "of")
class AccountRepo {
    private final Store store;

    boolean create(Account account) {
        Role role = store.findOrCreate(Role.class, account.getRole(), "role_name", account.getRole().getName());
        account.setRole(role);
        return store.persist(account).getId() > 0L;
    }

    void update(Account account) {
        if (account.getId() > 0) {
            account.setUpdated(new Date(System.currentTimeMillis()));
            store.update(account);
        }
    }

    Account findAccountById(Long id) {
        return store.tx(session ->
                (Account) session.createQuery(" select distinct a from Account a where a.id = :fId")
//                        .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                        .setParameter("fId", id)
                        .uniqueResult()
        );
    }

    Account findAccountByEmail(String email) {
        return store.tx(session ->
                (Account) session.createQuery(" select distinct a from Account a where a.email = :fEmail")
                        .setParameter("fEmail", email)
                        .uniqueResult()
        );
    }
}