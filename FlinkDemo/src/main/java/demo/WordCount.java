package demo;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class WordCount {
    public static void main(String[] args) throws Exception {
        //创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //从文件中读取数据
        DataSource<String> dataSource =
                env.readTextFile("D:\\JAVA_DEMO\\demo-collection\\FlinkDemo\\src\\main\\resources\\Word.txt");

        //转换数据格式,输出(word,1)
        FlatMapOperator<String, Tuple2<String, Integer>> flatMap =
                dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        String[] words = s.split(" ");
                        for (String word : words) {
                            Tuple2<String, Integer> wordTuple2 = Tuple2.of(word, 1);
                            collector.collect(wordTuple2);
                        }
                    }
                });

        //分组,按第0位进行分组
        UnsortedGrouping<Tuple2<String, Integer>> group = flatMap.groupBy(0);

        //聚合第1位的值
        AggregateOperator<Tuple2<String, Integer>> sum = group.sum(1);

        //输出
        sum.print();
    }
}
