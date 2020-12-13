package com.jld.MRDemo.demo4_shuffle.Demo_TopN;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 自定义分组
 */
public class wc extends WritableComparator {
    public wc() {
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        //强制类型转行
        OrderBean fr = (OrderBean) a;
        OrderBean sec = (OrderBean) b;

        return fr.getOrderId().compareTo(sec.getOrderId());
    }
}
