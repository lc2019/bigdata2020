package com.jld.MRDemo.demo2_flow.serialize;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class parts extends Partitioner<Text, flowBean> {
    @Override
    public int getPartition(Text text, flowBean flowBean, int numPartitions) {
        /**
         * 指定分区规则
         */
        int part;
        String s = text.toString();
        String proPre = s.substring(0, 3);
        if ("136".equals(proPre)){
            part = 0;
        }else if ("137".equals(proPre)){
            part=1;
        }else if ("138".equals(proPre)){
            part=2;
        }else if ("139".equals(proPre)){
            part=3;
        }else {
            part=4;
        }
        return part;
    }
}
