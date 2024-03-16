package com.yaru.TimeBank.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 志愿者实体
 */
@Data
public class Volunteer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String gender;

    private String address;

    private String identityNumber;

    private String accountStatus;

    private String physical;

    private String remark;
}
