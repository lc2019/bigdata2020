package com.jld.MRDemo.Demo3_mr.Demo_FileInputFormat.diy;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import java.io.IOException;


public class diyInputFormat extends FileInputFormat<Text, BytesWritable> {
    /**
     * 方法：
     * 1.自定义一个inputformat继承FileIputFormat --key就是文件名称 v就是文件内容
     * 2.改写RecoreReader实现一次读取完整文件封装为kv
     * 3.在输出是使用SequenceFileOutPutFormat输出合并文件---->文件路径+文件名称（key）：文件内容（value）
     */

    /**
     * 设置指定的文件是否切片
     * 整个文件的内容作为value，不能对文件进行切片
     * @param context
     * @param filename
     * @return boolean
     */
    @Override
    protected boolean isSplitable(JobContext context, Path filename) {
        return false;
    }

    /**
     *读取数据，使用默认切片规则
     * @param spt 切片
     * @param context
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit spt, TaskAttemptContext context) throws IOException, InterruptedException {
        diyRecordReader rc = new diyRecordReader();
        //直接传递进去给recordreader
        rc.initialize(spt, context);
        return rc;
    }
}
