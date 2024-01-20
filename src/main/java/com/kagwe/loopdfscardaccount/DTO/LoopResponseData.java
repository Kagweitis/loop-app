package com.kagwe.loopdfscardaccount.DTO;

import com.kagwe.loopdfscardaccount.Model.Account;
import com.kagwe.loopdfscardaccount.Model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoopResponseData implements Serializable {

    private int statusCode;

    private Account account;

    private Card card;

    private String message;
}
