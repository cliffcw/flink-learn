package com.chenwan.flink.TransFormation;

import com.chenwan.flink.function.SourceFromMySqLFunction;
import com.chenwan.flink.pojo.entity.User;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @program: flink-learn
 * @description: Filter
 * @author: cliffcw
 * @create: 2019-06-16 12:06
 */
public class Filter {

    /**
     * @Description: 跟进条件判断输出结果
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment evn = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<User> userSource = evn.addSource(new SourceFromMySqLFunction());

        SingleOutputStreamOperator<User> filter = userSource.filter(new FilterFunction<User>() {
            //判断出id大于5的数据
            @Override
            public boolean filter(User value) throws Exception {
                if (value.id > 5) {
                    return true;
                }
                return false;
            }
        });

        filter.print();

        evn.execute("Flink Filter");
    }

}
