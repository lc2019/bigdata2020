package MapReduce.diypartition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


/**
 * keyin 偏移量 当读取到的一行数据相对整个文件的偏移量 LongWriteable
 * valuein  Text 文本
 * keyout   Text 用户自定义方法返回的key的类型
 * valueout 返回的value 此处是LongWriteable
 */
public class wordMapper extends Mapper<LongWritable, Text,Text,LongWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.切分单词
        String[] words = value.toString().split(" ");
        //2.单词转换
        for (String word : words) {
            //3.写入上下文
            context.write(new Text(word),new LongWritable(1));
        }

    }
}
