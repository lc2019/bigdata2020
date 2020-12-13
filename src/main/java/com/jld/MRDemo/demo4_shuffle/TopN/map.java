package com.jld.MRDemo.demo4_shuffle.TopN;



import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 形成kv
 */
public class map  extends Mapper<LongWritable, Text,Text,OrderBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //u01,order001,apple,5,8.5
        String[] split = value.toString().split(",");
        OrderBean orderBean = new OrderBean(split[0], split[1], split[2], Integer.parseInt(split[3]), Float.parseFloat(split[4]));
        context.write(new Text(orderBean.getOid()),orderBean);
    }
}
