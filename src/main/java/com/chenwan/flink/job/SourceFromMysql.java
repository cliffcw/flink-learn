package com.chenwan.flink.job;

import com.chenwan.flink.source.SourceFromMySQLFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @program: flink-learn
 * @description:
 * @author: cliffcw
 * @create: 2019-06-13 21:20
 */
public class SourceFromMysql {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.addSource(new SourceFromMySQLFunction()).print();

        env.execute("Flink add data sourc");
    }
}
