package MapReduce.Demo_mr_sort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flowReduce extends Reducer<Text, FlowBean3, Text, FlowBean3> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean3> values, Context context) throws IOException, InterruptedException {
        FlowBean3 bean = new FlowBean3();
        long up = 0;
        long down = 0;
        for (FlowBean3 value : values) {
            up += value.getUpFlow();
            down += value.getdFlow();
        }
        //
        bean.set(key.toString(),up,down);
        context.write(key,bean);
    }
}
