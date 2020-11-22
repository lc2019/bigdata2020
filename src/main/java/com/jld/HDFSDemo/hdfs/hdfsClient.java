package com.jld.HDFSDemo.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;

public class hdfsClient {
    FileSystem fs = null;

    @Before
    public void init() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "atguigu");
    }

    //查看目录
    @Test
    public void listDir() throws IOException {
        //文件的描述信息-封装成迭代器
        RemoteIterator<LocatedFileStatus> itr = fs.listFiles(new Path("/"), true);
        while (itr.hasNext()) {
            LocatedFileStatus meta = itr.next();
            System.out.println(meta.getBlockSize());
            System.out.println(meta.getPath());
            BlockLocation[] blockLocations = meta.getBlockLocations();
            //数组块的元数据信息
            for (BlockLocation blk : blockLocations) {
                System.out.println(Arrays.toString(blk.getHosts()));
                System.out.println(blk.getLength());
                System.out.println(blk.getNames());
            }
        }
    }

    //查看文件和文件夹信息
    @Test
    public void ldirf() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.isDirectory() ? ("d " + fileStatus.getPath()) : ("f " + fileStatus.getPath()));
        }
    }

    //查看文件的内容
    @Test
    public void readData() throws IOException {
        //获取hdfs的数据流
        FSDataInputStream din = fs.open(new Path("/hdfsTest/wc.txt"));

        //创建字节数组
//        byte[] bs  = new byte[1024];
//        int read = 0;
//        while ( (read = dataInputStream.read(bs) )!=-1){
//            System.out.println(new String(bs,0,read));
//        }
        //手动实现wordcount单机版
        HashMap<String, Integer> cntMap = new HashMap<String, Integer>();
        //读取数据
        BufferedReader br = new BufferedReader(new InputStreamReader(din));
        String line = null;
        //循环读取
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            //遍历
            for (String word : words) {
                //单词计数 默认是0 如果存在+1
                cntMap.put(word, cntMap.getOrDefault(word, 0) + 1);
            }
        }
        System.out.println(cntMap);
        fs.close();
    }

    //写入数据到hdfs
    @Test
    public void write() throws IOException {
        //hdfs的输出流
        FSDataOutputStream out  = fs.append(new Path("/hdfsTest/wc.txt"));
        out.write("加油 坚持 加油 坚持 。。。。2020 2021".getBytes());
        out.close();
        fs.close();
    }
}
