package com.jld.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


/**
 * 查询数据
 */
public class hbase_demo4 {
    public static void main(String[] args) throws IOException {
        //创建配置对象
        Configuration conf = HBaseConfiguration.create();
        //获取连接
        Connection conn = ConnectionFactory.createConnection(conf);
        //获取表名
        TableName fruit = TableName.valueOf("fruit");
        //获取表
        Table table = conn.getTable(fruit);
        //扫描对象
        Scan scan = new Scan();
        //扫描器
        BinaryComparator comparator = new BinaryComparator(Bytes.toBytes("1002"));
        RegexStringComparator rex = new RegexStringComparator("^\\d{4}$");
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, rex);

        RowFilter filter2 = new RowFilter(CompareFilter.CompareOp.EQUAL, comparator);
//        MUST_PASS_ONE  or MUST_PASS_ALL and
        //过滤器集合
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);

        filterList.addFilter(filter);
        filterList.addFilter(filter2);
        //添加扫描器
        scan.setFilter(filter);

        org.apache.hadoop.hbase.client.ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
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
}
