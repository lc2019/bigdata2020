package MapReduce.Demo_mr_sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * 获取job对象
 * 指定jar
 * 关联mr的业务类
 * 关联输入输出类型
 * 指定job输入输出目录
 * 提交作业
 */
public class demoDriver1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job对象
        Configuration conf  = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar的位置
        job.setJarByClass(demoDriver1.class);

        //3.关联map和reducer的类
        job.setMapperClass(flow2.flow2Mapper.class);
        job.setReducerClass(flow2.flow2Reduce.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);

        job.setOutputFormatClass(TextOutputFormat.class);
        //4.设置map输出的kv
        job.setMapOutputKeyClass(FlowBean3.class);
        job.setMapOutputValueClass(NullWritable.class);

        //5.设置最终数据输出的kv
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(FlowBean2.class);

        //6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job,new Path("e:/mr/out14"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/out15"));

        //7.提交job
        job.waitForCompletion(true);
    }
}
