package com.chenwan.flink.source;

import com.chenwan.flink.function.SourceFromMySqLFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @program: flink-learn
 * @description:
 * @author: cliffcw
 * @create: 2019-06-13 21:20
 */
public class SourceFromMysql {
    /**
     * @Description: 从mysql读取数据输出
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new SourceFromMySqLFunction()).print();

        env.execute("Flink add data sourc");
    }
}
