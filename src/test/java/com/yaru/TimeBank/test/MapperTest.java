package com.yaru.TimeBank.test;

import com.yaru.TimeBank.entity.Admin;
import com.yaru.TimeBank.entity.Volunteer;
import com.yaru.TimeBank.mapper.AdminMapper;
import com.yaru.TimeBank.mapper.VolunteerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class MapperTest {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private VolunteerMapper volunteerMapper;
    @Test
    public void test1(){
        List<Admin> admins = adminMapper.selectList(null);
        System.out.println(admins);
        List<Volunteer> volunteers = volunteerMapper.selectList(null);
        System.out.println(volunteers);
    }
}
