package MapReduce.Demo3_mr.demo_reduce_Join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * * driver阶段-jar包
 * *  获取配置信息，获取job对象实例
 * *  指定本程序的jar包所在路径
 * *  关联map/reduce业务类
 * *  指定map输出kv
 * *  指定数据输出kv
 * *  指定job原始目录
 * *  指定job输出目录
 * *  提交作业
 */
public class Driver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar的位置
        job.setJarByClass(Driver.class);

        //3.关联map和reducer
        job.setMapperClass(map.class);
        job.setReducerClass(reduce.class);

        //4.设置map阶段的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        //5.设置最终reduce阶段的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //6.设置输入和输出的路径
        FileInputFormat.setInputPaths(job,new Path("e:/mr/in8"));
        FileOutputFormat.setOutputPath(job,new Path("e:/mr/out8"));

        //7.提交job任务
        job.waitForCompletion(true);
    }
}
