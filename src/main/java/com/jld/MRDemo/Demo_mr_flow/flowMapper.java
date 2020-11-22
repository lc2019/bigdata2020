package MapReduce.Demo_mr_flow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class flowMapper extends Mapper<LongWritable, Text,Text,FlowBean2> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        FlowBean2 fb = new FlowBean2();
        Text k = new Text();
        //文本切割
        String[] split = value.toString().split("\t");
        //赋值
        fb.set(Long.parseLong(split[split.length-3]),Long.parseLong(split[split.length-2]));
       //手机号作为key
        k.set(split[1]);
        //bean作为值
        context.write(k,fb);
    }
}
