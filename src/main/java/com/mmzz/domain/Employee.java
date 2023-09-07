package com.mmzz.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工信息(Employee)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
private static final long serialVersionUID = -35635033410790388L;
/**主键*/private Long id;
/**姓名*/private String name;
/**用户名*/private String username;
/**密码*/private String password;
/**手机号*/private String phone;
/**性别*/private String sex;
/**身份证号*/private String idNumber;
/**状态 0:禁用，1:正常*/private Integer status;

    /**创建时间*/
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**更新时间*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**创建人*/
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**修改人*/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}


