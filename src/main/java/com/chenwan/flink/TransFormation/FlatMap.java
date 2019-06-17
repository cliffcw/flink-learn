package com.chenwan.flink.TransFormation;

import com.chenwan.flink.function.SourceFromMySqLFunction;
import com.chenwan.flink.pojo.entity.User;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @program: flink-learn
 * @description: FlatMap操作
 * @author: cliffcw
 * @create: 2019-06-16 12:00
 */
public class FlatMap {

    /** * @Description:  flatMap 采用一条记录并输出零个，一个或多个
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<User> user = env.addSource(new SourceFromMySqLFunction());

        SingleOutputStreamOperator<User> flatMap = user.flatMap(new FlatMapFunction<User, User>() {
            @Override
            public void flatMap(User value, Collector<User> out) throws Exception {
                //聚集出id为奇数的数据
                if (value.id % 2 == 1) {
                    out.collect(value);
                }
            }
        });
        flatMap.print();
        env.execute("Flink flatMap");
    }
}
