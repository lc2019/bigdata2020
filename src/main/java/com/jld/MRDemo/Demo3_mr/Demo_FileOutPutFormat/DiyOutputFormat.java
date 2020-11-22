package MapReduce.Demo3_mr.Demo_FileOutPutFormat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class DiyOutputFormat extends FileOutputFormat<Text, NullWritable> {

    @Override
    public RecordWriter<Text, NullWritable> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        //提供给writer输出流
        //获取目标文件的输出流
        FileSystem fs = FileSystem.get(job.getConfiguration());
        //定义写入文件位置
        FSDataOutputStream  good = fs.create(new Path("e:/mr/good/good.txt"));
        FSDataOutputStream  bad = fs.create(new Path("e:/mr/bad/bad.txt"));

        //将输出流传递给writer
        DiyRecordWriter diyRecordWriter = new DiyRecordWriter(good, bad);
        return  diyRecordWriter;
    }
}
