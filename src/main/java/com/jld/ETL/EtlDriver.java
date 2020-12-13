package com.jld.ETL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * * driver阶段-jar包
 * *  获取配置信息，获取job对象实例
 * *  指定本程序的jar包所在路径
 * *  关联map/reduce业务类
 * *  指定map输出kv
 * *  指定数据输出kv
 * *  指定job原始目录
 * *  指定job输出目录
 * *  提交作业
 */
public class EtlDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar的位置
        job.setJarByClass(EtlDriver.class);

        //3.关联map和reducer
        job.setMapperClass(EtlMap.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setNumReduceTasks(0);

        //6.设置输入和输出的路径
        FileInputFormat.setInputPaths(job, new Path("G:\\尚硅谷大数据\\六、尚硅谷 2019-2020 最新大数据课程 - Hive 框架\\Hive\\2.资料\\04_data\\guiliVideo\\video\\2008\\0222"));
        FileOutputFormat.setOutputPath(job, new Path("e:/mr/gl"));

        //7.提交job任务
        job.waitForCompletion(true);
    }
}
