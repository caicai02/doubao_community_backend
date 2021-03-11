package com.douyuehan.doubao.utils.PhoneMessageUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by zxw on 2015/8/24.
 */
public class NumberUtils {

    /***
     * 生成固定位数数字随机数
     * @param length
     * @return
     */
    public static String generateRandomCode(int length) {
        String base = "0123456789";
        return generateRandomString(length, base);
    }

    public static String generateRandomString(int length, String base) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /****
     * 保留小数点后scale位
     *
     */
    public static double halfUp(double num, Integer scale){
        BigDecimal n = BigDecimal.valueOf(num);
        return n.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }



    /**
     * 保留小数点后scale位
     * @author mgj
     * @date 2016年2月26日下午2:50:35
     * @param num
     * @param scale 要保留的位数(数值越大, 误差越大)
     * @return
     */
    public static double round(double num, int scale){
        double s = Math.pow(10, scale);
        return (double)Math.round(num*s)/s;
    }

}
