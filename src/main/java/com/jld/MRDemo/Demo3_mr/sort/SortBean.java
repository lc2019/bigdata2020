package MapReduce.Demo3_mr.sort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SortBean implements WritableComparable<SortBean> {
    private String word;
    private int num;

    public SortBean() {
    }

    public SortBean(String word, int num) {
        this.word = word;
        this.num = num;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return word + "\t" + num;
    }

    /**
     * 定义比较器 指定排序规则
     * 第一列按照字典顺序排序 word
     * 第二列按照num进行排序
     */

    @Override
    public int compareTo(SortBean o) {
        //对word排序
        int i = this.word.compareTo(o.word);
        //如果第一列相同对num排序
        if (i == 0) {
            return this.num - o.num;
        }
        return i;
    }

    //实现序列化
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(word);
        out.writeInt(num);
    }

    //实现反序列化
    @Override
    public void readFields(DataInput in) throws IOException {
        this.word = in.readUTF();
        this.num = in.readInt();
    }

}
