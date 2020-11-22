package MapReduce.Demo3_mr.sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SortMapper extends Mapper<LongWritable, Text,SortBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //行文本数据拆分并将数据封装到bean对象
        String[] split = value.toString().split("\t");
        SortBean sortBean = new SortBean();
        sortBean.setWord(split[0]);
        sortBean.setNum(Integer.parseInt(split[1]));

        //写入bean 值为Null
        context.write(sortBean, NullWritable.get());
    }
}
