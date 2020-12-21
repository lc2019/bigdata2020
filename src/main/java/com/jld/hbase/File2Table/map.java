package com.jld.hbase.File2Table;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class map extends Mapper<LongWritable, Text, ImmutableBytesWritable, Put> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //切割
        String[] values = value.toString().split("\t");
        //rowkey
        String rowkey = values[0];
        //字节数组
        byte[] bs = Bytes.toBytes(rowkey);
        //插入
        Put put = new Put(bs);
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(values[1]));
        //序列花写入
        context.write(new ImmutableBytesWritable(bs), put);
    }
}
