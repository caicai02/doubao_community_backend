package com.douyuehan.doubao.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: ZXW Date: 14-4-15 Time: 上午10:22 To change
 * this template use File | Settings | File Templates.
 */
public class DateUtils {

	public static Date parseDate(String str, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static String formatMillisecond(Long time, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(new Date(time));
	}

    public static Long string2Timestamp(String time, String pattern){
        Date date = parseDate(time, pattern);
        return date.getTime()/1000;
    }

    public static Long timeStr2Timestamp(String time){
        return string2Timestamp(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static Long timeStr3Timestamp(String time) {
        return string2Timestamp(time, "yyyy-MM-dd HH:mm");
    }

    public static Long timeStr4Timestamp(String time) {
        return string2Timestamp(time, "yyyy-MM-dd");
    }

    public static String addDays(String date, int d){
        Calendar cal = Calendar.getInstance();
        Date dd = parseDate(date, "yyyyMMdd");
        cal.setTime(dd);
        cal.add(Calendar.DATE, d);
        return formatDate(cal.getTime(), "yyyyMMdd");
    }

    public static int subtract(String d1, String d2){
        Date dd1 = parseDate(d1, "yyyyMMdd");
        Date dd2 = parseDate(d2, "yyyyMMdd");
        int d = (int)((dd2.getTime() - dd1.getTime())/(1000*3600*24));
        return d;
    }

    /**
     * @description 日期秒数转换成日期
     * @param time
     * @return String
     */
    public static String getDatebyTimeMillis(Long time, String pattern) {
        if (time != null) {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            String newdate = sf.format(time * 1000);
            return newdate;
        }
        return null;
    }

    public static String getToday(String pattern){
        return formatDate(new Date(), pattern);
    }

    public static String getYesterday(String pattern){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return formatDate(cal.getTime(), pattern);
    }

    /**
     * @description 返回当前时间的秒数
     * @return Long
     * @author don
     * @time 2014年9月1日 上午10:29:27
     */
    public static Long getTimeMillisNow() {
        return (Long) (System.currentTimeMillis() / 1000);
    }

    /***
     *  传入字符串日期转换成秒数
     * */
    public static Long getTimeMillisbyDate(String date, String pattern) throws ParseException
    {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(date)) {
            SimpleDateFormat sf = new SimpleDateFormat(pattern);
            try {
                Date newdate = sf.parse(date);
                return (Long) (newdate.getTime() / 1000);
            } catch (ParseException e) {
                throw e;
            }
        }
        return null;
    }

    /**
     * 获取任何一天0点的秒数
     *
     * @return
     */
    public static Long getAnyTodayStartSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() / 1000;
    }


    /**
     * 获取任何一天24点的秒数
     *
     * @return
     */
    public static Long getAnyTodayEndSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 获取任何月第一天
     * */
    public static Long getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        // 将秒至0
        calendar.set(Calendar.SECOND, 0);
        // 将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        // 获得当前月第一天
        return calendar.getTimeInMillis() / 1000;
    }


