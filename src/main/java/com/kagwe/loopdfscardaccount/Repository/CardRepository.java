package com.kagwe.loopdfscardaccount.Repository;

import com.kagwe.loopdfscardaccount.Model.Account;
import com.kagwe.loopdfscardaccount.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
   
//    @Query(nativeQuery = true, value = "select * from card where account_id = :accountId")
    List<Card> findByDeletedFalseAndAccountId(@Param("accountId") Long accountId);

    Optional<Card> findCardByCardId(Long cardId);

    List<Card> findByDeletedFalse();
}
