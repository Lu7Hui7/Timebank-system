package com.yaru.TimeBank.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identityNumber;

    private String accountNumber;

    private String accountAddress;

    private String accountPassword;

    private Long money;


}