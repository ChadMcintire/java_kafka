//<T> is a generic, instantiate a class in a type safe manner 
// implements a way of immitating multiple inheritance in java
package yellerapp1.util.serializer;

import yellerapp1.collectors.FixedSizePriorityQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.unit.Map;


public class JsonSerializer<T> implements Serializer<T> {



    private Gson gson = new Gson();

    //allows the parent child class to provide a specific implementation of a
    //method provided by a parent class
    @Override
    public void configure(Map<String, ?> map, boolean b) {
       
    }

    public JsonSerializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(FixedSizePriorityQueue.class, 
        new FixedSizedPriorityQueueAdapter().nullSafe());
        gson = builder.create();
    
    @Override
    public byte[] serialize(String topic, T t) {
        return gson.toJson(t).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public void close() {

    }

