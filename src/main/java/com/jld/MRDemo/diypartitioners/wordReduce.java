package MapReduce.diypartition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * keyin map阶段输出的key
 * valuein 数字
 * keyout  最终的单词 Text
 * valueout 结果就是统计的次数 LongWrite
 */
public class wordReduce extends Reducer<Text, LongWritable, Text, LongWritable> {
    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        //1.定义一个变量
        long count = 0;
        //2.迭代
        for (LongWritable value : values) {
            count += value.get();
        }
        //3.写入到上下文
        context.write(key, new LongWritable(count));
    }
}
