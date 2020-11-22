package MapReduce.Demo3_mr.Demo_FileOutPutFormat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class DiyRecordWriter extends RecordWriter<Text, NullWritable> {
    private FSDataOutputStream good;
    private  FSDataOutputStream bad;

    public DiyRecordWriter() {
    }

    public DiyRecordWriter(FSDataOutputStream good, FSDataOutputStream bad) {
        this.good = good;
        this.bad = bad;
    }

    /**
     *写入的格式
     * @param key 行文本内容
     * @param value
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        //获取评论
        String[] split = key.toString().split("\t");
        String str = split[9];
        //判断评论类型，将对应的数据写入不同的文件加
        if (Integer.parseInt(str)<=1){
            //好评
            good.write(key.toString().getBytes());
            good.write("\r\n".getBytes());
        }else {
            //差评
            bad.write(key.toString().getBytes());
            bad.write("\r\n".getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        good.close();
        bad.close();
    }
}
