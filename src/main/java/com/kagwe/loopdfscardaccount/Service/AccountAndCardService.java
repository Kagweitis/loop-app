package com.kagwe.loopdfscardaccount.Service;


import com.kagwe.loopdfscardaccount.DTO.LoopResponseData;
import com.kagwe.loopdfscardaccount.Model.Account;
import com.kagwe.loopdfscardaccount.Model.Card;
import com.kagwe.loopdfscardaccount.Model.CardType;
import com.kagwe.loopdfscardaccount.Repository.AccountRepository;
import com.kagwe.loopdfscardaccount.Repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountAndCardService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public LoopResponseData createAccount(Account account) {
        LoopResponseData loopResponseData = new LoopResponseData();

        //check if account already exists
        Optional<Account> existingAccount = accountRepository.findByAccountId(account.getAccountId());
        if (existingAccount.isPresent()){
            loopResponseData.setMessage("This account already exists");
            loopResponseData.setStatusCode(HttpStatus.CONFLICT.value());
            return loopResponseData;
        }
        try {
            accountRepository.save(account);
            loopResponseData.setStatusCode(HttpStatus.OK.value());
            loopResponseData.setMessage("Account created Successfully");
            loopResponseData.setAccount(account);
            return loopResponseData;
        } catch (Exception e){
            e.printStackTrace();
            loopResponseData.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            loopResponseData.setMessage("An error occured");
            return loopResponseData;
        }

    }

    public LoopResponseData createCard(Card card) {
        LoopResponseData loopResponseData = new LoopResponseData();
        Long id = card.getAccountId();
        log.info(" acc id" + id);


        // Check if the associated account exists
        if (accountRepository.findByAccountId(id).isEmpty()) {
            // Handle case where the associated account does not exist
            loopResponseData.setMessage("The associated account does not exist");
            loopResponseData.setStatusCode(HttpStatus.NOT_FOUND.value());
            return loopResponseData;
        }
        try {
            CardType cardType = CardType.valueOf(String.valueOf(card.getCardType()).toUpperCase());
            card.setCardType(cardType);
            cardRepository.save(card);
            loopResponseData.setMessage("Card created and Linked successfully");
            loopResponseData.setStatusCode(HttpStatus.CREATED.value());
            return loopResponseData;
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            loopResponseData.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            loopResponseData.setMessage("An error occured");
            return loopResponseData;
        }


    }

    public LoopResponseData getCardsOnAcc(Long accountId) {
        log.info(" acc id "+accountId);
        List<Card> associatedCards = cardRepository.findByDeletedFalseAndAccountId(accountId);
        LoopResponseData loopResponseData = new LoopResponseData();
        log.info(" cards "+associatedCards);

        if(!associatedCards.isEmpty()){
            loopResponseData.setMessage("Cards found");
            loopResponseData.setCards(associatedCards);
            loopResponseData.setStatusCode(HttpStatus.OK.value());
            return loopResponseData;
        } else {
            loopResponseData.setMessage("No cards associated with the account");
            loopResponseData.setStatusCode(HttpStatus.NO_CONTENT.value());
            return loopResponseData;
        }
    }

    public LoopResponseData updateAccount(Long accountId, Account account) {
        LoopResponseData loopResponseData = new LoopResponseData();
        Optional<Account> existingAccountOptional = accountRepository.findByAccountId(accountId);

        try {

            if (existingAccountOptional.isEmpty()){
                loopResponseData.setMessage("Account does not exist");
                loopResponseData.setStatusCode(HttpStatus.NO_CONTENT.value());
                return loopResponseData;
            } else {
                Account existingAcc = existingAccountOptional.get();
                if (account.getIban() != null) {
                    existingAcc.setIban(account.getIban());
                }
                if (account.getBicSwift() != null) {
                    existingAcc.setBicSwift(account.getBicSwift());
                }
                if (account.getClientId() != null) {
                    existingAcc.setClientId(account.getClientId());
                }
                if (account.getDeleted() != null){
                    existingAcc.setDeleted(account.getDeleted());
                }
                accountRepository.save(existingAcc);
                loopResponseData.setMessage("account updated successfully");
                loopResponseData.setAccount(existingAcc);
                loopResponseData.setStatusCode(HttpStatus.OK.value());
                return loopResponseData;
            }
        } catch (Exception e){
            e.printStackTrace();
            loopResponseData.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            loopResponseData.setMessage("An error occured");
            return loopResponseData;
        }
    }

    public LoopResponseData updateCard(Long cardId, Card card) {
        //check if card exists
        Optional<Card> existingCard = cardRepository.findCardByCardId(cardId);
        LoopResponseData loopResponseData = new LoopResponseData();
        try {
            if (existingCard.isEmpty()){
                loopResponseData.setMessage("Card does not exist");
                loopResponseData.setStatusCode(HttpStatus.NO_CONTENT.value());
            } else {
                existingCard.get().setCardAlias(card.getCardAlias());
                cardRepository.save(existingCard.get());
                loopResponseData.setMessage("Card updated successfully");
                loopResponseData.setStatusCode(HttpStatus.OK.value());
            }
            return loopResponseData;
        } catch (Exception e){
            e.printStackTrace();
            loopResponseData.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            loopResponseData.setMessage("An error occured");
            return loopResponseData;
        }

    }
}
