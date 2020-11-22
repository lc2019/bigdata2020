package MapReduce.Demo_join;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class JoinBean implements Writable {
    private  String oid;
    private String uid;
    private String name;
    private  int age;
    private  String gender;
    private String friend;

    public JoinBean() {
    }
    public void set(String oid, String uid, String name, int age, String gender, String friend) {
        this.oid = oid;
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.friend = friend;
    }
    public JoinBean(String oid, String uid, String name, int age, String gender, String friend) {
        this.oid = oid;
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.friend = friend;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "JoinBean{" +
                "oid='" + oid + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", friend='" + friend + '\'' +
                '}';
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(oid);
        dataOutput.writeUTF(uid);
        dataOutput.writeUTF(name);
        dataOutput.writeInt(age);
        dataOutput.writeUTF(gender);
        dataOutput.writeUTF(friend);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.oid= dataInput.readUTF();
        this.uid= dataInput.readUTF();
        this.name= dataInput.readUTF();
        this.age= dataInput.readInt();
        this.gender= dataInput.readUTF();
        this.friend= dataInput.readUTF();
    }
}
