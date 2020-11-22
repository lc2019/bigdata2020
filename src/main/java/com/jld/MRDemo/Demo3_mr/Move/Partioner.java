package MapReduce.Demo3_mr.Move;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partioner extends Partitioner<MovieBean, LongWritable> {
    @Override
    public int getPartition(MovieBean key, LongWritable value, int numReduceTasks) {
        return (key.getMovie().hashCode() & Integer.MAX_VALUE)% numReduceTasks;
    }
}
