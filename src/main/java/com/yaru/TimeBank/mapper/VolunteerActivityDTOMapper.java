package com.yaru.TimeBank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.ActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VolunteerActivityDTOMapper extends BaseMapper<ActivityDTO> {
    @Select("<script>" +
            "SELECT a.id AS activityId, a.activity_name AS activityName, a.activity_content AS activityContent, " +
            "v.name AS volunteerName, v.address AS volunteerAddress, v.phone AS volunteerPhone, a.activity_status AS activityStatus, a.volunteer_hours AS volunteerHours " +
            "FROM activity a " +
            "INNER JOIN volunteer v ON a.volunteer_id = v.id " +
            "WHERE 1=1 " +
            "<if test='volunteerId != null'>" +
            "   AND a.id = #{volunteerId} " + // 添加志愿者ID的条件
            "</if>" +
            "<if test='activityName != null and activityName != \"\"'>" +
            "   AND a.activity_name LIKE CONCAT('%', #{activityName}, '%')" +
            "</if>" +
            "<if test='address != null and address != \"\"'>" +
            "   AND v.address LIKE CONCAT('%', #{address}, '%')" +
            "</if>" +
            "<if test='volunteerHours != null and volunteerHours != \"\"'>" +
            "   AND a.volunteer_hours LIKE CONCAT('%', #{volunteerHours}, '%')" +
            "</if>" +
            "<if test='id != null and id != \"\"'>" +
            "   AND CAST(a.id AS CHAR) LIKE CONCAT('%', #{id}, '%')" +
            "</if>" +
            "</script>")
    IPage<ActivityDTO> getVolunteerActivityPage(Page<?> page,
                                       @Param("volunteerId") int volunteerId, // 添加老人ID参数
                                       @Param("activityName") String activityName,
                                       @Param("address") String address,
                                       @Param("volunteerHours") String volunteerHours,
                                       @Param("id") String id);
}
