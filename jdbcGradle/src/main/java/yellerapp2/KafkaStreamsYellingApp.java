package yellerapp2;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

public class KafkaStreamsYellingApp {

 public static void main(String[] args) throws Exception {

Connection c = null;
try {
   Class.forName("org.postgresql.Driver");
   c = DriverManager
      .getConnection("jdbc:postgresql://localhost:5432/testdb", "testdb", "testdb");
} catch (Exception e) {
   e.printStackTrace();
   System.err.println(e.getClass().getName()+": "+e.getMessage());
   System.exit(0);
}
System.out.println("Opened database successfully");

  }
}