    /**
     * 获取任何月最后一天
     * */
    public static Long getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.DATE, -1);
        // calendar.set(Calendar.DAY_OF_MONTH, 31);
        // 将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        // 将分钟至0
        calendar.set(Calendar.MINUTE, 59);
        // 将秒至0
        calendar.set(Calendar.SECOND, 59);
        // 将毫秒至0
        calendar.set(Calendar.MILLISECOND, 999);
        // 获得当前月最后一天
        return calendar.getTimeInMillis() / 1000;
    }



    /***
     *
     *   获取指定月份的天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 说明:指定日期的开始时间Long
     * 参数:
     * 作者:ht
     * 创建时间:2016年5月31日
     */
    public static Long formatDateStartTimeMillisNow(String date) {
        String startTime = date + " 00:00:00";
        return DateUtils.timeStr2Timestamp(startTime);
    }
    /**
     * 说明:指定日期的结束时间Long
     * 参数:
     * 作者:ht
     * 创建时间:2016年5月31日
     */
    public static Long formatDateEndTimeMillisNow(String date) {
        String endTime = date + " 23:59:59";
        return DateUtils.timeStr2Timestamp(endTime);
    }


    /***
     * 几天后的时间
     * */
    private static long getBeforeDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) + days);// 让日期加N
        return calendar.getTimeInMillis() / 1000;
    }

    /**
     * 15天后
     * */
    public static long lastFifteenDay(){
        return getBeforeDate(-15);
    }


    /**
     * 上个月最后一天
     * yyyy-MM-dd
     * @return
     */
    public static String firstOfMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,0);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(calendar.getTime());
    }


    /**
     * 获取某个月的第一天时间
     * @param month
     * @return
     */
    public static long getMonthFirstDay(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis()/1000;
    }
    /**
     *  获取某个月的最后一天时间
     * @param month
     * @return
     */
    public static long getMonthLastDay(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month+1);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return calendar.getTimeInMillis()/1000;
    }

    /**
     * 获取本月第一天的时间
     * @return
     */
    public static long getFirstMonthDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 当月第一天
        return calendar.getTimeInMillis()/1000;
    }
    /**
     * 获取当前年
     * @return
     */
    public static int getYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取当前月
     * @return
     */
    public static int getMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 前1天
     * @return 毫秒数/1000
     */
    public static long yesterDay(){
        return getBeforeDate(-1);
    }
    /**
     * 前8天
     * @return 毫秒数/1000
     */
    public static long lastWeekDay(){
        return getBeforeDate(-8);
    }
    /**
     * 前31天
     * @return 毫秒数/1000
     */
    public static long lastMonthDay(){
        return getBeforeDate(-31);
    }
    /**
     * 前91天
     * @return 毫秒数/1000
     */
    public static long lastSeasonDay(){
        return getBeforeDate(-91);
    }
    /**
     * 前366天
     * @return 毫秒数/1000
     */
    public static long lastYearDay(){
        return getBeforeDate(-366);
    }


    /**
     * 获取以后的日期
     * @param nt 0:今天  1：明天。2：后天，以此类推
     * @return
     * @throws ParseException
     */
    public static String getNext(int nt) throws ParseException{
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sf.format(date);
        //通过日历获取下一天日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(sf.parse(nowDate));
        cal.add(Calendar.DAY_OF_YEAR, nt);
        String nextDate = sf.format(cal.getTime());
        return nextDate;
    }


    /**
     * 根据传入的日期返回对应的星期
     * @author mgj
     * @date 2016年2月26日下午3:05:53
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    public static String tochange(Long time) {
        if(time!=null){
            String times="";
            long time1 = DateUtils.getTimeMillisNow()-time;
            if(time1<60){
                times=time1+"秒钟前";
            }else if(60<=time1&&time1<3600){
                times=(time1/60)+"分钟前";
            }else if(3600<=time1&&time1<3600*24){
                times=(time1/3600)+"小时前";
            }else{
                times = DateUtils.getDatebyTimeMillis(time, "yyyy-MM-dd HH:mm:ss");
            }
            return times;
        }else{
            return "";
        }

    }

    public static String tochange2(Long time) {
        if(time!=null){
            return DateUtils.getDatebyTimeMillis(time, "yyyy-MM-dd HH:mm:ss");
        }else{
            return "";
        }

    }

    public static Long tochange3(String time) {
        String[] strings=time.split(":");
        Long t1=Long.valueOf(strings[0])*60*60;
        Long t2=Long.valueOf(strings[1])*60;
        Long t3=Long.valueOf(strings[2]);
        return t1+t2+t3;
    }

    /**
     * 获取当前时间戳
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
     * @param str 字符串
     * @param format 日期格式
     * @return 日期
     */
    public static Date strToDate(String str, String format) {
        if (null == str || "".equals(str)) {
            return null;
        }
        // 如果没有指定字符串转换的格式，则用默认格式进行转换
        if (null == format || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
       // System.out.println(tochange3("1:01:01"));
        System.out.println(timeStr4Timestamp("2020-2-14"));
        System.out.println(getDatebyTimeMillis(1581609600L,"yyyy-MM-dd"));
    }

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return 返回值单位为[s:秒]
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 方式二： 设定时间戳，倒计时
     */
    public static void time(long midTime,Integer status) {

        while (midTime > 0) {
            if (TrueOrNot(status)) {
                midTime--;
                long hh = midTime / 60 / 60 % 60;
                long mm = midTime / 60 % 60;
                long ss = midTime % 60;
                System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 方式二： 倒计时30分钟内客户确定或者取消
     */
    public static boolean TrueOrNot(Integer status){
        if(status == 1){
            return true;
        }
        return false;
    }

    /**
     *
     * @param nowTime   当前时间
     * @param startTime	开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * @comments 计算两个时间的时间差
     * @param strTime1
     * @param strTime2
     */
    public static Long getTimeDifference(String strTime1,String strTime2) {
        //格式日期格式，在此我用的是"2018-01-24 19:49:50"这种格式
        //可以更改为自己使用的格式，例如：yyyy/MM/dd HH:mm:ss 。。。
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l =0;
        try{
            Date now = df.parse(strTime1);
            Date date=df.parse(strTime2);
            l=now.getTime()+30*60*1000-date.getTime();       //获取时间差
            l=l/1000;
        }catch(Exception e){
            e.printStackTrace();
        }
        return l;
    }

}
