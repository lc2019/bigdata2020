package com.jld.MRDemo.demo2_flow.serialize;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flowRduce  extends Reducer<Text, MapReduce.demo2_flow.serialize.flowBean,Text, MapReduce.demo2_flow.serialize.flowBean> {
    @Override
    protected void reduce(Text key, Iterable<MapReduce.demo2_flow.serialize.flowBean> values, Context context) throws IOException, InterruptedException {
       long upSumflow  = 0;
       long downSumflow  = 0;
        //迭代，累计流量求和
        for (MapReduce.demo2_flow.serialize.flowBean flowBean : values) {
            upSumflow+=flowBean.getUpFlow();
            downSumflow+=flowBean.getDownFlow();
        }
        MapReduce.demo2_flow.serialize.flowBean fb = new MapReduce.demo2_flow.serialize.flowBean(upSumflow, downSumflow);

        //写出
        context.write(key,fb);
    }
}
