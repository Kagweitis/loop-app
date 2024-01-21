package com.kagwe.loopdfscardaccount.Config;

import com.kagwe.loopdfscardaccount.Model.Account;
import com.kagwe.loopdfscardaccount.Model.Card;
import com.kagwe.loopdfscardaccount.Model.CardType;
import com.kagwe.loopdfscardaccount.Repository.AccountRepository;
import com.kagwe.loopdfscardaccount.Repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class DataIntializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;


    @Override
    public void run(String... args) {
        initData();
    }

    private void initData() {
        // Create and save some mock data for testing
        Account account1 = Account.builder()
                .accountId(12345789L)
                .iban("IBAN123")
                .bicSwift("BICSWIFT12")
                .clientId(1L)
                .build();

        Account account2 = Account.builder()
                .accountId(987654321L)
                .iban("IBAN456")
                .bicSwift("BICSWIFT34")
                .clientId(2L)
                .build();

        Card card1 = Card.builder()
                .cardId(44455L)
                .cardAlias("CardA")
                .accountId(account1.getAccountId())
                .cardType(CardType.PHYSICAL)
                .build();

        Card card2 = Card.builder()
                .cardId(999888L)
                .cardAlias("CardB")
                .accountId(account2.getAccountId())
                .cardType(CardType.VIRTUAL)
                .build();

        account1.setCards(Arrays.asList(card1));
        account2.setCards(Arrays.asList(card2));

        accountRepository.saveAll(Arrays.asList(account1, account2));
    }
}
