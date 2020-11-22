package MapReduce.Demo_join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;
import java.util.ArrayList;

public class join {
    public static class JoinMapper extends Mapper<LongWritable, Text, Text, JoinBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //获取任务切片信息
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            //从任务切片信息中获取文件路径
            String name = fileSplit.getPath().getName();
            String[] split = value.toString().split(",");

            JoinBean joinBean = new JoinBean();
            if (name.equals("user.txt")) {
                //u01,sen,18,male,lc
                joinBean.set("NULL", split[0], split[1], Integer.parseInt(split[2]), split[3], split[4]);
            } else {
                joinBean.set(split[0], split[1], "NULL", 0, "NULL", "NULL");
            }
            context.write(new Text(joinBean.getUid()), joinBean);
        }
    }

    public static class JoinReduce extends Reducer<Text, JoinBean, JoinBean, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<JoinBean> values, Context context) throws IOException, InterruptedException {
            ArrayList<JoinBean> orl = new ArrayList<>();
            JoinBean userbean = new JoinBean();

            for (JoinBean value : values) {
                if (value.getOid().equals("NuLL")) {
                    //用户数据
                    userbean.set(value.getOid(), value.getUid(), value.getName(), value.getAge(), value.getGender(), value.getFriend());
                } else {
                    //订单数据
                    JoinBean orderBean = new JoinBean();
                    orderBean.set(value.getOid(), value.getUid(), value.getName(), value.getAge(), value.getGender(), value.getFriend());
                    orl.add(orderBean);
                }
            }
            //拼接输出数据
            for (JoinBean joinBean : orl) {
                joinBean.setName(userbean.getName());
                joinBean.setAge(userbean.getAge());
                joinBean.setGender(userbean.getGender());
                joinBean.setFriend(userbean.getFriend());
                //select a*，b* from t_order a join t_user b on a.uid=b.uid;
                context.write(joinBean, NullWritable.get());
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


        /**
         * 获取job对象
         * 指定jar
         * 关联mr的业务类
         * 关联输入输出类型
         * 指定job输入输出目录
         * 提交作业
         */

        //1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar的位置
        job.setJarByClass(join.class);

        //3.关联map和reducer的类
        job.setMapperClass(JoinMapper.class);
        job.setReducerClass(JoinReduce.class);

        //4.设置map输出的kv
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(JoinBean.class);

        //5.设置最终数据输出的kv
        job.setOutputKeyClass(JoinBean.class);
        job.setOutputValueClass(NullWritable.class);

        //6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("e:/mr/in11"));
        FileOutputFormat.setOutputPath(job, new Path("e:/mr/out11"));

        //7.提交job
        job.waitForCompletion(true);
    }
}

