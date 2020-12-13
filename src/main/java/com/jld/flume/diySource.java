package com.jld.flume;

import org.apache.flume.Context;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

import java.util.HashMap;

/**
 * 获取event的前缀
 */
public class diySource extends AbstractSource implements Configurable, PollableSource {
    private String prefix;

    /**
     * 封装数据为event，将数据放入channel
     * 自定义source
     *
     * @return
     * @throws EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {
        Status status = Status.READY;
        //实现事件
        SimpleEvent simpleEvent = new SimpleEvent();

        HashMap<String, String> headers = new HashMap<>();
        //放入数据
        headers.put("source", "diysource");
        for (int i = 0; i < 9; i++) {
            //event数据格式 header+body--->map+byte[]
            simpleEvent.setHeaders(headers);
            simpleEvent.setBody((prefix + i).getBytes());
            try {
                getChannelProcessor().processEvent(simpleEvent);
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
                //状态
                status = Status.BACKOFF;
            }
        }
        return status;
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }


    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix", "atguigu");
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

}
