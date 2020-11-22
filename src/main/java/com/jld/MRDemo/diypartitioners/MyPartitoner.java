package MapReduce.diypartition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * key 单词
 * value 次数
 */
public class MyPartitoner extends Partitioner<Text, LongWritable> {

    @Override
    public int getPartition(Text text, LongWritable longWritable, int i) {
        //根据单词的长度进行判断
        if (text.toString().length() >= 5) {
            //写入0号分区
            return 0;
        } else {
            //写入1号分区
            return 1;
        }
    }
}
