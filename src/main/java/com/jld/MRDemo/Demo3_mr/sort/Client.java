package MapReduce.Demo3_mr.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Client extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(super.getConf(), "sort");

        //job对象
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path("e:/mr/in5"));
        //mr
        job.setMapperClass(SortMapper.class);
        job.setReducerClass(SortReduce.class);
        //k2v2
        job.setMapOutputKeyClass(SortBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        //k3v3
        job.setOutputKeyClass(SortBean.class);
        job.setOutputValueClass(NullWritable.class);

        //输出路径
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job,new Path("e:/mr/out5"));



        job.waitForCompletion(true);
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        int run = ToolRunner.run(conf, new Client(), args);
        System.exit(run);
    }
}
