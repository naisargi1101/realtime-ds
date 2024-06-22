import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import java.util.Properties;

public class FlinkJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "flink_consumer");

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>("data_stream", new SimpleStringSchema(), properties);

        env.addSource(consumer)
            .map(new MapFunction<String, String>() {
                @Override
                public String map(String value) {
                    // Process data
                    return value;
                }
            })
            .print();

        env.execute("Flink Job");
    }
}
