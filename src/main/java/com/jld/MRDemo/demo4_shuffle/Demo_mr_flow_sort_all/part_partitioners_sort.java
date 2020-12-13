package com.jld.MRDemo.demo4_shuffle.Demo_mr_flow_sort_all;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 实现区内有序
 */
public class part_partitioners_sort extends Partitioner<FlowBean3, Text> {

    @Override
    public int getPartition(FlowBean3 flowBean3, Text text, int numPartitions) {
        int res;
        String phone = text.toString();
        String strs = phone.substring(0, 3);
        if ("136".equals(strs)) {
            res = 0;
        } else if ("137".equals(strs)) {
            res = 1;
        } else if ("138".equals(strs)) {
            res = 2;
        } else if ("139".equals(strs)) {
            res = 3;
        } else {
            res = 4;
        }
        return res;
    }

}
