package com.yaru.TimeBank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminActivityMapper extends BaseMapper<ActivityDTO> {

    @Select("<script>" +
            "SELECT a.id AS activityId, a.activity_name AS activityName, a.activity_content AS activityContent, " +
            "v.name AS volunteerName, v.address AS volunteerAddress, a.activity_status AS activityStatus, a.volunteer_hours AS volunteerHours " +
            "FROM activity a " +
            "INNER JOIN volunteer v ON a.volunteer_id = v.id " +
            "WHERE a.activity_status = '待审核' " +
            "<if test='id != null and id != \"\"'>" +
            "   AND CAST(a.id AS CHAR) LIKE CONCAT('%', #{id}, '%')" +
            "</if>" +
            "<if test='name != null and name != \"\"'>" +
            "   AND a.activity_name LIKE CONCAT('%', #{name}, '%')" +
            "</if>" +
            "<if test='address != null and address != \"\"'>" +
            "   AND v.address LIKE CONCAT('%', #{address}, '%')" +
            "</if>" +
            "<if test='volunteerHours != null and volunteerHours != \"\"'>" +
            "   AND a.volunteer_hours LIKE CONCAT('%', #{volunteerHours}, '%')" +
            "</if>" +
            "</script>")
    IPage<ActivityDTO> getAdminActivityPage(Page<?> page,
                                            @Param("id") String id,
                                            @Param("name") String name,
                                            @Param("address") String address,
                                            @Param("volunteerHours") String volunteerHours);
}
