package com.jld.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class hbase_demo2 {
    public static void main(String[] args) throws IOException {
        //创建配置对象
        Configuration conf = HBaseConfiguration.create();
        //1.获取hbase连接
        Connection connection = ConnectionFactory.createConnection(conf);
        //2.操作对象
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf("atguigu:student");
//        //3.删除表
//        if(admin.tableExists(tableName)){
//            //获取表
////
//            admin.disableTable(tableName);
//            admin.deleteTable(tableName);
//        }
        Table table = connection.getTable(tableName);
      /* 删除数据
        String rowkey = "1001";
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        table.delete(delete);
       */
        //获取扫描对象
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            hbase_demo.tableData(result);
        }
        //4.获取操作结果
        System.out.println(" over");
        //  5.关闭数据库连接
    }
}
