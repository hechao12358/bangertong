package com.wode.bangertong.service;

import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.common.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Result getUserPhonenumber(String code);

    Result mobileLogin(Integer loginType, String phone, String code, String deviceId, String clientId, Integer channel, String ip, Integer deviceType, Integer appType, Integer version, Integer source);

    Result mobileLogout(String token);

    Result setClientId(String token, String clientId);

    String getClientId(String userId);

    String getPhone(String userId);

    String getUserId(String phone);

    Result getInfo(String token);

    Result update(String token, User user);

    Result matches(String password);
}
