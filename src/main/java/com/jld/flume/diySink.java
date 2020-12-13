package com.jld.flume;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class diySink extends AbstractSink implements Configurable {
    private String prefix;
    private String suffix;
    private static final Logger logger = LoggerFactory.getLogger(diySink.class);

    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix", "atguigu");
        suffix = context.getString("suffix", ":event");
    }

    /**
     * 获取channel
     * 从channel获取事务
     * 发送数据
     *
     * @return
     * @throws EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {

        Status status = Status.READY;

        //获取当前channel
        Channel channel = getChannel();
        //开启事务
        Transaction tx = channel.getTransaction();

        Event event;
        tx.begin();
        try {

            event = channel.take();
            //获取到的event进行输出
            logger.info(prefix + new String(event.getBody()) + suffix);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            status = Status.BACKOFF;
        } finally {
            tx.close();
        }
        return status;
    }
}
