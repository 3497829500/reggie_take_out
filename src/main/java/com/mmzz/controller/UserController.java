package com.mmzz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mmzz.common.R;
import com.mmzz.domain.User;
import com.mmzz.service.UserService;
import com.mmzz.utils.SMSUtils;
import com.mmzz.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        //验证手机号是否注册
        if (StringUtils.isNotBlank(phone)){
            //生成4位数验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码为：{}",code);

            //调用阿里云提供的短信服务测试API完成发送短信
            SMSUtils.sendMessage("吉瑞外卖","SMS_244420771",phone,code);

            //将验证码存入session
            session.setAttribute(phone,code);
            return R.success("发送成功");
        }

        return R.error("发送失败");
    }

    /**
     * 登录移动端
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();

        //获取验证码
        String code = map.get("code").toString();

        //从session中获取验证码
        Object attribute = session.getAttribute(phone);

        //判断验证码是否正确
        if (attribute != null && attribute.equals(code)) {
            //验证码正确
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断用户是否为新用户，如果是就自动注册
                user=new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }


}
