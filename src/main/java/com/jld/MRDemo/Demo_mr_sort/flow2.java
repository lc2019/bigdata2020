package MapReduce.Demo_mr_sort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flow2 {
    public static class flow2Mapper extends Mapper<Text, FlowBean3, FlowBean3, NullWritable> {
        @Override
        protected void map(Text key, FlowBean3 value, Context context) throws IOException, InterruptedException {
            context.write(value, NullWritable.get());
        }
    }

    public static class flow2Reduce extends Reducer<FlowBean3, NullWritable, FlowBean3, NullWritable> {
        @Override
        protected void reduce(FlowBean3 key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }
}