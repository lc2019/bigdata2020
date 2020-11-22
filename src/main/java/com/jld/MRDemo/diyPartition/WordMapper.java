package com.jld.MRDemo.diyPartition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author LIAO
 * create  2020-11-16 20:16
 * Mapper<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
 *     KEYIN:偏移量，当前读取到的一行数据相对与整个文本开头的偏移量。 LongWritable类型
 *     VALUEIN:当前读取到的一行的文本数据。 Text类型
 *     KEYOUT:指的是用户自定义的方法中返回的key类型，由用户根据业务逻辑决定。 Text类型
 *     VALUEOUT:指的是用户自定义的方法中返回的value类型，由用户根据业务逻辑决定。 LongWritable类型
 */
public class WordMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1、切分单词
        String[] words = value.toString().split(" ");

        //2、单词转换
        for (String word : words) {
            //3、写入到上下文
            context.write(new Text(word),new LongWritable(1));
        }

    }
}
