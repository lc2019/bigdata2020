package com.jld.hbase.opApi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;

public class hbaseoperApi {
    public static void main(String[] args) throws IOException {
//        System.out.println(isTableExist("student"));
//        createTable("ht1", "c1", "c2");

//        insertData("ht1","001","c1","name","lc");
//        getData("ht1");
//        getRowData("ht1","001");
//        getQdata("ht1","001","c1","name");

//        deleteData("ht1","001");
        dropTable("ht1");
    }

    public static Configuration conf;

    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop101:2181,hadoop102:2181,hadoop103:2181");
    }

    /**
     * 1.判断表是否存在
     *
     * @param tableName
     * @return
     * @throws IOException
     */
    public static boolean isTableExist(String tableName) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     * 2.创建表描述器HTableDescriptor---列簇描述器HColumnDescriptor
     * 表增加列簇 table.addFamily
     */
    public static void createTable(String tableName, String... columnF) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        if (isTableExist(tableName)) {
            System.out.println("table: " + tableName + " is exist");
        } else {
            //表描述器
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            for (String s : columnF) {
                //列描述器,增加列簇
                tableDescriptor.addFamily(new HColumnDescriptor(s));
            }
            admin.createTable(tableDescriptor);
            System.out.println("table " + tableName + " is ok");
        }
    }

    /**
     * 3.插入数据
     *
     * @param tableName 表名
     * @param rowkey    key 主键
     * @param colF      列簇
     * @param col       列
     * @param value     值
     * @throws IOException
     */
    public static void insertData(String tableName, String rowkey, String colF, String col, String value) throws IOException {
        //获取连接
        Connection conn = ConnectionFactory.createConnection(conf);
        //获取表
        Table table = conn.getTable(TableName.valueOf(tableName));
        //插入rowkey
        Put put = new Put(Bytes.toBytes(rowkey));
        //插入列数据
        put.addColumn(Bytes.toBytes(colF), Bytes.toBytes(col), Bytes.toBytes(value));
        //插入
        table.put(put);
        table.close();
        System.out.println("insert data ok");
    }

    /**
     * 获取所有的数据
     */
    public static void getData(String tableName) throws IOException {
        //获取连接
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        //扫描器
        Scan scan = new Scan();
        //获取表扫描器
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            //cell 封装了表的数据信息
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                //rowkey
                System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
                //列簇
                System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
                //列名
                System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
                //值
                System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
    }

    public static void getRowData(String tableName, String rowkey) throws IOException {
        //获取连接
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        //get获取指定rowkey的数据
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);
        for (Cell cell : result.rawCells()) {
            //rowkey
            System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
            //列簇
            System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
            //列名
            System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
            //值
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }

    /**
     * 获取指定列簇的某个列
     */
    public static void getQdata(String tableName, String rowkey, String colF, String col) throws IOException {
        //获取连接
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));
        //get获取指定rowkey的数据
        Get get = new Get(Bytes.toBytes(rowkey));
        Get get1 = get.addColumn(Bytes.toBytes(colF), Bytes.toBytes(col));
        Result result = table.get(get1);
        for (Cell cell : result.rawCells()) {
            //rowkey
            System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
            //列簇
            System.out.println(Bytes.toString(CellUtil.cloneFamily(cell)));
            //列名
            System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
            //值
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }

    public static void deleteData(String tableName, String... raws) throws IOException {
        //获取连接
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tableName));

        ArrayList<Delete> deletes = new ArrayList<>();

        for (String raw : raws) {
            Delete delete = new Delete(Bytes.toBytes(raw));
            deletes.add(delete);
        }
        table.delete(deletes);
        table.close();
    }

    /**
     * 删除表
     */
    public static void dropTable(String tableName) throws IOException {
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();
        if (isTableExist(tableName)) {
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
            System.out.println("delete ok");
        } else {
            System.out.println("not exist");
        }
    }
}
