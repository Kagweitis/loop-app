package com.kagwe.loopdfscardaccount.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Accounts")
@Builder
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", unique = true, nullable = false)
    private Long accountId;

    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "bic_swift", nullable = false)
    private String bicSwift;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "deleted")
    @Builder.Default
    private Boolean deleted = false;

    @OneToMany(targetEntity = Card.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private List<Card> cards;



}
