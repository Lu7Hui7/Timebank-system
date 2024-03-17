package com.yaru.TimeBank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.AdminActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminActivityMapper extends BaseMapper<AdminActivityDTO> {

    @Select("SELECT a.activity_id AS activityId, a.activity_name AS activityName, a.activity_content AS activityContent, " +
            "v.name AS volunteerName, v.address AS address, a.activity_status AS activityStatus, a.volunteer_hours AS volunteerHours " +
            "FROM activity a " +
            "INNER JOIN volunteer v ON a.volunteer_id = v.id " +
            "WHERE #{name} IS NULL OR a.activity_name LIKE CONCAT('%', #{name}, '%')")
    IPage<AdminActivityDTO> selectVolunteerActivityPage(Page<?> page, @Param("name") String name);
}
