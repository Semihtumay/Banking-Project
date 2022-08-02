package com.semihtumay.bankingproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bankName;
    private Integer accountNumber;
    private Double currentBalance;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Transactions> transactions;
}
