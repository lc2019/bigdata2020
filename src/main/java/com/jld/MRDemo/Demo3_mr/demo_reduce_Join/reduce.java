package MapReduce.Demo3_mr.demo_reduce_Join;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class reduce  extends Reducer<Text,Text,Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //k2v2--->k3v3
        /**
         * 遍历集合
         * 将k3v3拼接写入---p0001  p0001,小米5,1000,2000  1001,20150710,p0001,2
         */
        String first="";
        String sec="";
        for (Text value : values) {
            if (value.toString().startsWith("p")){
                 first = value.toString();
            }else {
                sec+=value.toString();
            }
        }
        context.write(key,new Text(first+"\t"+sec));
    }
}
