package com.guyu.sqlite_demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user") // 指定表名（若表名与类名一致可省略）
public class User {
    @TableId(type = IdType.AUTO) // 主键自增（SQLite 需配置主键）
    private Long id;
    private String name;
    private Date birthday;
}
