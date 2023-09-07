package com.mmzz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmzz.domain.User;
import com.mmzz.mapper.UserMapper;
import com.mmzz.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
