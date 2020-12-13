package com.jld.MRDemo.Demo1_WordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * map阶段
 * reduce阶段
 * driver阶段-jar包
 * 获取配置信息，获取job对象实例
 * 指定本程序的jar包所在路径
 * 关联map/reduce业务类
 * 指定map输出kv
 * 指定数据输出kv
 * 指定job原始目录
 * 指定job输出目录
 * 提交作业
 */

/**
 * map reduce的类型
 * keyin   输入的数据
 * value in 输入的值
 * keyout 输出数据key类型
 * valueout 输出数据值类型
 */
public class wordCountRduce extends Reducer<Text, IntWritable, Text, IntWritable> {
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
     */

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        IntWritable v = new IntWritable();
        int sum = 0;
        //1.累计求和 k,v，汇总各个key的个数
        for (IntWritable value : values) {
            sum += value.get();
        }

        //2.写出,int类型的实例 获取get set，输出总个数
        v.set(sum);

        //3.写入
        context.write(key,v);
    }
}
