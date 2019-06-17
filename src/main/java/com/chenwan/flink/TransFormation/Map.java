package com.chenwan.flink.TransFormation;

import com.chenwan.flink.function.SourceFromMySqLFunction;
import com.chenwan.flink.pojo.entity.User;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @program: flink-learn
 * @description: Map操作
 * @author: cliffcw
 * @create: 2019-06-16 11:03
 */
public class Map {

    /** * @Description:  map输入一个数据流，输出也是一个数据流
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<User> user = env.addSource(new SourceFromMySqLFunction());

        SingleOutputStreamOperator<User> map = user.map(new MapFunction<User, User>() {
            //将每个user的name加上"cliffcw"
            @Override
            public User map(User value) throws Exception {
                User user = new User();
                user.id = value.id;
                user.name = value.name+"cliffcw";
                user.password = value.password;
                user.age = value.age;

                return user;
            }
        });
        map.print();
        env.execute("Flink Map");
    }
}
