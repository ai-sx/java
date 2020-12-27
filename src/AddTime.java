import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * 日期递增类（默认步长为1秒）
 * @author WangFengLong
 * @date 2020/11/14 12:58
 */
@Getter
public class  AddTime{
    public static final int MILLISECOND = 0;
    public static final int SECOND = 1;
    public static final int MINUTE = 2;
    public static final int HOUR = 3;
    private long value;
    @Setter
    private long step;

    public  AddTime(){
        this(1000,MILLISECOND);
    }
    public AddTime(long step){
        this(step,MILLISECOND);
    }
    public AddTime(long step,int type){
        this.step =  getStepByUnit(step,type);
        this.value = System.currentTimeMillis();
    }

    /**
     * 返回时间然后增加相应的步长
     * @return
     */
    public Date next(){
        Date date = new Date(this.value);
        this.value+=this.step;
        return date;
    }

    /**
     * 重置时间为当前时间
     */
    public void reset(){
        this.value = System.currentTimeMillis();
    }

    /**
     * 设置步长通过时间单位
     * @param step
     * @param type
     */
    public void setStep(long step,int type){
        this.step = getStepByUnit(step,type);
    }

    /**
     * 获得步长通过时间单位
     * @param step
     * @param type
     * @return
     */
    private long getStepByUnit(long step,int type){
        switch (type){
            case HOUR: return  step*1000*60*60;
            case MINUTE: return step*1000*60;
            case SECOND: return step*1000;
            case MILLISECOND:
            default:return step;
        }
    }
}
