package com.jld.hbase.File2Table;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;

import javax.management.ImmutableDescriptor;
import java.io.FileInputStream;

public class tool implements Tool {

    public int run(String[] args) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(tool.class);
        //format
        Path path = new Path("hdfs://hadoop101:9000/input_fruit/fruit.tsv");
        FileInputFormat.addInputPath(job, path);

        //map
        job.setMapperClass(map.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);
        job.setMapOutputValueClass(Put.class);

        //reducer
        TableMapReduceUtil.initTableReducerJob(
                "atguigu:student",
                reducer.class,
                job
        );

        return job.waitForCompletion(true) ? JobStatus.State.SUCCEEDED.getValue() : JobStatus.State.FAILED.getValue();
    }

    @Override
    public void setConf(Configuration conf) {

    }

    @Override
    public Configuration getConf() {
        return null;
    }
}
