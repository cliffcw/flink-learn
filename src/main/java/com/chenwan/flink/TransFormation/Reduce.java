package com.chenwan.flink.TransFormation;

import com.chenwan.flink.function.SourceFromMySqLFunction;
import com.chenwan.flink.pojo.entity.User;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @program: flink-learn
 * @description: Reduce
 * @author: cliffcw
 * @create: 2019-06-16 12:59
 */
public class Reduce {

    /**
     * @Description: Reduce 返回单个的结果值，并且 reduce 操作每处理一个元素总是创建一个新值。常用的方法有 average, sum, min, max, count，使用 reduce 方法都可实现。
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<User> userDataStreamSource = env.addSource(new SourceFromMySqLFunction());

        SingleOutputStreamOperator<User> reduce = userDataStreamSource.keyBy(new KeySelector<User, Long>() {
            @Override
            public Long getKey(User value) throws Exception {
                return value.age;
            }
        }).reduce(new ReduceFunction<User>() {
            //先将数据流进行 keyby 操作，因为执行 reduce 操作只能是 KeyedStream，然后将 student 对象的 age 做了一个求平均值的操作。
            @Override
            public User reduce(User value1, User value2) throws Exception {
                User user = new User();
                user.name = value1.name + value2.name;
                user.id = (value1.id + value2.id) / 2;
                user.password = value1.password + value2.password;
                user.age = (value1.age + value2.age) / 2;

                return user;
            }
        });

        reduce.print();

        env.execute("Flink Reduce");
    }
}
