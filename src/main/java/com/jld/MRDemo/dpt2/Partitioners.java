package com.jld.MRDemo.dpt2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


public class Partitioners extends Partitioner<Text, NullWritable> {
    /**
     * 指定分区规则,返回分区编号
     *
     * @param text
     * @param nullWritable
     * @param i
     * @return
     */
    @Override
    public int getPartition(Text text, NullWritable nullWritable, int i) {
        //1	0	1	2017-07-31 23:10:12	837255	6	4+1+1=6	小,双	0	0.00	0.00	1	0.00	1	1
        //1.拆分文本数据
        String result = text.toString().split("\t")[5];
        System.out.println(result);
        //2.判断中奖数字返回对应的分区编号
        if (Integer.parseInt(result) > 15) return 1;
        else return 0;
    }
}
