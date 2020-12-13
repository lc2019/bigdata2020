package com.jld.MRDemo.Demo3_mr.Demo_NLineInputFormat;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class NLineMap extends Mapper<LongWritable,Text,Text, IntWritable> {
    Text k  = new Text();
    IntWritable v = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取每一行
        String line = value.toString();
        //切割数据
        String[] ss = line.split(" ");

        //迭代写出
        for (String s : ss) {
            k.set(s);
            context.write(k,v);
        }
    }
}
