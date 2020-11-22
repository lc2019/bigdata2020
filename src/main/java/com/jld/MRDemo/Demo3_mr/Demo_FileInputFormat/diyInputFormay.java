//package MapReduce.Demo_mr.Demo_Fileinputformat;
//
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.ByteWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.*;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import java.io.IOException;
//
//
//public class diyInputFormay extends FileInputFormat<Text, ByteWritable>{
//    /**
//     * 方法：
//     * 1.自定义一个继承FileIputFormat
//     * 2.改写RecoreReader实现一次读取完整文件封装为kv
//     * 3.在输出是使用SequenceFileOutPutFormat输出合并文件---->文件路径+文件名称（key）：文件内容（value）
//     */
//
//    /**
//     * 设置指定的文件是否切片
//     * 整个文件的内容作为value，不能对文件进行切片
//     * @param context
//     * @param filename
//     * @return boolean
//     */
//    @Override
//    protected boolean isSplitable(JobContext context, Path filename) {
//        return super.isSplitable(context, filename);
//    }
//
//    /**
//     *
//     * @param spt 切片
//     * @param context
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @Override
//    public RecordReader<Text, ByteWritable> createRecordReader(InputSplit spt, TaskAttemptContext context) throws IOException, InterruptedException {
//        DiyFileReader rc = new DiyFileReader();
//        //直接传递进去
//        rc.initialize(spt, context);
//        return rc;
//    }
//
//
//
//}
