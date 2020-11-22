package com.jld.MRDemo.dpt2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PartitionMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    /**
     * 将k1v1转k2v2
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //整行数据作为key
//        1	0	1	2017-07-31 23:10:12	837255	6	4+1+1=6	小,双	0	0.00	0.00	1	0.00	1	1
        context.write(value, NullWritable.get());
    }
}
