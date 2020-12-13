package com.jld.MRDemo.demo4_shuffle.Demo_mr_flow_sort_all;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class flowMapper1 extends Mapper<LongWritable, Text, FlowBean3, Text> {
    Text v = new Text();


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 1 获取一行
        String line = value.toString();

        // 2 截取
        String[] fields = line.split("\t");

        // 3 封装对象

        String upFlow = fields[fields.length - 3];
        String downFlow = fields[fields.length - 2];
        FlowBean3 fb = new FlowBean3(Long.parseLong(upFlow), Long.parseLong(downFlow));

        //手机号作为key
        v.set(fields[1]);

        // 4 输出
        context.write(fb, v);
    }
}
