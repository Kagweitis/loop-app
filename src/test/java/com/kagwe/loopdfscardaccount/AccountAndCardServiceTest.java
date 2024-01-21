package com.kagwe.loopdfscardaccount;

import com.kagwe.loopdfscardaccount.DTO.LoopResponseData;
import com.kagwe.loopdfscardaccount.Model.Account;
import com.kagwe.loopdfscardaccount.Model.Card;
import com.kagwe.loopdfscardaccount.Model.CardType;
import com.kagwe.loopdfscardaccount.Repository.AccountRepository;
import com.kagwe.loopdfscardaccount.Repository.CardRepository;
import com.kagwe.loopdfscardaccount.Service.AccountAndCardService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class AccountAndCardServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private AccountAndCardService accountAndCardService;

    @Test
    void testCreateAccount() {
        Account testAccount = new Account(1L, 123456789L, "IBAN123", "BICSWIFT12", 123L, false, Collections.singletonList(
                new Card(1L, 1234L, "John", 1234567891L, CardType.PHYSICAL, false)));

        Mockito.when(accountRepository.findByAccountId(testAccount.getAccountId()))
                .thenReturn(Optional.empty());

        Mockito.when(accountRepository.save(Mockito.any(Account.class)))
                .thenReturn(testAccount);

        LoopResponseData response = accountAndCardService.createAccount(testAccount);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Account created Successfully", response.getMessage());
        assertEquals(testAccount, response.getAccount());
    }

    @Test
    void testCreateAccountWhenAccountExists() {
        Account existingAccount = new Account(1L, 123456789L, "IBAN123", "BICSWIFT12", 123L, false, Collections.singletonList(
                new Card(1L, 1234L, "John", 1234567891L, CardType.PHYSICAL, false)) );

        Mockito.when(accountRepository.findByAccountId(existingAccount.getAccountId()))
                .thenReturn(Optional.of(existingAccount));

        LoopResponseData response = accountAndCardService.createAccount(existingAccount);

        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
        assertEquals("This account already exists", response.getMessage());
        assertNull(response.getAccount());
    }

    @Test
    void testCreateCard() {
        Card testCard = new Card(1L, 1234L, "John", 1234567891L, CardType.PHYSICAL, false );
        Long accountId = testCard.getAccountId();

        Mockito.when(accountRepository.findByAccountId(accountId))
                .thenReturn(Optional.of(new Account(1L, 123456789L, "IBAN123", "BICSWIFT12", 123L, false, Collections.singletonList(
                        new Card(1L, 1234L, "John", 1234567891L, CardType.PHYSICAL, false)) )));

        Mockito.when(cardRepository.save(Mockito.any(Card.class)))
                .thenReturn(testCard);

        LoopResponseData response = accountAndCardService.createCard(testCard);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
        assertEquals("Card created and Linked successfully", response.getMessage());
    }

    @Test
    void testCreateCardWhenAccountDoesNotExist() {
        Card testCard = new Card(1L, 1234L, "John", 1234567891L, CardType.PHYSICAL, false );
        Long accountId = testCard.getAccountId();

        Mockito.when(accountRepository.findByAccountId(accountId))
                .thenReturn(Optional.empty());

        LoopResponseData response = accountAndCardService.createCard(testCard);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        assertEquals("The associated account does not exist", response.getMessage());
    }

    @Test
    void testGetCardsOnAcc() {
        Long accountId = null;
        Card testCard = new Card(1L, 1234L, "John", 1234567891L, CardType.PHYSICAL, false );

        Mockito.when(cardRepository.findByDeletedFalseAndAccountId(accountId))
                .thenReturn(Collections.singletonList(testCard));

        LoopResponseData response = accountAndCardService.getCardsOnAcc(accountId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Cards found", response.getMessage());
        assertEquals(Collections.singletonList(testCard), response.getCards());
    }

    @Test
    void testGetCardsOnAccWhenNoCards() {
        Long accountId = 1234567891L;

        Mockito.when(cardRepository.findByDeletedFalseAndAccountId(accountId))
                .thenReturn(Collections.emptyList());

        LoopResponseData response = accountAndCardService.getCardsOnAcc(accountId);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        assertEquals("No cards associated with the account", response.getMessage());
        assertNull(response.getCards());
    }

}
