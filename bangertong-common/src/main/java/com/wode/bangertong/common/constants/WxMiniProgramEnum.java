package com.wode.bangertong.common.constants;


/**
 *
 */
public enum WxMiniProgramEnum {

    SCREEN("wxf5f273cc7d4b860e", "855fd1ad7711cbfad93c80efc774314c"),   //沃民筛查小程序
    GREEN("wx39804de583408678", "2178bf55b5b60383bc0ed6aa7e66d843");    //沃德报告小程序
    // 成员变量
    private String appId;
    private String appSecret;

    // 构造方法 ,赋值给成员变量
    private WxMiniProgramEnum(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
