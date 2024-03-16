package com.yaru.TimeBank.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * 老人需求表实体
 */
@Data
public class Requirement {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    private String serviceContent;


    // 使用 @ManyToOne 注解表示与 Elder 实体类的多对一关系，并指定关联字段
    @ManyToOne
    @JoinColumn(name = "elder_id", referencedColumnName = "id")
    private Long elderId;

    // 使用 @ManyToOne 注解表示与 Volunteer 实体类的多对一关系，并指定关联字段
    @ManyToOne
    @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
    private Long volunteerId;

    private LocalDate lastTime;

    private LocalDate createTime;

    private Long durationHours;

    private LocalDate reviewTime;

    private String status;
}
