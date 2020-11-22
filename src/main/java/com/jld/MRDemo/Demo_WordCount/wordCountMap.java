package com.jld.MRDemo.Demo_WordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

/**
 * map阶段
 * reduce阶段
 * driver阶段-jar包
 *  获取配置信息，获取job对象实例
 *  指定本程序的jar包所在路径
 *  关联map/reduce业务类
 *  指定map输出kv
 *  指定数据输出kv
 *  指定job原始目录
 *  指定job输出目录
 *  提交作业
 */

/**
 * map reduce的类型
 * keyin   输入的数据
 * value in 输入的值
 * keyout 输出数据key类型
 * valueout 输出数据值类型
 */
public  class wordCountMap extends Mapper<LongWritable, Text, Text, IntWritable>{
    /**
     *
     * @param key 行偏移量
     * @param value 一行的文本内容
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    /**
     * 避免内存浪费
     * k v(1)
     */
    Text k = new Text();
    IntWritable v = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取1行数据，将文本内容转行成string
        String line = value.toString();

        //2.切割单词 空格
        String[] words = line.split(" ");

        //3.循环写单词，将单词输出为《单词，1》的形式
        for (String word : words) {
            //set和get
            k.set(word);
            context.write(k,v);
        }
    }
}
