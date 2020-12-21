package com.jld.hbase;


import com.jld.hbase.utils.HbaseUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 预分区
 */
public class hbase_demo5 {
    public static void main(String[] args) throws IOException {
        //创建配置对象
        Configuration conf = HBaseConfiguration.create();
        //1.获取hbase连接
        Connection connection = ConnectionFactory.createConnection(conf);
        //2.操作对象
        Admin admin = connection.getAdmin();
        //表名
        TableName tableName = TableName.valueOf("emp4");
        //创建表
        HTableDescriptor table = new HTableDescriptor(tableName);
        //创建列
        HColumnDescriptor info = new HColumnDescriptor("info");

        //列簇
        table.addFamily(info);
        //分区
        byte[][] bs = new byte[2][];
        bs[0] = Bytes.toBytes("0|");
        bs[1] = Bytes.toBytes("1|");

        //创建表的同时增加与分区
        admin.createTable(table, bs);

        //获取表
        Table emp3 = connection.getTable(TableName.valueOf("emp3"));
        String rowkey = "lc";

        //分区 计算分区键 HashMap
        String s = HbaseUtils.genRegionNum(rowkey, 3);
        Put put = new Put(Bytes.toBytes(s));
        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes("20"));

        emp3.put(put);

        //4.获取操作结果
        System.out.println(" over");
        emp3.close();
        //  5.关闭数据库连接
    }
}
