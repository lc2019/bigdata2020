package com.jld.MRDemo.demo4_shuffle.TopN;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * 可以已经包含了所有 v可以使用Null
 */
public class reduce extends Reducer<Text, OrderBean, OrderBean, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<OrderBean> values, Context context) throws IOException, InterruptedException {
        //集合存储数据
        ArrayList<OrderBean> beans = new ArrayList<>();
        //迭代取出放入beans
        for (OrderBean orderBean : values) {
            OrderBean newBean = new OrderBean(orderBean.getUid(), orderBean.getOid(), orderBean.getItem(), orderBean.getNum(), orderBean.getPrice());
            beans.add(newBean);
        }
        //集合排序
        Collections.sort(beans, (o1, o2) -> o2.getNum()*o2.getPrice()-o1.getNum()*o1.getPrice()>0?1:-1);

        //将top2的数据返回
        for (int i = 0; i < 2; i++) {
            context.write(beans.get(i),NullWritable.get());
        }
    }
}
