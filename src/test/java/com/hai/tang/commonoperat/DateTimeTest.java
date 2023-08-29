package com.hai.tang.commonoperat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

@DisplayName("日期时间测试类")
public class DateTimeTest {

    @Test
    @DisplayName("获取当前日期和时间")
    public void getCurrentTime() {
        //LocalDate获取当前 年月日
        LocalDate nowdata = LocalDate.now();
        System.out.println(nowdata);   //2021-04-06 获取当前年月日
        System.out.println(nowdata.getYear());//2021 获取当前年份
        System.out.println(nowdata.getMonth().getValue());//4 获取当前月份
        System.out.println(nowdata.getDayOfMonth());//6 获取今天几号
        System.out.println(nowdata.getDayOfWeek().getValue());//2 获取今天星期几
        System.out.println(nowdata.getDayOfYear());//96 获取今天是今年的第几天
        System.out.println("-------------------------------------------------");

        //LocalTime获取当前 时分秒
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);//  获取当前的时间
        System.out.println(nowTime.getHour());// 获取当前时间的小时
        System.out.println(nowTime.getMinute());// 获取当前时间的分钟
        System.out.println(nowTime.getSecond());// 获取当前时间的秒
        System.out.println("-------------------------------------------------");

        //LocalDateTime获取当前 年月日时分秒
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);//  获取当前的年月日时分秒
        System.out.println(localDateTime.getYear());// 获取当前年份
        System.out.println(localDateTime.getMonth().getValue());// 获取当前月
        System.out.println(localDateTime.getDayOfMonth());// 获取当前日
        System.out.println(localDateTime.getHour());// 获取当前时
        System.out.println(localDateTime.getMinute());// 获取当前分
        System.out.println(localDateTime.getSecond());// 获取当前秒
        System.out.println(localDateTime.getDayOfYear());// 今天是今年的第几天
        System.out.println(localDateTime.getDayOfWeek().getValue());// 今天是星期几
        System.out.println("-------------------------------------------------");
    }

    @Test
    @DisplayName("日期和时间格式化")
    public void dateTimeFormatter() {
        //1.LocalDate格式化
        LocalDate getdata = LocalDate.now();
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        System.out.println(getdata.format(f1));//2023.08.29

        //2.LocalTime格式化
        LocalTime getTime = LocalTime.now();
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH-mm-ss");
        System.out.println(getTime.format(f2));//15-22-41

        //3.LocalDateTime 格式化
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter f3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(f3));//2023-08-29 15:22:41

        LocalDateTime localDateTime1 = LocalDateTime.now();
        DateTimeFormatter f4 = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm");
        System.out.println(localDateTime1.format(f4));//2023.08.29-15:22

        //添加时区
        LocalDateTime localDateTime3 = LocalDateTime.now();
        OffsetDateTime date = localDateTime3.atOffset(ZoneOffset.ofHours(+8));
        System.out.println(date);//2023-08-29T15:22:41.125+08:00
    }

    @Test
    @DisplayName("字符串与LocalDate、LocalTime、LocalDateTime之间的互换")
    public void dateTimeToString() {
        //日期字符串转LocalDate类型
        LocalDate getdata = LocalDate.parse("2023-08-29");//年月日用-分开，月份和日期如果小于10要补零
        System.out.println(getdata);//2023-08-29

        //20220805 转 2022-08-05
        String dayAfterTommorrow = "20230805";
        //除了DateTimeFormatter.BASIC_ISO_DATE格式DateTimeFormatter里还有其他格式可选择
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println("格式化后的日期为:  " + formatted);//格式化后的日期为:  2023-08-05

        //时间字符串转LocalTime类型
        LocalTime gettime = LocalTime.parse("16:59:09");//时分秒用:分开，时分秒如果小于10要补零
        System.out.println(gettime);//16:59:09

        //日期时间字符串转LocalDateTime类型（年月日之间要用-分割，时分秒用:分割，日期和时间之间用T分割）
        LocalDateTime localDateTime = LocalDateTime.parse("2023-04-06T10:13:12");
        System.out.println(localDateTime);//2023-04-06T10:13:12

        //LocalDate、LocalTime、LocalDateTime类型转为字符串直接toString
        LocalDate nowdata = LocalDate.now();
        System.out.println(nowdata);//2023-08-29
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime.toString());//15:28:22.487
        LocalDateTime localDateTime1 = LocalDateTime.now();
        System.out.println(localDateTime1);//2023-08-29T15:28:22.487
    }

    @Test
    @DisplayName("手动设置指定日期时间")
    public void setDateTime() {
        LocalDateTime ofTime = LocalDateTime.of(2023, 8, 29, 8, 8, 8);
        System.out.println("当前精确时间：" + ofTime);

        LocalDate localDate = LocalDate.of(2023, 8, 29);
        System.out.println("当前日期：" + localDate);

        LocalTime localTime = LocalTime.of(12, 1, 1);
        System.out.println("当天时间：" + localTime);
    }

    @Test
    @DisplayName("日期和时间的加减")
    public void DateTimeCalculate() {
        LocalDate today = LocalDate.now();
        System.out.println("今天的日期为:" + today);
        //plus() 方法用来增加天、周、月，ChronoUnit 类声明了这些时间单位
        //可以用同样的方法增加 1 个月、1 年、1 小时、1 分钟甚至一个世纪
        LocalDate nextWeek = today.plus(2, ChronoUnit.WEEKS);
        System.out.println("两周后的日期为:" + nextWeek);

        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间：" + now);
        LocalDateTime plusTime = now.plusMonths(1).plusDays(4).plusHours(1).plusMinutes(1).plusSeconds(1);
        LocalTime plusTime1 = now.plusHours(3).toLocalTime();
        System.out.println("增加1月4天1小时1分钟1秒时间后：" + plusTime);
        System.out.println("3个小时后的时间为:" + plusTime1);
        LocalDateTime minusTime = now.minusMonths(2).minusDays(3);
        System.out.println("减少2个月3天时间后：" + minusTime);
    }

    @Test
    @DisplayName("判断平年和闰年")
    public void isLeapYear() {
        //指定具体年月日
        LocalDate localDate = LocalDate.of(1999, 1, 7);//设置指定日期
        boolean bo = localDate.isLeapYear();//闰年为ture,平年是false
        System.out.println(bo);//false

        //指定年份
        boolean bo1 = IsoChronology.INSTANCE.isLeapYear(1999);//指定年份
        System.out.println(bo);//false

        //LocalDateTime和LocalDate 判断平年闰年
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间：" + now);
        System.out.println("今年是否闰年：" + Year.isLeap(now.getYear()));

        LocalDate data = LocalDate.now();
        System.out.println("当前日期：" + data);
        System.out.println("今年是否闰年：" + Year.isLeap(data.getYear()));
    }

    @Test
    @DisplayName("判断指定日期是星期几")
    public void judgmentWeek() {
        //判断指定日期星期几
        LocalDate localDate = LocalDate.of(2021, 1, 25);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(dayOfWeek);//MONDAY

        //判断是不是星期一
        LocalDate localDate1 = LocalDate.of(2021, 1, 25);
        System.out.println(localDate1.getDayOfWeek() == DayOfWeek.MONDAY);//true

        //判断今天是星期几
        DayOfWeek dayOfWeek1 = LocalDate.now().getDayOfWeek();
        System.out.println(dayOfWeek1);
    }

    @Test
    @DisplayName("计算指定日期的月份有多少天")
    public void monthDayCalculate() {
        //计算指定日期的月份有多少天
        LocalDate endDate = LocalDate.of(2021, 2, 1);
        int monthDay = endDate.lengthOfMonth();
        System.out.println(monthDay);//输出28，2021年2月有28天

        //获取本月最后一天的时间或日期：
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间：" + now);
        LocalDateTime lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("本月最后一天的当前时间:" + lastDay);

        LocalDate nowData = LocalDate.now();
        System.out.println("当前日期：" + now);
        LocalDate lastDay1 = nowData.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("本月最后一天:" + lastDay1);
    }

    @Test
    @DisplayName("比较两个时间的早晚")
    public void compareDateTime() {
        LocalTime gettimeStart = LocalTime.parse("00:01:40");
        LocalTime gettimeEnd = LocalTime.parse("00:03:20");
        //-1表示早于，1表示晚于，0则相等
        int value = gettimeStart.compareTo(gettimeEnd);
        System.out.println(value);
    }

    @Test
    @DisplayName("比较两个日期的早晚")
    public void compareDate() {
        LocalDate startDate = LocalDate.of(2020, 1, 18);
        LocalDate endDate = LocalDate.of(2021, 5, 17);
        //startDate是否早于endDate
        System.out.println(startDate.isBefore(endDate));//true
        //endDate是否晚于startDate
        System.out.println(endDate.isAfter(startDate));//true
        //startDate是否等于endDate
        System.out.println(startDate.equals(endDate));//false
    }

    @Test
    @DisplayName("比较两个日期时间的早晚")
    public void compareLocalDateTime() {
        LocalDateTime date = LocalDateTime.parse("2019-03-03T12:30:30");
        LocalDateTime date1 = LocalDateTime.parse("2017-03-03T12:30:30");
        System.out.println(date.isAfter(date1));//true
        System.out.println(date.isBefore(date1));//false
        System.out.println(date.isEqual(date1));//false
    }

    @Test
    @DisplayName("计算两个时间相差多久（倒计时）")
    public void calculateTimeDifference() {
        LocalTime gettimeStart = LocalTime.parse("00:01:40");
        LocalTime gettimeEnd = LocalTime.parse("00:03:20");
        System.out.println("总的相差: " + HOURS.between(gettimeStart, gettimeEnd) + "小时");//总的相差: 0小时
        System.out.println("总的相差: " + MINUTES.between(gettimeStart, gettimeEnd) + "分钟");//总的相差: 1分钟
        System.out.println("总的相差: " + SECONDS.between(gettimeStart, gettimeEnd) + "秒");//总的相差: 100秒

        //计算两个时间的差
        String diffTime = calculationEndTime("02:04:49", "18:07:11");
        System.out.println("相差: " + diffTime);
    }

    /**
     * 计算两个时间的时间差
     *
     * @param startime 开始时间，如：00:01:09
     * @param endtime  结束时间，如：00:08:27
     * @return 返回xx:xx:xx形式，如：00:07:18
     */
    public static String calculationEndTime(String startime, String endtime) {
        LocalTime timeStart = LocalTime.parse(startime);
        LocalTime timeEnd = LocalTime.parse(endtime);
        long hour = HOURS.between(timeStart, timeEnd);
        long minutes = MINUTES.between(timeStart, timeEnd);
        long seconds = SECONDS.between(timeStart, timeEnd);
        minutes = minutes > 59 ? minutes % 60 : minutes;
        String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
        String minutesStr = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        long getSeconds = seconds - (hour * 60 + minutes) * 60;
        String secondsStr = getSeconds < 10 ? "0" + getSeconds : String.valueOf(getSeconds);
        return hourStr + ":" + minutesStr + ":" + secondsStr;
    }

    @Test
    @DisplayName("计算两个日期相隔多久")
    public void calculateDateDifference() {
        LocalDate startDate = LocalDate.of(2023, 8, 29);
        LocalDate endDate = LocalDate.of(2030, 10, 2);
        System.out.println("总相差的天数:" + startDate.until(endDate, ChronoUnit.DAYS));//总相差的天数:2591
        System.out.println("总相差的月数:" + startDate.until(endDate, ChronoUnit.MONTHS));//总相差的月数:85
        System.out.println("总相差的年数:" + startDate.until(endDate, ChronoUnit.YEARS));//总相差的年数:7
        Period period = Period.between(startDate, endDate);
        System.out.println("相差:" + period.getYears() + " 年 " + period.getMonths() + " 个月 " + period.getDays() + " 天");  //相差:7 年 1 个月 3 天
    }

    @Test
    @DisplayName("计算某年某月有几个星期五")
    public void calculateFridayCount() {
        YearMonth yearMonth = YearMonth.of(2023, 6);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate first = startOfMonth.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        LocalDate last = startOfMonth.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
        int count = (last.getDayOfMonth() - first.getDayOfMonth()) / 7 + 1;
        System.out.println("2023年6月有" + count + "个星期五");
    }

    @Test
    @DisplayName("获取毫秒级时间戳")
    public void getTimeStamp() {
        //获取时间戳方法1
        Instant timestamp = Instant.now();
        System.out.println("Instant获取时间戳：" + timestamp.toEpochMilli());
        //获取时间戳方法2
        Clock clock = Clock.systemUTC();
        System.out.println("Clock时间戳 : " + clock.millis());
        //获取时间戳方法3
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("defaultClock时间戳: " + defaultClock.millis());

        //Clock转LocalDateTime
        LocalDateTime data1 = LocalDateTime.now(defaultClock);
        System.out.println("时间戳转化为时间" + data1);//时间戳转化为时间 2023-08-29T16:08:16.910
    }


    @Test
    @DisplayName("LocalDateTime与时间戳的转换")
    public void LocalDateTimeconvertStamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(localDateTime);

        //转为毫秒级时间戳
        long milliSecondStamp = ts.getTime();
        System.out.println(milliSecondStamp);
        //转为秒级时间戳
        Instant instant = ts.toInstant();
        long secondStamp = instant.getEpochSecond();
        System.out.println(secondStamp);

        //毫秒级时间戳转LocalDateTime
        long milliSecondStamp1 = 1693296810007L;
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime1 = Instant.ofEpochMilli(milliSecondStamp1).atZone(zoneId).toLocalDateTime();
        System.out.println(localDateTime1);
        //级时间戳转LocalDateTime
        long secondStamp1 = 1693296810L;
        ZoneId zoneId1 = ZoneId.systemDefault();
        LocalDateTime localDateTime2 = Instant.ofEpochSecond(secondStamp1).atZone(zoneId1).toLocalDateTime();
        System.out.println(localDateTime2);
    }

    @Test
    @DisplayName("Date 转 LocalDateTime")
    public void dateToLocalDateTime() {
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println(localDateTime);
    }

    @Test
    @DisplayName("LocalDateTime 转 Date")
    public void localDateTimeToDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date from = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(from);
    }
}
