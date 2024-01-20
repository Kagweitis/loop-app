package com.kagwe.loopdfscardaccount.Controller;


import com.kagwe.loopdfscardaccount.DTO.LoopResponseData;
import com.kagwe.loopdfscardaccount.Model.Account;
import com.kagwe.loopdfscardaccount.Model.Card;
import com.kagwe.loopdfscardaccount.Service.AccountAndCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loop")
@CrossOrigin
@RequiredArgsConstructor
public class LoopController {

    private final AccountAndCardService accountAndCardService;

    @PostMapping("/create-account")
    public LoopResponseData createAccount(@RequestBody Account account){
        return accountAndCardService.createAccount(account);
    }

    @PostMapping("/card-create")
    public LoopResponseData createCard(@RequestBody Card card){
        return accountAndCardService.createCard(card);
    }

    @GetMapping("/cards/{accountId}")
    public LoopResponseData getCardsAssociatedToAccount(@PathVariable Long accountId){
        return accountAndCardService.getCardsOnAcc(accountId);
    }

    @PutMapping("account/update/{accountId}")
    public LoopResponseData updateAccount(@PathVariable Long accountId, @RequestBody Account account){
        return accountAndCardService.updateAccount(accountId, account);
    }
}
