package com.wode.bangertong.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author Administrator
 */
public class TokenGenerator {
    public static String getRandomToken(int length) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }


    /**
     * 利用uuid生成64位token
     *
     * @return
     */
    public static String getUUIDToken() {
        StringBuilder sb = new StringBuilder(UUID.randomUUID().toString()).append(UUID.randomUUID().toString());
        return sb.toString().replace("-", "");
    }

    /**
     * 利用useID生成邀请码
     * @param id
     * @return
     */
    public static String getShToken(String id) {
        StringBuffer buf = new StringBuffer();
        byte[] b = id.getBytes();
        for (int i = 0; i < b.length; i++) {
            buf.append((char) (b[i] + 17));
        }
        return buf.toString();
    }
}