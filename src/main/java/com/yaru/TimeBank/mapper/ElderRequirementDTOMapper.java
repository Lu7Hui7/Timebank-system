package com.yaru.TimeBank.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaru.TimeBank.dto.RequirementDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ElderRequirementDTOMapper {
    @Select("<script>" +
            "SELECT r.id AS requestId, r.service_name AS serviceName, r.service_content AS serviceContent, " +
            "e.name AS elderName, e.address AS address, " +
            "r.create_time AS createTime, r.last_time AS lastTime, r.duration_hours AS durationHours, " +
            "r.status AS status, COALESCE(v.name, 'null') AS volunteerName " + // 使用COALESCE处理volunteer_name可能为空的情况
            "FROM requirement r " +
            "LEFT JOIN elder e ON r.elder_id = e.id " +
            "LEFT JOIN volunteer v ON r.volunteer_id = v.id " +
            "WHERE 1=1 " +
            "<if test='elderId != null'>" +
            "   AND e.id = #{elderId} " + // 添加老人ID的条件
            "</if>" +
            "<if test='serviceName != null and serviceName != \"\"'>" +
            "   AND r.service_name LIKE CONCAT('%', #{serviceName}, '%')" +
            "</if>" +
            "<if test='address != null and address != \"\"'>" +
            "   AND e.address LIKE CONCAT('%', #{address}, '%')" +
            "</if>" +
            "<if test='durationHours != null and durationHours != \"\"'>" +
            "   AND r.duration_hours LIKE CONCAT('%', #{durationHours}, '%')" +
            "</if>" +
            "<if test='id != null and id != \"\"'>" +
            "   AND CAST(r.id AS CHAR) LIKE CONCAT('%', #{id}, '%')" +
            "</if>" +
            "</script>")
    IPage<RequirementDTO> getElderRequestPage(Page<?> page,
                                              @Param("elderId") int elderId, // 添加老人ID参数
                                              @Param("serviceName") String serviceName,
                                              @Param("address") String address,
                                              @Param("durationHours") String durationHours,
                                              @Param("id") String id);
}
