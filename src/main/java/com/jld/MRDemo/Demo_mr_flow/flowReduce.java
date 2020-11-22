package MapReduce.Demo_mr_flow;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class flowReduce extends Reducer<Text, FlowBean2, Text, FlowBean2> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean2> values, Context context) throws IOException, InterruptedException {
        FlowBean2 bean = new FlowBean2();
        long up = 0;
        long down = 0;
        for (FlowBean2 value : values) {
            up += value.getUpFlow();
            down += value.getdFlow();
        }
        //
        bean.set(up,down);
        context.write(key,bean);
    }
}
