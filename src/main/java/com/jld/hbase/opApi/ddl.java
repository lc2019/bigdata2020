package com.jld.hbase.opApi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ddl {
    public static Configuration conf;
    Connection conn = null;
    public static ArrayList<String> list = new ArrayList<>();

    @Before
    public void getConn() throws Exception {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hadoop101:2181,hadoop102:2181,hadoop103:2181");
        //获取配置对象
        conn = ConnectionFactory.createConnection(conf);
    }

    @After
    public void closeConn() throws IOException {
        conn.close();
    }

    @Test
    public void testCreateNameSpace() throws IOException {
        //表管理器
        Admin admin = conn.getAdmin();

        NamespaceDescriptor youspace = NamespaceDescriptor.create("youspace").build();

        admin.createNamespace(youspace);

        admin.close();
        conn.close();
    }

//    @Test
//    public void isTableExist(TableName emp5) throws IOException {
//        Admin admin = conn.getAdmin();
//        //老方法
////        HBaseAdmin admin = new HBaseAdmin(conf);
//        System.out.println(admin.tableExists(TableName.valueOf("student")));
//    }

    @Test
    public void createTable() throws IOException {
        Admin admin = conn.getAdmin();
        //判断表是否存在
        if (admin.tableExists(TableName.valueOf("emp5"))) {
            System.out.println("table");
        }
    }

    /**
     * 查询名称空间
     *
     * @throws IOException
     */
    @Test
    public void queryNameSpace() throws IOException {
        Admin admin = conn.getAdmin();
        NamespaceDescriptor[] ss = admin.listNamespaceDescriptors();
        for (NamespaceDescriptor s : ss) {
            System.out.println(s);
        }
        admin.close();
    }

    /**
     * 判断库是否存在
     */
    @Test
    public void isExistNamespace() throws IOException {
        Admin admin = conn.getAdmin();
        try {
            admin.getNamespaceDescriptor("emp5");
        } catch (Exception e) {
            System.out.println("Namespace Not Found");
            System.exit(1);
        }
        admin.close();
    }

    /**
     * 创建namespace
     *
     * @throws IOException
     */
    @Test
    public void createNamespace() throws IOException {
        Admin admin = conn.getAdmin();
        try {
            NamespaceDescriptor nss = NamespaceDescriptor.create("nss").build();
            admin.createNamespace(nss);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            admin.close();
        }
    }

    /**
     * 查看所有的表
     */
    @Test
    public void listTable() throws IOException {
        Admin admin = conn.getAdmin();
        //表获取
        HTableDescriptor[] tables = admin.listTableDescriptorsByNamespace("nss");

        for (HTableDescriptor table : tables) {
            list.add(table.getNameAsString());
        }
        for (String s : list) {
            System.out.println(s);
        }
        admin.close();
    }

    @Test
    public void deleteNameSpace() throws IOException {
        Admin admin = conn.getAdmin();
        try {
            admin.deleteNamespace("nss");
        } catch (Exception e) {
            System.out.println("not exist nss");
        }
        admin.close();
    }
}
