package com.jld.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class hbase_demo {
    public static void main(String[] args) throws IOException {
        //创建配置对象
        Configuration conf = HBaseConfiguration.create();
        //1.获取hbase连接
        Connection connection = ConnectionFactory.createConnection(conf);
        //2.操作对象
        Admin admin = connection.getAdmin();
        //3.操作数据库
        //判断表是否存在
        TableName table = TableName.valueOf("atguigu:student");
        boolean flag = admin.tableExists(table);

        try {
            //查看是否存在名称空间
            admin.getNamespaceDescriptor("atguigu");
        } catch (NamespaceNotFoundException e) {
            //没有就创建名称空间对象
            NamespaceDescriptor build = NamespaceDescriptor.create("atguigu").build();
            admin.createNamespace(build);
        }
        //表空间
        if (flag) {
            //获取表对象
            Table tb = connection.getTable(table);
            String rowkey = "1001";
            //字节数组
            Get get = new Get(Bytes.toBytes(rowkey));
            Result result = tb.get(get);
            boolean empty = result.isEmpty();
            System.out.println("meiyou shuju" + empty);
            if (empty) {
                //新增数据
                Put put = new Put(Bytes.toBytes(rowkey));
                String family = "info";
                String col = "name";
                String value = "lc";
                //列簇 列名 值
                Put insert = put.addColumn(Bytes.toBytes(family), Bytes.toBytes(col), Bytes.toBytes(value));
                //插入
                tb.put(insert);
                System.out.println("增加数据");
            } else {
                //展示数据
                tableData(result);
            }
        } else {
//            admin.getNamespaceDescriptor("atguigu");

            //创建表对象
            HTableDescriptor hTable = new HTableDescriptor(table);
            hTable.addCoprocessor("com.jld.hbase.regioncoprocess");
            //列
            HColumnDescriptor info = new HColumnDescriptor("info");
            //列簇增加到表
            hTable.addFamily(info);
            //创建表
            admin.createTable(hTable);

            System.out.println("ok");
        }


        //4.获取操作结果
        System.out.println("over");
        //  5.关闭数据库连接
    }

    static void tableData(Result result) {
        for (Cell cell : result.rawCells()) {
            //列值
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
            //列
            System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
            //列簇
            System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
            //列名
            System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
        }
    }
}
