package com.jld.MRDemo.demo4_shuffle.Demo_mr_flow_sum;

import com.jld.MRDemo.demo4_shuffle.Demo_mr_flow_sort_all.FlowBean3;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class flowMapper extends Mapper<LongWritable, Text, Text, FlowBean2> {
    //创建对象
    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//   数据 id 手机号  -2 为下行流量 -3 上行流量
//        1363157985066	13726230503	00-FD-07-A4-72-B8:CMCC	120.196.100.82	i02.c.aliimg.com	游戏娱乐	24	27	2481	24681	200
        //文本切割
        String[] split = value.toString().split("\t");
        //封装对象
        FlowBean2 fb = new FlowBean2(Long.parseLong(split[split.length - 3]), Long.parseLong(split[split.length - 2]));
        //手机号作为key
        k.set(split[1]);
        //写出bean作为值
        context.write(k, fb);
    }
}
