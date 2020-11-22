package MapReduce.Demo3_mr.Demo_KeyValueTextInputFormat;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 键值对 kv都Text  banzhang nihao
 * 最终数据类型 banzhang 2
 */
public class map extends Mapper<Text, Text,Text, IntWritable> {
    IntWritable v = new IntWritable(1);

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {

    //写出
        context.write(key,v);
    }
}
