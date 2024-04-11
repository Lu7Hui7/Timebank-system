package com.yaru.TimeBank.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AOrder {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String senderAddress;

    private String receiverAddress;

    private LocalDateTime orderTime;

    private Integer oMoney;

    private Integer state;

    private Integer finishTime;

}
