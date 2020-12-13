package com.jld.MRDemo.demo4_shuffle.Demo_TopN;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class JobClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf  = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(JobClient.class);

        job.setMapperClass(map.class);
        job.setReducerClass(reduce.class);

        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path("e:/mr/in10"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/out10"));

        //设置分区
        job.setPartitionerClass(part.class);
        //设置分组
        job.setGroupingComparatorClass(wc.class);
//        job.setNumReduceTasks(3);
        job.waitForCompletion(true);

    }
}