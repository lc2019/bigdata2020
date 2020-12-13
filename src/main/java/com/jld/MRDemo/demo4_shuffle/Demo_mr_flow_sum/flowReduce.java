package com.jld.MRDemo.demo4_shuffle.Demo_mr_flow_sum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flowReduce extends Reducer<Text, FlowBean2, Text, FlowBean2> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean2> values, Context context) throws IOException, InterruptedException {
        //创建bean对象

        long up = 0;
        long down = 0;

        for (FlowBean2 value : values) {
            //对象的get方法
            up += value.getUpFlow();
            down += value.getdFlow();
        }
        //写入
        FlowBean2 bean = new FlowBean2(up, down);
        context.write(key, bean);
    }
}
