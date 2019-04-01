package com.authorization.pub.config.confModel;

import org.springframework.security.crypto.password.PasswordEncoder;

public class RawEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        //不做任何加密处理
        return "{noop}" + rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        //rawPassword是前端传过来的密码，encodedPassword是数据库中查到的密码
        if (rawPassword.toString().equals(encodedPassword)) {
            return true;
        }
        return false;
    }
}
