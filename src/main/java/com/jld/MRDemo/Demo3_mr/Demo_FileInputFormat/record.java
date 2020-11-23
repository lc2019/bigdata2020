package com.jld.MRDemo.Demo3_mr.Demo_FileInputFormat;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class record extends RecordReader<NullWritable, BytesWritable>  {
    private Configuration conf = null;
    private FileSplit sp = null;
    private  boolean process=false;
    private BytesWritable bwa = new BytesWritable();
    private FileSystem fs = null;
    private FSDataInputStream inputs = null;

    /**
     * 初始化 源文件
     * conf
     *
     * @param split
     * @param task
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext task) throws IOException, InterruptedException {
        //获取conf对象
        conf = task.getConfiguration();
        //InputSplit 多态FileSplit
        sp = (FileSplit) split;

    }

    /**
     * k1v1 NUllWritable
     * BytesWritable
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (!process) {
            //获取源文件的字节输入,源文件系统
             fs = FileSystem.get(conf);
            //切片的getpath方法
             inputs = fs.open(sp.getPath());
            //2.读取源文件的数据到普通字节数组byte[]
            byte[] bytes = new byte[(int) sp.getLength()];
            IOUtils.readFully(inputs, bytes, 0, (int) sp.getLength());
            //3.将字节数组数据封装到BytesWritable

            bwa.set(bytes, 0, (int) sp.getLength());

            //文件读取完毕
            process=true;
            return true;
        }
        return false;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {
        return NullWritable.get();
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {

        return  bwa;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    @Override
    public void close() throws IOException {
        inputs.close();
        fs.close();
    }
}
