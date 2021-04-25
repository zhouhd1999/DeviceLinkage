package com.zhd.device.management.component;

import com.alibaba.fastjson.JSONObject;
import com.zhd.common.utils.others.Uuid;
import com.zhd.device.management.service.IsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Token {

    @Autowired
    IsApiService isApiService;

    private String accessToken = "";
    private Long expireTime;

    public String getAccessToken(){
        if (accessToken.equals("") || System.currentTimeMillis() >= expireTime){
            boolean ret = getAccessTokenFromIS();
            if (!ret){
                System.out.println("get token error");
            }
        }
        return accessToken;
    }

    private boolean getAccessTokenFromIS(){
        String appKey = "e778682baa13464a9335f2be6699b251";
        String appSecret = "aedd65da008aa0a13bc8fbae1dc1b9b6";
        JSONObject object = isApiService.getAccessToken(appKey, appSecret);
        if (object.getString("code").equals("200")) {
            accessToken = object.getJSONObject("data").getString(("accessToken"));
            expireTime = object.getJSONObject("data").getLong("expireTime");
            return true;
        }else {
            return false;
        }
    }
}


