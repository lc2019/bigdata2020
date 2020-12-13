package com.jld.MRDemo.Demo3_mr.Demo_NLineInputFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class NLineDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.关联jar
        job.setJarByClass(NLineDriver.class);

        //3.关联map和reduce
        job.setMapperClass(NLineMap.class);
        job.setReducerClass(NLineRduce.class);
        //4.设置输出的mapreduce类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终输出的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //6.设置输入输出路径
        FileInputFormat.setInputPaths(job,new Path("/Users/mac/nline.txt"));
        FileOutputFormat.setOutputPath(job,new Path("/Users/mac/mrout/nline"));

        //设置输入格式NLineInput,不使用默认的切割规则
        job.setInputFormatClass(NLineInputFormat.class);
        //设置每3行一个切片
        NLineInputFormat.setNumLinesPerSplit(job,3);

        //7.提交job
        job.waitForCompletion(true);
    }
}
