package MapReduce.Demo3_mr.TopN;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements Writable {
    private String uid;
    private String oid;
    private String item;
    private int num;
    private float price;

    public OrderBean() {}

    public OrderBean(String uid, String oid, String item, int num, float price) {
        this.uid = uid;
        this.oid = oid;
        this.item = item;
        this.num = num;
        this.price = price;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "oid='" + uid + '\'' +
                ", uid='" + oid + '\'' +
                ", item='" + item + '\'' +
                ", num=" + num +
                ", price=" + price +
                '}';
    }

    //序列化
    @Override
    public void write(DataOutput Output) throws IOException {
        Output.writeUTF(this.uid);
        Output.writeUTF(this.oid);
        Output.writeUTF(this.item);
        Output.writeInt(this.num);
        Output.writeFloat(this.price);
    }


    //反序列化
    @Override
    public void readFields(DataInput Input) throws IOException {
        this.uid=Input.readUTF();
        this.oid=Input.readUTF();
        this.item=Input.readUTF();
        this.num=Input.readInt();
        this.price=Input.readFloat();
    }
}
