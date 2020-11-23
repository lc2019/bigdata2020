package com.jld.MRDemo.Demo3_mr.Demo_FileInputFormat.diy;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;


/**
 * 自定义recordreader
 * 将文件的内容全部读取作为value，文件的路径+名字作为key
 */
public class diyRecordReader extends RecordReader<Text, BytesWritable> {
    private FileSplit fsp; //当前切片对象
    private Configuration conf; //当前配置对象
    private Text k = new Text();
    private BytesWritable byw = new BytesWritable();
    private boolean flag = true;

    /**
     * 初始化
     *
     * @param split
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        //获取当前切片对象
        fsp = (FileSplit) split;
        //获取job的配置对象
        conf = context.getConfiguration();
    }


    /**
     * 读取下一个kv----->
     * 将文件的内容全部读取作为value，文件的路径+名字作为key
     *
     * @return boolean
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        //key 文件的路径 当前文件系统对象转string,获取文件路径和名字
        if (flag) {
            String path = fsp.getPath().toString();
            k.set(path);

            //获取输入流 作为value
            FileSystem fs = FileSystem.get(conf);
            FSDataInputStream inputStream = fs.open(fsp.getPath());

            //输出文件内容字节数组
            byte[] bytes = new byte[(int) fsp.getLength()];
            //字节数组读取完
            IOUtils.readFully(inputStream, bytes, 0, bytes.length);

            //值
            byw.set(bytes, 0, bytes.length);

            flag = false;
            return true;

        }
        return false;
    }

    /**
     * 文件的路径+名字作为key
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return k;
    }

    /**
     * value 值--->将文件的内容全部读取作为value，
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return byw;
    }

    /**
     * 获取map的进度
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    /**
     * 关闭
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {

    }
}
