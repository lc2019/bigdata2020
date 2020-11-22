package MapReduce.Demo_mr_sort;

import MapReduce.Demo_mr_flow.FlowBean2;
import MapReduce.Demo_mr_flow.flowMapper;
import MapReduce.Demo_mr_flow.flowReduce;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/**
 * 获取job对象
 * 指定jar
 * 关联mr的业务类
 * 关联输入输出类型
 * 指定job输入输出目录
 * 提交作业
 */
public class demoDriver0 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job对象
        Configuration conf  = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar的位置
        job.setJarByClass(demoDriver0.class);

        //3.关联map和reducer的类
        job.setMapperClass(flowMapper.class);
        job.setReducerClass(flowReduce.class);

        //4.设置map输出的kv
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean2.class);

        //输出文件为流
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        //5.设置最终数据输出的kv
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean2.class);

        //6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path("e:/mr/in6"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/out14"));

        //7.提交job
        job.waitForCompletion(true);
    }
}
