package com.jld.MRDemo.demo4_shuffle.Demo_mr_distinct;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class map extends Mapper<LongWritable, Text,Text, NullWritable>  {

    @Override
    protected void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        Text k = new Text();
        String[] split = value.toString().split(",");
        k.set(split[0]);
        context.write(k, NullWritable.get());
    }
}
