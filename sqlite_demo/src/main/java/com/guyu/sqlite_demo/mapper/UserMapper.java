package com.guyu.sqlite_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guyu.sqlite_demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 继承 BaseMapper 后，自动拥有 CRUD 方法
}
