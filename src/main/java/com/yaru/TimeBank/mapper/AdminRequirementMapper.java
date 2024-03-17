package com.yaru.TimeBank.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.yaru.TimeBank.dto.AdminRequirementDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminRequirementMapper extends BaseMapper<AdminRequirementDTO> {
    @Select("SELECT r.id AS requestId, r.service_name AS serviceName, r.service_content AS serviceContent, " +
            "e.name AS elderName, " +
            "r.create_time AS createTime,r.last_time AS lastTime,r.duration_hours AS durationHours, " +
            "r.status AS status " +
            "FROM requirement r " +
            "LEFT JOIN elder e ON r.elder_id = e.id " +
            "LEFT JOIN volunteer v ON r.volunteer_id = v.id " +
            "WHERE #{name} IS NULL OR r.service_name LIKE CONCAT('%', #{name}, '%')")
    IPage<AdminRequirementDTO> selectVolunteerRequestPage(Page<?> page, @Param("name") String name);
}

