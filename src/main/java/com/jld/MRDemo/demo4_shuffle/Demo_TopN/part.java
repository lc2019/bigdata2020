package com.jld.MRDemo.demo4_shuffle.Demo_TopN;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class part extends Partitioner<OrderBean, Text> {
    //分区规则
    @Override
    public int getPartition(OrderBean orderBean, Text text, int numPartitions) {
        //根据orderid
        return (orderBean.getOrderId().hashCode() & 2147483647) % numPartitions;
    }
}
