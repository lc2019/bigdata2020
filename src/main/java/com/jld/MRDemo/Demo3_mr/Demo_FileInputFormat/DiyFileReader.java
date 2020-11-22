//package MapReduce.Demo_mr.Demo_Fileinputformat;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.io.ByteWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.InputSplit;
//import org.apache.hadoop.mapreduce.RecordReader;
//import org.apache.hadoop.mapreduce.TaskAttemptContext;
//import org.apache.hadoop.mapreduce.lib.input.FileSplit;
//
//import java.io.IOException;
//
//import static org.apache.hadoop.util.StringUtils.split;
//
//
///**
// * 自定义recordreader
// * 将文件的内容全部读取作为value，文件的路径+名字作为key
// */
//public class DiyFileReader  extends RecordReader<Text, ByteWritable> {
//    private FileSplit fsp ; //当前切片对象
//    private Configuration conf; //当前配置对象
//    /**
//     * 初始化
//     * @param split
//     * @param context
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @Override
//    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
//        //获取当前切片对象
//       fsp =  (FileSplit)split();
//
//        conf = context.getConfiguration();
//    }
//
//
//    /**
//     * 读取下一个kv----->将文件的内容全部读取作为value，文件的路径+名字作为key
//     * @return boolean
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @Override
//    public boolean nextKeyValue() throws IOException, InterruptedException {
//        return false;
//    }
//
//    /**
//     * 文件的路径+名字作为key
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @Override
//    public Text getCurrentKey() throws IOException, InterruptedException {
//        return null;
//    }
//
//    /**
//     * value 值--->将文件的内容全部读取作为value，
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @Override
//    public ByteWritable getCurrentValue() throws IOException, InterruptedException {
//        return null;
//    }
//
//    /**
//     *获取map的进度
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @Override
//    public float getProgress() throws IOException, InterruptedException {
//        return 0;
//    }
//
//    /**
//     * 关闭
//     * @throws IOException
//     */
//    @Override
//    public void close() throws IOException {
//
//    }
//}
