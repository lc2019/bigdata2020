package MapReduce.Demo3_mr.Move;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MovieBean implements WritableComparable<MovieBean> {
    //    {"movie":"2797"， "rate" : "4"，"timestamp" : "978302039"， "uid": "1"}
    private String movie;
    private int rate;
    private long timeStamp;
    private String uid;

    public MovieBean() {
    }

    public MovieBean(String movie, int rate, long timeStamp, String uid) {
        this.movie = movie;
        this.rate = rate;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 序列化
     */
    @Override
    public void write(DataOutput Output) throws IOException {
        Output.writeUTF(this.movie);
        Output.writeInt(this.rate);
        Output.writeLong(this.timeStamp);
        Output.writeUTF(this.uid);
    }

    /**
     * 反序列化
     *
     * @param Input
     * @throws IOException
     */
    @Override
    public void readFields(DataInput Input) throws IOException {
        this.movie = Input.readUTF();
        this.rate = Input.readInt();
        this.timeStamp = Input.readLong();
        this.uid = Input.readUTF();
    }

    @Override
    public int compareTo(MovieBean o) {
        //相等就比较评分 ，不相等比较movieid
        return this.movie.compareTo(o.getMovie())==0?(o.getRate()-this.rate):this.movie.compareTo(o.getMovie());
    }
}
