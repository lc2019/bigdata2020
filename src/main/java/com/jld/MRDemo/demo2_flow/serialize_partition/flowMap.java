package com.jld.MRDemo.demo2_flow.serialize_partition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class flowMap extends Mapper<LongWritable, Text, Text, flowBean> {
    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取第一行
        // id 手机号 ip  网站 上行 下行 网络状态
        //1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
        String line = value.toString();

        //2.切割
        String[] fields = line.split("\t");

        //3.提取需要的内容
        String phone = fields[1];//手机号
        //上行
        String up = fields[fields.length - 3];
        //下行
        String down = fields[fields.length - 2];
        //封装对象
        flowBean fb = new flowBean(Long.parseLong(up), Long.parseLong(down));
        //4.写出 手机号为key，bean为对象输出
        k.set(phone);
        context.write(k, fb);
    }
}
