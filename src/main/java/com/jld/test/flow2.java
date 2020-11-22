package MapReduce.test;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flow2 {
    public static class flow2Mapper extends Mapper<Text, FlowBean2, FlowBean2, NullWritable> {
        @Override
        protected void map(Text key, FlowBean2 value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    public static class flow2Reduce extends Reducer<FlowBean2, NullWritable, FlowBean2, NullWritable> {
        @Override
        protected void reduce(FlowBean2 key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }
}