package com.yaru.TimeBank.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 活动表实体
 */


@Data
public class Activity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    // 使用 @ManyToOne 注解表示与 Volunteer 实体类的多对一关系，并指定关联字段
    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    private Long volunteerId;

    private String activityName;

    private String activityContent;

    private String activityStatus;

    private Long volunteerHours;

}
