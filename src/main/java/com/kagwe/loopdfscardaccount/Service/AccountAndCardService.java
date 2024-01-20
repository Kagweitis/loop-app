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
        Long id = card.getAccount().getAccountId();


        // Check if the associated account exists
        if (accountRepository.findByAccountId(id).isEmpty()) {
            // Handle case where the associated account does not exist
            loopResponseData.setMessage("The associated account does not exist");
            loopResponseData.setStatusCode(HttpStatus.NOT_FOUND.value());
            return loopResponseData;
        }
        try {
            Optional<Account> savedAcc = accountRepository.findByAccountId(id);
            card.setAccount(savedAcc.get());
            CardType cardType = CardType.valueOf(String.valueOf(card.getCardType()).toUpperCase());
            card.setCardType(cardType);
            cardRepository.save(card);
            loopResponseData.setMessage("Card created and Linked successfully");
            loopResponseData.setStatusCode(HttpStatus.CREATED.value());
            loopResponseData.setCard(card);
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
        List<Card> associatedCards = cardRepository.findByAccountIdAndDeletedFalse(accountId);
        LoopResponseData loopResponseData = new LoopResponseData();
        log.info(" cards "+associatedCards);

        if(!associatedCards.isEmpty()){
            loopResponseData.setMessage("Cards found");
            loopResponseData.setCard((Card) associatedCards);
            loopResponseData.setStatusCode(HttpStatus.OK.value());
        } else {
            loopResponseData.setMessage("No cards associated with the account");
            loopResponseData.setStatusCode(HttpStatus.NO_CONTENT.value());
        }
        return loopResponseData;
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
                Optional<Account> existingaccount = accountRepository.findById(accountId);
                if (account.getIban() != null) {
                    existingaccount.get().setIban(account.getIban());
                }
                if (account.getBicSwift() != null) {
                    existingaccount.get().setBicSwift(account.getBicSwift());
                }
                if (account.getClientId() != null) {
                    existingaccount.get().setClientId(account.getClientId());
                }
                accountRepository.save(existingaccount.get());
                loopResponseData.setMessage("account updated successfully");
                loopResponseData.setAccount(existingaccount.get());
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
}
