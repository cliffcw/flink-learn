package com.chenwan.flink.example;

import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @program: flink-learn
 * @description: 实时统计成交额 1，实时统计每个类别的成交额度  2，实时统计全部类别的成交额度。
 * @author: cliffcw
 * @create: 2019-06-17 22:28
 */
public class GroupedProcessingTimeWindowExample {

    /**
     * @Description: 模拟数据源
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    private static class DataSource extends RichParallelSourceFunction<Tuple2<String, Integer>> {
        private volatile boolean running = true;

        @Override
        public void run(SourceContext<Tuple2<String, Integer>> ctx) throws Exception {
            Random random = new Random(System.currentTimeMillis());
            while (running) {
                Thread.sleep((getRuntimeContext().getIndexOfThisSubtask() + 1) * 1000 + 500);
                String key = "类别" + (char) ('A' + random.nextInt(3));
                int value = random.nextInt(10) + 1;
                System.out.println(String.format("Emit:\t(%s, %d)", key, value));
                ctx.collect(new Tuple2<>(key, value));
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(2);

        DataStreamSource<Tuple2<String, Integer>> dataSource = env.addSource(new DataSource());

        KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = dataSource.keyBy(0);

        keyedStream.sum(1).keyBy(new KeySelector<Tuple2<String, Integer>, Object>() {

            @Override
            public Object getKey(Tuple2<String, Integer> value) throws Exception {
                return "";
            }
        }).fold(new HashMap<String, Integer>(), new FoldFunction<Tuple2<String, Integer>, Map<String, Integer>>() {
            //fold和reduce类似
            @Override
            public Map<String, Integer> fold(Map<String, Integer> accumulator, Tuple2<String, Integer> value) throws Exception {
                accumulator.put(value.f0, value.f1);
                return accumulator;
            }

        }).addSink(new SinkFunction<Map<String, Integer>>() {
            @Override
            public void invoke(Map<String, Integer> value, Context context) throws Exception {
                System.out.println(value.values().stream().mapToInt(v -> v).sum());
            }
        });

        dataSource.addSink(new SinkFunction<Tuple2<String, Integer>>() {
            @Override
            public void invoke(Tuple2<String, Integer> value, Context context) throws Exception {
                System.out.println(String.format("Get:\t(%s, %d)", value.f0, value.f1));
            }
        });

        env.execute("test");
    }
}
