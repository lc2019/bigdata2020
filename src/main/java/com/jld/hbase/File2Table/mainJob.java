package com.jld.hbase.File2Table;

import org.apache.hadoop.util.ToolRunner;

public class mainJob {
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new tool(), args);
    }
}
