package MapReduce.diypartition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class JobMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //初始化job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置jar的位置
        job.setJarByClass(JobMain.class);

        //2.设置job的相关信息
        // 设置输入路径
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path("e:/input/test.txt"));

        ///设置map类 k2v2
        job.setMapperClass(wordMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //设置分区
        job.setPartitionerClass(MyPartitoner.class);
        job.setNumReduceTasks(2);

        //设置reduce k3v3 -->结果文件
        job.setReducerClass(wordReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //设置输出路径
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path("e:/out/nx2"));

        //7.提交job
        boolean b = job.waitForCompletion(true);
        System.out.println(b);
        System.exit(b ? 0 : 1);

    }
}
