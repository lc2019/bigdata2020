package com.jld.MRDemo.demo2_flow.serialize_partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flowRduce extends Reducer<Text, flowBean, Text, flowBean> {
    @Override
    protected void reduce(Text key, Iterable<flowBean> values, Context context) throws IOException, InterruptedException {
        long upSumflow = 0;
        long downSumflow = 0;
        //迭代，累计流量求和
        for (flowBean flowBean : values) {
            upSumflow += flowBean.getUpFlow();
            downSumflow += flowBean.getDownFlow();
        }
        flowBean fb = new flowBean(upSumflow, downSumflow);
        //写出
        context.write(key, fb);
    }
}
