package com.jld.hbase.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HbaseUtils {
    //线程池
    private static ThreadLocal<Connection> connHolder = new ThreadLocal<Connection>();

    private HbaseUtils() {
    }

    /**
     * 获取hbase的连接对象
     *
     * @return
     */
    public static void makeHbaseConnection() throws IOException {
        //获取连接
        Connection conn = connHolder.get();
        if (conn == null) {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            //放入缓冲池
            connHolder.set(conn);
        }
    }

    /**
     * 插入数据
     */
    public static void insert(String tableName, String rowkey, String family, String col, String value) throws IOException {
        Connection conn = connHolder.get();
        //获取表
        Table table = conn.getTable(TableName.valueOf(tableName));
        //表插入
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(family), Bytes.toBytes(col), Bytes.toBytes(value));
        //表操作
        table.put(put);
        table.close();
    }

    public static void delete(String tableName, String rowkey) throws IOException {
        Connection conn = connHolder.get();
        //获取表
        Table table = conn.getTable(TableName.valueOf(tableName));
        //表删除数据
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        //表操作
        table.delete(delete);
        table.close();
    }

    /**
     * 关闭连接
     */
    public static void close() throws IOException {
        Connection conn = connHolder.get();
        if (conn != null) {
            conn.close();
            connHolder.remove();
        }
    }

    /**
     * 分区号
     *
     * @param rowkey
     * @param reginCount
     * @return
     */
    public static String genRegionNum(String rowkey, int reginCount) {
        int regionNum;
        int hash = rowkey.hashCode();
        if (reginCount > 0 & (reginCount & (reginCount - 1)) == 0) {
            regionNum = hash & (reginCount - 1);
        } else {
            regionNum = hash % (reginCount);
        }
        return regionNum + "_" + rowkey;
    }

    /**
     * 生成分区键
     */
    public static byte[][] genRegionKeys(int reginCount) {
        byte[][] bs = new byte[reginCount - 1][];
        for (int i = 0; i < reginCount - 1; i++) {
            bs[i] = Bytes.toBytes(i + "|");
        }
        return bs;
    }
}

