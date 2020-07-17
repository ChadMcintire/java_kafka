package yellerapp2;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;

import com.google.gson.Gson;

import java.util.Properties;

public class KafkaStreamsYellingApp {

 public static void main(String[] args) throws Exception {

  //Connection c = null;
  //try {
  //   Class.forName("org.postgresql.Driver");
  //   c = DriverManager
  //      .getConnection("jdbc:postgresql://localhost:5432/testdb", "testdb", "testdb");
  //} catch (Exception e) {
  //   e.printStackTrace();
  //   System.err.println(e.getClass().getName()+": "+e.getMessage());
  //   System.exit(0);
  //}
  //System.out.println("Opened database successfully");

  Properties props = new Properties();

  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "yelling_app_id");
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

  StreamsConfig streamsConfig = new StreamsConfig(props);

  Serde<String> stringSerde = Serdes.String();

  StreamsBuilder builder = new StreamsBuilder();

  KStream<String, String> simpleFirstStream = builder.stream("text-input",
    Consumed.with(stringSerde, stringSerde));
//
//  KStream<String, String> upperCasedStream =
//    simpleFirstStream.mapValues(String::toUpperCase);
//
//  upperCasedStream.to( "text-output",Produced.with(stringSerde, stringSerde));
//

  KStream<String, String> upperCasedStream =
    simpleFirstStream.mapValues(String::toUpperCase);
//
//  upperCasedStream.foreach((key, value) -> System.out.println(key + " => " + value));
  
  DbWriter a1 = new DbWriter();
  //a1.test();

  upperCasedStream.foreach((key, value) -> a1.test(value));
//  upperCasedStream.foreach((key, value) -> System.out.println(key + " => " + value));
//  KStream<String, String> source = builder.stream( "in-stream");
//  source.foreach(new ForeachAction<String, String>() {
//    void apply(String key, String value) {
//        System.out.println(key + ": " + value);
//    }
//  });

  KafkaStreams kafkaStreams = new KafkaStreams(builder.build(),streamsConfig);

  kafkaStreams.start();

  Thread.sleep(120000);
  kafkaStreams.close();

  }
}
