package com.jld.MRDemo.demo2_flow.serialize_partition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class parts extends Partitioner<Text, flowBean> {
    //序号  手机号            ip            网址          上行    下行    状态
    // 1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
    @Override
    public int getPartition(Text text, flowBean flowBean, int numPartitions) {
        /**
         * 指定分区规则
         */
        int part;
        String s = text.toString();
        String proPre = s.substring(0, 3);
        if ("135".equals(proPre)){
            part = 0;
        }else if ("136".equals(proPre)){
            part=1;
        }else if ("137".equals(proPre)){
            part=2;
//        }else if ("139".equals(proPre)){
//            part=3;
        }else {
            part=3;
        }
        return part;
    }
}
