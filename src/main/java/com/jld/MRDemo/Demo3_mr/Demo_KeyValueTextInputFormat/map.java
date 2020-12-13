package com.jld.MRDemo.Demo3_mr.Demo_KeyValueTextInputFormat;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 统计输入文件中每一行的第一个单词相同的行数。
 * record读取到的是分割的好的数据
 * 键值对 kv都Text  banzhang nihao
 * 最终数据类型 banzhang 2
 * 这里直接在driver里面设置分隔符
 */
public class map extends Mapper<Text, Text,Text, IntWritable> {
    IntWritable v = new IntWritable(1);

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        context.write(key,v);
    }
}
