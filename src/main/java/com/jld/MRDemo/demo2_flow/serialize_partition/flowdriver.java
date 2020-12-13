package com.jld.MRDemo.demo2_flow.serialize_partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class flowdriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //关键jar
        job.setJarByClass(flowdriver.class);

        //关联mr
        job.setMapperClass(flowMap.class);
        job.setReducerClass(flowRduce.class);

        //设置mr的输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(flowBean.class);

        //设置最终输出的kv
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(flowBean.class);
        //设置输入输出
        FileInputFormat.setInputPaths(job,new Path("e:/mr/in6"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/out7"));
        //

        job.setPartitionerClass(parts.class);
        job.setNumReduceTasks(5);
        job.waitForCompletion(true);

    }

}
