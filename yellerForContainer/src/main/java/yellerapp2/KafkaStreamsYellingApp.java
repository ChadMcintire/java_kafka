package yellerapp2;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Properties;


public class KafkaStreamsYellingApp {

 public static void main(String[] args) throws Exception {

  Properties props = new Properties();

  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "yelling_app_id");   
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-service:9092");

  StreamsConfig streamsConfig = new StreamsConfig(props);

  Serde<String> stringSerde = Serdes.String();

  StreamsBuilder builder = new StreamsBuilder();

  KStream<String, String> simpleFirstStream = builder.stream("text-input",
    Consumed.with(stringSerde, stringSerde));

  KStream<String, String> upperCasedStream =
    simpleFirstStream.mapValues(String::toUpperCase);

  upperCasedStream.to( "text-output",Produced.with(stringSerde, stringSerde)); 

  KafkaStreams kafkaStreams = new KafkaStreams(builder.build(),streamsConfig);


  kafkaStreams.start();

  Thread.sleep(120000);
  kafkaStreams.close();

  }
}
