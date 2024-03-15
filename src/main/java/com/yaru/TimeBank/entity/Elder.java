package com.yaru.TimeBank.entity;
import lombok.Data;

import java.io.Serializable;

/**
 * 老人实体
 */
@Data
public class Elder {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String gender;

    private String address;

    private String identityNumber;

    private String accountStatus;

    private String physical;

    private String remark;

    private String childrenPhone;
}
