package MapReduce.Demo3_mr.Move;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class MoiveRateTopMapper extends Mapper<LongWritable, Text,MovieBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //{"movie":"2797"， "rate" : "4"，"timestamp" : "978302039"， "uid": "1"}
        String json = value.toString();
        //解析json
        ObjectMapper objectMapper = new ObjectMapper();
        MovieBean bean = objectMapper.readValue(json, MovieBean.class);
        context.write(bean,NullWritable.get());

    }
}
