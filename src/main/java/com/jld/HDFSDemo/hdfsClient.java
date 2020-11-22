package com.jld.HDFSDemo;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class hdfsClient {
    private FileSystem fs;

    /**
     * hdfs客户端获取
     * get uri conf,user
     * uri 本地的文件系统协议 本地file 分布式 hdfs user 当前操作的用户
     * conf 读取hadoop的配置文件
     */
    @Test
    public void testConnect() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "atguigu");
        System.out.println(fs);
        //创建目录
        fs.mkdirs(new Path("/hdfsTest/in"));
        //关闭流
        fs.close();
    }


    @Before
    public void init() throws IOException, URISyntaxException, InterruptedException {
        Configuration conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "atguigu");
    }

    @After
    public void destory() throws IOException {
        if (fs != null) {
            fs.close();
        }
    }

    //上传 src dst
    @Test
    public void hdfsUpload() throws IOException {
        fs.copyFromLocalFile(new Path("e:/testcopy.txt"), new Path("/hdfsTest/in"));
    }

    //下载
    //   * The src file is under FS, and the dst is on the local disk.
    //   * Copy it from FS control to the local dst name.
    @Test
    public void hdfsDownload() throws IOException {
        fs.copyToLocalFile(new Path("/sync.sh"), new Path("e:/"));
    }

    /**
     * 删除
     */
    @Test
    public void delete() throws IOException {
        fs.delete(new Path("/sync.sh"), true);
    }

    /**
     * 查询文件，目录
     */
    @Test
    public void query() throws IOException {
//        FileStatus fileStatus = fs.getFileStatus(new Path("/hdfsTest"));
//        if (fileStatus.isDirectory()){
//            System.out.println("当前是目录");
//        }else {
//            System.out.println("是文件");
//        }

        //递归查看
        FileStatus[] fileStatuses = fs.listStatus(new Path("/hdfsTest"));
        //遍历数组
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getPath());
            System.out.println(fileStatus.getReplication());
        }

        RemoteIterator<LocatedFileStatus> itr = fs.listFiles(new Path("/hdfsTest"), true);
        while (itr.hasNext()) {
            LocatedFileStatus next = itr.next();
            System.out.println(next.getBlockLocations());
            System.out.println(next.getLen());
        }
    }

    //手动上传
    @Test
    public void upload() throws IOException {
        FileInputStream fin = new FileInputStream(new File("e:/wc.txt"));
        //上传到hdfs的路径
//        Create an FSDataOutputStream at the indicated Path.
        FSDataOutputStream fsd = fs.create(new Path("/test"));
        //  * @param in InputStream to read from
        //   * @param out OutputStream to write to
        //   * @param conf the Configuration object
        //   * @param close whether or not close the InputStream and
        IOUtils.copyBytes(fin, fsd, fs.getConf(), true);
    }

    @Test
    /**
     * 手动下载
     */
    public void download() throws IOException {
        FileOutputStream fout = new FileOutputStream(new File("e:/test"));
        FSDataInputStream open = fs.open(new Path("/test"));
        IOUtils.copyBytes(open, fout, fs.getConf(), true);
    }

    //下载指定的块
    @Test
    public void downloadBlock() throws IOException {
        //读取第一块的内容
        //hdfs输入流
        FSDataInputStream is = fs.open(new Path("/test"));
        //本地文件输出流
        FileOutputStream out = new FileOutputStream(new File("e:/blk.txt"));

        //字节数组读取
        byte[] bytes = new byte[1024];
        for (int i = 0; i < 128 * 1024; i++) {
            is.read(bytes);
            out.write(bytes);
        }
        IOUtils.closeStream(is);
        IOUtils.closeStream(out);
    }

    //下载第二块
    public void dbk2() throws IOException {
        //hdfs文件系统
        FSDataInputStream io = fs.open(new Path("/test"));
        //本地文件系统
        FileOutputStream fo = new FileOutputStream(new File("e:/bk2"));

        //定位到指定位置读取
        io.seek(134217728);

        //字节数组读取
        byte[] bytes = new byte[1024];
        for (int i = 0; i < 128 * 1024; i++) {
            io.read(bytes);
            fo.write(bytes);
        }
        IOUtils.closeStream(io);
        IOUtils.closeStream(fo);
    }

    /**
     * 递归显示目录和文件
     * @throws IOException
     */
    @Test
    public void  test() throws IOException {
        listfileAndDir(new Path("/"));
    }
    public void listfileAndDir(Path path) throws IOException {
        //获取文件状态
        FileStatus[] fileStatuses = fs.listStatus(path);
        //遍历
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                //如果是文件
                System.out.println("file " + path.toString()+"/"+fileStatus.getPath().getName());
            } else {
                //递归 截取子串
                System.out.println("dir " + fileStatus.getPath().toString().substring("hdfs://hadoop101:9000".length()));
                listfileAndDir(fileStatus.getPath());
            }
        }
    }

    @Test
    /**
     * 文件及目录递归
     */
    public void lists() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                System.out.println(fileStatus.getPath().getName() + " 是文件");
            } else {
                System.out.println(fileStatus.getPath().getName() + " 是目录");
            }
        }
    }
}
