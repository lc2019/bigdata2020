package MapReduce.Demo3_mr.Demo_FileOutPutFormat;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class client {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.获取jar存储路径
        job.setJarByClass(client.class);
        //3.管理mapper
        job.setMapperClass(map.class);

        //4.设置map和reduce的输出
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);


        //设置输出的类型
        job.setOutputFormatClass(DiyOutputFormat.class);

        //6.设置输出路径
        FileInputFormat.setInputPaths(job,new Path("e:/mr/in9"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/out10"));
        //7.提交job
        job.waitForCompletion(true);

    }
}
