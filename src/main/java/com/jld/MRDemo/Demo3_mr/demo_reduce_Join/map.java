package MapReduce.Demo3_mr.demo_reduce_Join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * k1 longwritable
 * v1 text
 * k1,v1
 * 0    p0001,小米5,1000,2000
 * 0    1001,20150710,p0001,2
 * k2 text
 * v2 商品描述信息 text
 * <p>
 * k2,v2
 * p0001  p0001,小米5,1000,2000
 * 1001  1001,20150710,p0001,2
 */
public class map extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        //来自那个文件
        String filename = fileSplit.getPath().getName();
        if (filename.equals("product.txt")) {
            //产品表
            String[] split = value.toString().split(",");
            String pro = split[0];
            context.write(new Text(pro), value);
        } else {
            //订单表
            String[] split = value.toString().split(",");
            String proId = split[2];
            context.write(new Text(proId), value);
        }
    }
}
