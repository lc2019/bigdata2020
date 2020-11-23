package com.jld.MRDemo.Demo3_mr.Demo_KeyValueTextInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 改变了读取顺序的规则
 */
public class driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job对象
        Configuration conf = new Configuration();
        //设置kv的分隔符
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, " ");


        Job job = Job.getInstance(conf);

        //设置最终的输出格式
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        //2.获取jar存储路径
        job.setJarByClass(driver.class);
        //3.管理mapper
        job.setMapperClass(map.class);
        job.setReducerClass(redurce.class);
        //4.设置map和reduce的输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        //6.设置输出路径
        FileInputFormat.setInputPaths(job, new Path("/Users/mac/kv.txt"));
        FileOutputFormat.setOutputPath(job, new Path("/Users/mac/mrout/outputkv"));
        //7.提交job
        job.waitForCompletion(true);

    }
}
