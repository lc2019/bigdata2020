package com.jld.MRDemo.Demo3_mr.Demo_FileInputFormat;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * k1 v1
 * NullWritable BytesWritable
 * k2 v2
 * Text BytesWritable
 */
public class map extends Mapper<NullWritable, BytesWritable, Text,BytesWritable> {
    @Override
    protected void map(NullWritable key, BytesWritable value, Context context) throws IOException, InterruptedException {
       //获取文件名称
        FileSplit inputSplit = (FileSplit)context.getInputSplit();
        String filename = inputSplit.getPath().getName();
        //写入文件
        context.write(new Text(filename),value);
    }
}
