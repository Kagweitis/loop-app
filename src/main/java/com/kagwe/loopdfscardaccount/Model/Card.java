package com.kagwe.loopdfscardaccount.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id", updatable = false, unique = true, nullable = false)
    private Long cardId;

    @Column(name = "card_alias")
    private String cardAlias;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", updatable = false, nullable = false)
    private CardType cardType;

    @Column(name = "deleted")
    private Boolean deleted;

//    @Column(name = "account_id", updatable = false, insertable=false, nullable = false)
//    private Long accountId;


    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    @JsonBackReference
    private Account account;



}
