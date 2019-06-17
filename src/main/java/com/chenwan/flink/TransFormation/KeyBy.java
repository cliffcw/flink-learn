package com.chenwan.flink.TransFormation;

import com.chenwan.flink.function.SourceFromMySqLFunction;
import com.chenwan.flink.pojo.entity.User;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @program: flink-learn
 * @description: KeyBy
 * @author: cliffcw
 * @create: 2019-06-16 12:15
 */
public class KeyBy {

    /** * @Description:  在逻辑上是基于key对流进行分区。在内部，使用hash函数对流进行分区。它返回KeyedDataStream数据流
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment evn = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<User> userDataStreamSource = evn.addSource(new SourceFromMySqLFunction());

        KeyedStream<User, Long> keyBy = userDataStreamSource.keyBy(new KeySelector<User, Long>() {
            //对age做keyBy分区操作
            @Override
            public Long getKey(User value) throws Exception {
                return value.age;
            }
        });

        keyBy.print();

        evn.execute("Flink KeyBy");
    }
}
