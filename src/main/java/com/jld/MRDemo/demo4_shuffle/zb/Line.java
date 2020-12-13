package MapReduce.Demo3_mr.zb;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Line {
    public static class LineMapper extends Mapper<LongWritable, Text, IntWritable,IntWritable>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//            1,4
            String[] split = value.toString().split(",");
            for (int i = Integer.parseInt(split[0]); i <=Integer.parseInt(split[1]) ; i++) {
                context.write(new IntWritable(i),new IntWritable(1));
            }
        }
    }

    public  static class  LineReduce extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable>{
        @Override
        protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count=0;
            for (IntWritable v : values) {
                count+=v.get();
            }
            if (count>1){
                context.write(key,new IntWritable(count));
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf  = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(Line.class);

        //map输出的kv类型必须设置
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setMapperClass(LineMapper.class);
        job.setReducerClass(LineReduce.class);

        FileInputFormat.setInputPaths(job,new Path("e:/mr/input"));
        FileOutputFormat.setOutputPath(job, new Path("e:/mr/output"));

        job.waitForCompletion(true);
    }
}
