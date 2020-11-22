package com.jld.MRDemo.diyPartition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @author LIAO
 * create  2020-11-16 20:43
 * 需求：根据单词的长度给单词出现的次数的结果存储到不同文件中，以便于在快速查询
 */
public class JobMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //一、初始化一个Job
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration, "partitioner");

        //二、设计Job的相关信息，8个小步骤
        //1、设置输入路径
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path("e://input/test.txt"));

        //2、设置Mapper类，并设置k2 v2
        job.setMapperClass(WordMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //3 4 5 6 Shuffle阶段
        //3、设置分区
        job.setPartitionerClass(MyPartitioner.class);

        //7、设置Reducer类，并设置k3 v3
        job.setReducerClass(WordReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //8、设置输出路径
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,new Path("e:/out/partitioner"));

        //设置NumReduceTask的个数
        job.setNumReduceTasks(2);

        //三、等待完成
        boolean b = job.waitForCompletion(true);
        System.out.println(b);
        System.exit(b ? 0 : 1);
    }
}
