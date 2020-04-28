package yellerapp1;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class ZMartKafkaStreamsApp {

 public static void main(String[] args) throws Exception {

  

  StreamsConfig streamsConfig = new StreamsConfig(getProperties());

  Serde<String> purchaseSerde = StreamsSerdes.PurchaseSerde();
  Serde<PurchasePattern> purchasePatternSerde = StreamsSerdes.PurchasePatternSerde();
  Serde<RewardAccumulator> rewardAccumulatorSerde = StreamsSerdes.RewardAccumulatorSerde();

  
  //used everytime
  Serde<String> stringSerde = Serdes.String();
  StreamsBuilder builder = new StreamsBuilder();

  //make source node and masking node
  KStream<String, Purchase> purchaseKStream =
  streamsBuilder.stream("transcation", Consumed.with(stringSerde,
  purchaseSerde)).mapValues(p -> Purchase.builder(p).maskCreditCard().build());

  ////second processing node
  //KStream<String, PurchasePattern> patternKStream = 
  //  purchaseKStream.mapValues(purchase -> 
  //  PurchasePattern.builder(purchase).build());
 
  ////sink to the patterns topic
  //patternKStream.to("patterns", Produced.with(stringSerde,
  //purchasePatternSerde));

  ////rewards object node
  //KStream<String, RewardAccumulator> rewardsKStream =
  //purchaseKStream.mapValues(purchase -> 
  //RewardAccumulator.builder(purchase).build());

  ////rewards object sink node
  //rewardsKStream.to("rewards", Produced.with(stringSerde,
  //rewardAccumulatorSerde));

  ////sink processor to sink the purchase data
  //purchaseKStream.to("purchase", Produced.with(stringSerde, purchaseSerde));
  
  kafkaStreams.start();
  kafkaStreams.close();

  }

  public static Properties getProperties() {
      Properties props = new Properties();
      props.put(StreamsConfig.CLIENT_ID_CONFIG, "FirstZmart-Kafka-Streams-Client");
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "zmart-purchases");
      //required
      props.put(StreamsConfig.APPLICATION_ID_CONFIG, "FirstZmart-Kafka-Streams-App");
      //required
      props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

      //replication factor for changelog topics and repartition topics created
      //by the application
      props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
      
      //implements the timestampextractor interface
      props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
      return props;
  }

}
