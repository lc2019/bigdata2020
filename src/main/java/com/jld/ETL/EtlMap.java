package com.jld.ETL;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class EtlMap extends Mapper<LongWritable, Text, Text, NullWritable> {
    private Text key_out = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String res = EtlUtils.etl(value.toString());
        if (res == null) {
            return;
        }
        key_out.set(res);
        context.write(key_out, NullWritable.get());
    }
}
