package com.jld.hbase;


import com.jld.hbase.utils.HbaseUtils;


import java.io.IOException;

public class hbase_demo3 {
    public static void main(String[] args) throws IOException {
        HbaseUtils.makeHbaseConnection();
        HbaseUtils.delete("atguigu:student", "1002");
        HbaseUtils.insert("atguigu:student", "1002", "info", "name", "lcc");
        HbaseUtils.close();
    }
}
