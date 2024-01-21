package com.kagwe.loopdfscardaccount.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Card")
@Builder
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id", updatable = false, unique = true, nullable = false)
    private Long cardId;

    @Column(name = "card_alias")
    private String cardAlias;

    @Column(name = "account_id", nullable = false, updatable = false)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", updatable = false, nullable = false)
    private CardType cardType;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;




}
