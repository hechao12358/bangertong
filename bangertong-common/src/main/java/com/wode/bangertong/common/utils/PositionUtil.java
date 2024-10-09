package com.wode.bangertong.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wode.bangertong.common.model.AmapGeoInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 计算距离
 */
public class PositionUtil {
    /**
     * 高德地图计算方法
     *
     * @param longitude1 第一点的经度
     * @param latitude1  第一点的纬度
     * @param longitude2 第二点的经度
     * @param latitude2  第二点的纬度
     * @return 返回的距离，单位m
     */
    public static Double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        if (longitude1 == 0 || latitude1 == 0 || latitude2 == 0 || longitude2 == 0) {
            return -1.0;
        }
        longitude1 *= 0.01745329251994329;
        latitude1 *= 0.01745329251994329;
        longitude2 *= 0.01745329251994329;
        latitude2 *= 0.01745329251994329;
        double var1 = Math.sin(longitude1);
        double var2 = Math.sin(latitude1);
        double var3 = Math.cos(longitude1);
        double var4 = Math.cos(latitude1);
        double var5 = Math.sin(longitude2);
        double var6 = Math.sin(latitude2);
        double var7 = Math.cos(longitude2);
        double var8 = Math.cos(latitude2);
        double[] var10 = new double[3];
        double[] var20 = new double[3];
        var10[0] = var4 * var3;
        var10[1] = var4 * var1;
        var10[2] = var2;
        var20[0] = var8 * var7;
        var20[1] = var8 * var5;
        var20[2] = var6;
        return Math.asin(Math.sqrt((var10[0] - var20[0]) * (var10[0] - var20[0]) + (var10[1] - var20[1]) * (var10[1] - var20[1]) + (var10[2] - var20[2]) * (var10[2] - var20[2])) / 2.0) * 1.27420015798544E7;
        // 结果四舍五入 保留2位小数
//        return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String distance(double distanceInMeters){
        String unit = "m";
        double distance = distanceInMeters;
        if(distanceInMeters > 1000){
            unit = "km";
            distance = convertToKm(distanceInMeters);

        }
        return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue() + unit;
    }

    public static double convertToKm(double distanceInMeters) {
        return Math.round(distanceInMeters / 1000.0 * 100.0) / 100.0;
    }

//    public static void main(String[] args) {
//
//        Double distance4 = getDistance(116.409824, 39.992952, 116.271335,39.953453);
//        System.out.println(distance4);
//        distance(distance4);
//    }
}
