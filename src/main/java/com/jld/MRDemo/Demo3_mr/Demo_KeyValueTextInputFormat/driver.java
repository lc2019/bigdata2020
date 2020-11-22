//package MapReduce.Demo_mr.Demo_KeyValueTextInputFormat;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
//import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//
//import java.io.IOException;
//
//public class driver {
//    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        //1.获取job对象
//        Configuration conf = new Configuration();
//        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR," ");
//        Job job = Job.getInstance(conf);
//        //2.获取jar存储路径
//        job.setJarByClass(driver.class);
//        //3.管理mapper
//        job.setMapperClass(map.class);
//        job.setReducerClass(redurce.class);
//        //4.设置map和reduce的输出
//        job.setMapOutputKeyClass(Text.class);
//        job.setMapOutputValueClass(IntWritable.class);
//        //5.设置最终的输出类型
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(IntWritable.class);
//
//        job.setInputFormatClass(KeyValueTextInputFormat.class);
//        //6.设置输出路径
//        FileInputFormat.setInputPaths(job,new Path("/Users/mac/input"));
//        FileOutputFormat.setOutputPath(job,new Path("/Users/mac/outputkv"));
//        //7.提交job
//        job.waitForCompletion(true);
//
//    }
//}
