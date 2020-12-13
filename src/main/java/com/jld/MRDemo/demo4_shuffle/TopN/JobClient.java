package com.jld.MRDemo.demo4_shuffle.TopN;

import com.jld.MRDemo.demo4_shuffle.Demo_TopN.reduce;
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

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(OrderBean.class);

        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job,new Path("e:/mr/input2/"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/output3/"));

        job.waitForCompletion(true);

    }
}