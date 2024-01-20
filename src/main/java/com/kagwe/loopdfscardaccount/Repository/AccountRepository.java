package com.kagwe.loopdfscardaccount.Repository;

import com.kagwe.loopdfscardaccount.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query(nativeQuery = true, value = "select * from accounts where account_id = :accountId")
    Optional<Account> findByAccountId(Long accountId);

    List<Account> findByDeletedFalse();
}
