package com.jld.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;

import java.io.IOException;

/**
 * 协处理器-类似触发器
 * 1.创建类继承
 * 2.重写方法
 * 3.实现逻辑（关联源 ）
 */
public class regioncoprocess extends BaseRegionObserver {
    /**
     * @param e          环境
     * @param put        增加
     * @param edit       编辑
     * @param durability
     * @throws IOException
     */
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        Table table = e.getEnvironment().getTable(TableName.valueOf("fruit2"));
        //增加数据
        table.put(put);
        //关闭
        table.close();
    }
}
