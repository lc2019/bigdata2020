package com.jld.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class hbase_demo {
    public static void main(String[] args) throws IOException {
        //创建配置对象
        Configuration conf = HBaseConfiguration.create();
        //1.获取hbase连接
        ConnectionFactory.createConnection(conf);
        //2.操作对象

        //3.操作数据库

        //4.获取操作结果

        //  5.关闭数据库连接
    }
}
