package com.jld.MRDemo.demo4_shuffle.Demo_mr_flow_sort_all;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flowReduce1 extends Reducer<FlowBean3, Text, Text, FlowBean3> {
    @Override
    protected void reduce(FlowBean3 key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //循环写入，避免出现重复
        for (Text text : values) {
            context.write(text, key);
        }
    }
}
