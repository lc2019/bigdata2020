package MapReduce.Demo_mr_flow;

import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean2 implements Writable {
    private long upFlow;
    private long dFlow;
    private long sumFlow;

    public void set(long upFlow, long dFlow) {
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = upFlow+dFlow;
    }

    public FlowBean2(long upFlow, long dFlow, long sumFlow) {
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = sumFlow;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(dFlow);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow = dataInput.readLong();
        this.dFlow = dataInput.readLong();
        this.sumFlow=this.upFlow+this.dFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getdFlow() {
        return dFlow;
    }

    public void setdFlow(long dFlow) {
        this.dFlow = dFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    @Override
    public String toString() {
        return  upFlow + ", " + dFlow + "," + sumFlow;
    }

    public FlowBean2() {
    }
}
