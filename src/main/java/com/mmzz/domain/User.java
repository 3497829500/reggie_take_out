package com.mmzz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息(User)实体类
 *
 * @author makejava
 * @since 2023-06-08 15:34:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
private static final long serialVersionUID = 351512422213816654L;
/**主键*/private Long id;
/**姓名*/private String name;
/**手机号*/private String phone;
/**性别*/private String sex;
/**身份证号*/private String idNumber;
/**头像*/private String avatar;
/**状态 0:禁用，1:正常*/private Integer status;
}


