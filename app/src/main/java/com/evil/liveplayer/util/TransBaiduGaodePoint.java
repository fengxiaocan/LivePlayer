package com.evil.liveplayer.util;

import java.math.BigDecimal;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 28/6/18
 * @desc ...
 */
public class TransBaiduGaodePoint {
    private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    /**
     * 对double类型数据保留小数点后多少位
     * 高德地图转码返回的就是 小数点后6位，为了统一封装一下
     *
     * @param digit 位数
     * @param in 输入
     * @return 保留小数位后的数
     */
    static double dataDigit(int digit,double in) {
        return new BigDecimal(in).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 将火星坐标转变成百度坐标
     *
     * @param longitude 火星坐标（高德、腾讯地图坐标等）
     * @param latitude 火星坐标（高德、腾讯地图坐标等）
     * @return 百度坐标
     */

    public static double[] gaode_to_baidu(double longitude,double latitude) {
        double x = longitude;
        double y = latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y,x) + 0.000003 * Math.cos(x * x_pi);
        return new double[]{
                dataDigit(6,z * Math.sin(theta) + 0.006),dataDigit(6,z * Math.cos(theta) + 0.0065)
        };
    }

    //    /**
    //     * 将百度坐标转变成火星坐标
    //     *
    //     * @param lngLat_bd 百度坐标（百度地图坐标）
    //     * @return 火星坐标(高德、腾讯地图等)
    //     */
    //    public static LatLng baidu_to_gaode(LatLng lngLat_bd) {
    //        double x = lngLat_bd.longitude - 0.0065, y = lngLat_bd.latitude - 0.006;
    //        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
    //        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
    //        return new LatLng(dataDigit(6, z * Math.sin(theta)), dataDigit(6, z * Math.cos(theta)));
    //    }

    //    /**
    //     * 将[图片google坐标]gps 转百度坐标
    //     * @param sourceLatLng
    //     * @return
    //     */
    //    public static LatLng GoogleGps_to_BaiduGps(LatLng sourceLatLng) {
    //        CoordinateConverter converter = new CoordinateConverter();
    //        converter.from(CoordinateConverter.CoordType.GPS);
    //        // sourceLatLng待转换坐标
    //        converter.coord(sourceLatLng);
    //        return converter.convert();
    //    }
}
