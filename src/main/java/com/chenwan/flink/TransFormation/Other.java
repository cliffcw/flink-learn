package com.chenwan.flink.TransFormation;

/**
 * @program: flink-learn
 * @description: 其他数据转换操作
 * @author: cliffcw
 * @create: 2019-06-16 13:10
 */
public class Other {

    /**
     * Fold
     * Fold 通过将最后一个文件夹流与当前记录组合来推出 KeyedStream。 它会发回数据流。
     * KeyedStream.fold("1", new FoldFunction<Integer, String>() {
     *
     * @Override public String fold(String accumulator, Integer value) throws Exception {
     * return accumulator + "=" + value;
     * }
     * });
     * <p>
     * <p>
     * Aggregations
     * DataStream API 支持各种聚合，例如 min，max，sum 等。 这些函数可以应用于 KeyedStream 以获得 Aggregations 聚合。
     * max 和 maxBy 之间的区别在于 max 返回流中的最大值，但 maxBy 返回具有最大值的键， min 和 minBy 同理。
     * KeyedStream.sum(0)
     * KeyedStream.sum("key")
     * KeyedStream.min(0)
     * KeyedStream.min("key")
     * KeyedStream.max(0)
     * KeyedStream.max("key")
     * KeyedStream.minBy(0)
     * KeyedStream.minBy("key")
     * KeyedStream.maxBy(0)
     * KeyedStream.maxBy("key")
     * <p>
     * 注意：Window  Window 函数允许按时间或其他条件对现有 KeyedStream 进行分组。 以下是以 10 秒的时间窗口聚合：
     * inputStream.keyBy(0).window(Time.seconds(10));
     * Flink 定义数据片段以便（可能）处理无限数据流。 这些切片称为窗口。 此切片有助于通过应用转换处理数据块。 要对流进行窗口化，我们需要分配一个可以进行分发的键和一个描述要对窗口化流执行哪些转换的函数
     * <p>
     * <p>
     * WindowAll
     * windowAll 函数允许对常规数据流进行分组。 通常，这是非并行数据转换，因为它在非分区数据流上运行。
     * 与常规数据流功能类似，我们也有窗口数据流功能。 唯一的区别是它们处理窗口数据流。 所以窗口缩小就像 Reduce 函数一样，Window fold 就像 Fold 函数一样，并且还有聚合。
     * inputStream.keyBy(0).windowAll(Time.seconds(10));
     * <p>
     * <p>
     * Union
     * Union 函数将两个或多个数据流结合在一起。 这样就可以并行地组合数据流。 如果我们将一个流与自身组合，那么它会输出每个记录两次。
     * inputStream.union(inputStream1, inputStream2, ...);
     * <p>
     * <p>
     * Window join
     * 通过一些 key 将同一个 window 的两个数据流 join 起来。
     * 如下 在 5 秒的窗口中连接两个流，其中第一个流的第一个属性的连接条件等于另一个流的第二个属性。
     * inputStream.join(inputStream1)
     * .where(0).equalTo(1)
     * .window(Time.seconds(5))
     * .apply (new JoinFunction () {...});
     * <p>
     * <p>
     * Split
     * 根据条件将流拆分为两个或多个流。 当您获得混合流并且您可能希望单独处理每个数据流时，可以使用此方法。
     * SplitStream<Integer> split = inputStream.split(new OutputSelector<Integer>() {
     * @Override public Iterable<String> select(Integer value) {
     * List<String> output = new ArrayList<String>();
     * if (value % 2 == 0) {
     * output.add("even");
     * }
     * else {
     * output.add("odd");
     * }
     * return output;
     * }
     * });
     * <p>
     * <p>
     * Select
     * 允许从拆分流中选择特定流。
     * SplitStream<Integer> split;
     * DataStream<Integer> even = split.select("even");
     * DataStream<Integer> odd = split.select("odd");
     * DataStream<Integer> all = split.select("even","odd");
     * <p>
     * <p>
     * Project
     * 允许您从事件流中选择属性子集，并仅将所选元素发送到下一个处理流。
     * DataStream<Tuple4<Integer, Double, String, String>> in = // [...]
     * DataStream<Tuple2<String, String>> out = in.project(3,2);
     * <p>
     * 上述函数从给定记录中选择属性号 2 和 3。 以下是示例输入和输出记录：
     * (1,10.0,A,B)=> (B,A)
     * (2,20.0,C,D)=> (D,C)
     */
    public static void main(String[] args) {
        String str = "/**\n" +
                "     *\n" +
                "     * Fold\n" +
                "     * Fold 通过将最后一个文件夹流与当前记录组合来推出 KeyedStream。 它会发回数据流。\n" +
                "     * KeyedStream.fold(\"1\", new FoldFunction<Integer, String>() {\n" +
                "     *    @Override\n" +
                "     *    public String fold(String accumulator, Integer value) throws Exception {\n" +
                "     *         return accumulator + \"=\" + value;\n" +
                "     *     }\n" +
                "     *  });\n" +
                "     *\n" +
                "     *\n" +
                "     * Aggregations\n" +
                "     * DataStream API 支持各种聚合，例如 min，max，sum 等。 这些函数可以应用于 KeyedStream 以获得 Aggregations 聚合。\n" +
                "     * max 和 maxBy 之间的区别在于 max 返回流中的最大值，但 maxBy 返回具有最大值的键， min 和 minBy 同理。\n" +
                "     * KeyedStream.sum(0)\n" +
                "     * KeyedStream.sum(\"key\")\n" +
                "     * KeyedStream.min(0)\n" +
                "     * KeyedStream.min(\"key\")\n" +
                "     * KeyedStream.max(0)\n" +
                "     * KeyedStream.max(\"key\")\n" +
                "     * KeyedStream.minBy(0)\n" +
                "     * KeyedStream.minBy(\"key\")\n" +
                "     * KeyedStream.maxBy(0)\n" +
                "     * KeyedStream.maxBy(\"key\")\n" +
                "     *\n" +
                "     * 注意：Window  Window 函数允许按时间或其他条件对现有 KeyedStream 进行分组。 以下是以 10 秒的时间窗口聚合：\n" +
                "     * inputStream.keyBy(0).window(Time.seconds(10));\n" +
                "     * Flink 定义数据片段以便（可能）处理无限数据流。 这些切片称为窗口。 此切片有助于通过应用转换处理数据块。 要对流进行窗口化，我们需要分配一个可以进行分发的键和一个描述要对窗口化流执行哪些转换的函数\n" +
                "     *\n" +
                "     *\n" +
                "     * WindowAll\n" +
                "     * windowAll 函数允许对常规数据流进行分组。 通常，这是非并行数据转换，因为它在非分区数据流上运行。\n" +
                "     * 与常规数据流功能类似，我们也有窗口数据流功能。 唯一的区别是它们处理窗口数据流。 所以窗口缩小就像 Reduce 函数一样，Window fold 就像 Fold 函数一样，并且还有聚合。\n" +
                "     * inputStream.keyBy(0).windowAll(Time.seconds(10));\n" +
                "     *\n" +
                "     *\n" +
                "     * Union\n" +
                "     * Union 函数将两个或多个数据流结合在一起。 这样就可以并行地组合数据流。 如果我们将一个流与自身组合，那么它会输出每个记录两次。\n" +
                "     * inputStream.union(inputStream1, inputStream2, ...);\n" +
                "     *\n" +
                "     *\n" +
                "     * Window join\n" +
                "     * 通过一些 key 将同一个 window 的两个数据流 join 起来。\n" +
                "     * 如下 在 5 秒的窗口中连接两个流，其中第一个流的第一个属性的连接条件等于另一个流的第二个属性。\n" +
                "     * inputStream.join(inputStream1)\n" +
                "     *            .where(0).equalTo(1)\n" +
                "     *            .window(Time.seconds(5))\n" +
                "     *            .apply (new JoinFunction () {...});\n" +
                "     *\n" +
                "     *\n" +
                "     * Split\n" +
                "     * 根据条件将流拆分为两个或多个流。 当您获得混合流并且您可能希望单独处理每个数据流时，可以使用此方法。\n" +
                "     * SplitStream<Integer> split = inputStream.split(new OutputSelector<Integer>() {\n" +
                "     *     @Override\n" +
                "     *     public Iterable<String> select(Integer value) {\n" +
                "     *         List<String> output = new ArrayList<String>();\n" +
                "     *         if (value % 2 == 0) {\n" +
                "     *             output.add(\"even\");\n" +
                "     *         }\n" +
                "     *         else {\n" +
                "     *             output.add(\"odd\");\n" +
                "     *         }\n" +
                "     *         return output;\n" +
                "     *     }\n" +
                "     * });\n" +
                "     *\n" +
                "     *\n" +
                "     * Select\n" +
                "     * 允许从拆分流中选择特定流。\n" +
                "     * SplitStream<Integer> split;\n" +
                "     * DataStream<Integer> even = split.select(\"even\");\n" +
                "     * DataStream<Integer> odd = split.select(\"odd\");\n" +
                "     * DataStream<Integer> all = split.select(\"even\",\"odd\");\n" +
                "     *\n" +
                "     *\n" +
                "     * Project\n" +
                "     * 允许您从事件流中选择属性子集，并仅将所选元素发送到下一个处理流。\n" +
                "     * DataStream<Tuple4<Integer, Double, String, String>> in = // [...]\n" +
                "     * DataStream<Tuple2<String, String>> out = in.project(3,2);\n" +
                "     *\n" +
                "     * 上述函数从给定记录中选择属性号 2 和 3。 以下是示例输入和输出记录：\n" +
                "     * (1,10.0,A,B)=> (B,A)\n" +
                "     * (2,20.0,C,D)=> (D,C)\n" +
                "     */";

        System.out.println(str);
    }

}
