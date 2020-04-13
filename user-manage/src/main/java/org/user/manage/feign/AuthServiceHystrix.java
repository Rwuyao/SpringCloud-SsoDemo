package org.user.manage.feign;

import org.springframework.stereotype.Component;
import org.user.manage.entity.JWT;

@Component
public class AuthServiceHystrix implements AuthService {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }
}
