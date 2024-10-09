package com.wode.bangertong.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassWordUtil {
    /**
     * 用户密码加密
     */
    public static String encode(String pwd){
        return new BCryptPasswordEncoder().encode(pwd);
    }
    /**
     * 校验密码
     */
    public static boolean matches(String password, String encodedPassword){
        return new BCryptPasswordEncoder().matches(password,encodedPassword);
    }

//    public static void main(String[] args) {
//         System.out.println(encode("123456"));
////        System.out.println(matches("123456","$2a$10$VDz7ueB//s7tSwpxqQi.7.xvJgbawG2AI7ab1SxMCQMTxAleUlZM."));
//    }

}
