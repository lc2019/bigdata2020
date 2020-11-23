package com.jld.MRDemo.Demo3_mr.Move;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partioner extends Partitioner<MapReduce.Demo3_mr.Move.MovieBean, LongWritable> {
    @Override
    public int getPartition(MapReduce.Demo3_mr.Move.MovieBean key, LongWritable value, int numReduceTasks) {
        return (key.getMovie().hashCode() & Integer.MAX_VALUE)% numReduceTasks;
    }
}
