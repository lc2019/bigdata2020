package MapReduce.Demo_mr_sort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class flowMapper extends Mapper<LongWritable, Text,Text, FlowBean3> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        FlowBean3 fb = new FlowBean3();
        Text k = new Text();
        //文本切割
        String[] split = value.toString().split("\t");
        //赋值
        fb.set(split[1],Long.parseLong(split[split.length-3]),Long.parseLong(split[split.length-2]));
       //手机号作为key
        k.set(split[1]);
        //bean作为值
        context.write(k,fb);
    }
}
