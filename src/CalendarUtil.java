import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * Calendar工具类
 * 舍入模式，默认CEIL格式，eg: 2.5秒在FLOOR模式下为2秒，在CEIL为3秒
 * @describtion 始终保证你的cal2.compareTo(cal1)>=0或者!cal2.before(cal1)才能保证计算结果的正确
 * @author WangFengLong
 * @Date 2020/11/17
 */
public final class CalendarUtil {
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    private CalendarUtil() {}
    /**
     * 获取年份差（向上取）
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static int getBetweenYear(Calendar cal1, Calendar cal2){
        return getBetweenYear(cal1,cal2, RoundingMode.CEILING);
    }

    public static int getBetweenYear(Calendar cal1, Calendar cal2, RoundingMode roundingMode){
        int betweenYear = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        Calendar cloneCal1 = (Calendar)cal1.clone();
        cloneCal1.add(Calendar.YEAR,betweenYear);
        if(roundingMode == RoundingMode.CEILING){
            // 如果第一个日期加上对应的年份后小于第二个日期则年份加一
            if(cloneCal1.before(cal2)){
                betweenYear++;
            }
        }else{
            // 如果第一个日期加上对应的年份后大于第二个日期则年份减一
            if(cloneCal1.after(cal2)){
                betweenYear--;
            }
        }
        return betweenYear;
    }


    /**
     * 获取月份差（向上取）
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static int getBetweenMonth(Calendar cal1,Calendar cal2){
        return getBetweenMonth(cal1,cal2,RoundingMode.CEILING);
    }

    public static int getBetweenMonth(Calendar cal1,Calendar cal2,RoundingMode roundingMode){
        int betweenYear = getBetweenYear(cal1, cal2, RoundingMode.FLOOR);
        int betweenMonth  = betweenYear * 12;
        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);
        // 2020.10.1 - 2020.11.1的日期差为11-1；2020.10.1 - 2021.1.1的日期差为（12-11）+1
        if(month2>=month1){
            betweenMonth += month2-month1;
        }else {
            betweenMonth += (12-month1)+month2;
        }
        Calendar cloneCal1 = (Calendar)cal1.clone();
        cloneCal1.add (Calendar.MONTH,betweenMonth);
        if(roundingMode == RoundingMode.CEILING){
            // 如果第一个日期加上对应的月份后小于第二个日期则月份加一
            if (cloneCal1.before(cal2)){
                betweenMonth++;
            }
        }else{
            // 如果第一个日期加上对应的年份后大于第二个日期则月份减一
            if (cloneCal1.after(cal2)){
                betweenMonth--;
            }
        }
        return betweenMonth;
    }

    /**
     * 获取天数差（向上取）
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static long getBetweenDay(Calendar cal1,Calendar cal2){
        return getBetweenDay(cal1,cal2,RoundingMode.CEILING);
    }

    public static long getBetweenDay(Calendar cal1,Calendar cal2,RoundingMode roundingMode){
        return betweenModeHandle(cal1,cal2,1000*60*60*24,roundingMode);
    }

    /**
     * 获取小时差（向上取）
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static long getBetweenHour(Calendar cal1,Calendar cal2){
        return getBetweenHour(cal1,cal2,RoundingMode.CEILING);
    }

    public static long getBetweenHour(Calendar cal1,Calendar cal2,RoundingMode roundingMode){
        return betweenModeHandle(cal1,cal2,1000*60*60,roundingMode);
    }

    /**
     * 获取分钟差（向上取）
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static long getBetweenMinute(Calendar cal1,Calendar cal2){
        return getBetweenMinute(cal1,cal2,RoundingMode.CEILING);
    }

    public static long getBetweenMinute(Calendar cal1,Calendar cal2,RoundingMode roundingMode){
        return betweenModeHandle(cal1,cal2,1000*60,roundingMode);
    }

    /**
     * 获取秒差（向上取）
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static long getBetweenSecond(Calendar cal1,Calendar cal2){
        return getBetweenSecond(cal1,cal2,RoundingMode.CEILING);
    }

    public static long getBetweenSecond(Calendar cal1,Calendar cal2,RoundingMode roundingMode){
        return betweenModeHandle(cal1,cal2,1000,roundingMode);
    }

    /**
     * 获取毫秒差
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static long getBetweenMillisecond(Calendar cal1,Calendar cal2){
        return cal2.getTimeInMillis() - cal1.getTimeInMillis();
    }
    /**
     * 天时分秒处理
     * @param cal1
     * @param cal2
     * @param unit
     * @param roundingMode
     * @return
     */
    private static long betweenModeHandle(Calendar cal1,Calendar cal2,long unit,RoundingMode roundingMode){
        long betweenMillisecond = getBetweenMillisecond(cal1,cal2);
        long betweenValue = betweenMillisecond/unit;
        if(roundingMode == RoundingMode.CEILING){
            if(betweenMillisecond%unit>0){
                betweenValue++;
            }
        }
        return betweenValue;
    }

    public static String toString(Calendar cal){
        return toString(cal,DATETIME_FORMAT);
    }

    public static String toString(Calendar cal,String pattern){
        Objects.requireNonNull(cal);
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }
}
