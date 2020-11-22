package MapReduce.test;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean2 implements WritableComparable<FlowBean2> {
    private String phone;
    private long upFlow;
    private long dFlow;
    private long sumFlow;

    public FlowBean2() {
    }

    public void set(String phone, long upFlow, long dFlow) {
        this.phone = phone;
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = upFlow + dFlow;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public FlowBean2(long upFlow, long dFlow, long sumFlow) {
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = sumFlow;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(phone);
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(dFlow);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.phone = dataInput.readUTF();
        this.upFlow = dataInput.readLong();
        this.dFlow = dataInput.readLong();
        this.sumFlow = this.upFlow + this.dFlow;
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
        return this.phone + "," + this.upFlow + ", " + this.dFlow + "," + this.sumFlow;
    }

    @Override
    public int compareTo(FlowBean2 o) {
        //手机作为key 流量作为排序
        return (int) (o.getSumFlow() - this.getSumFlow());
    }
}
