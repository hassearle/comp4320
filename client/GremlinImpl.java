package client;
import java.net.DatagramPacket;
import java.util.Random;

public class GremlinImpl implements IGremlin {
   // corrupts a packet if the provided probability is met
   public DatagramPacket corruptPackets(DatagramPacket packet, float probability) {
      // code here
      if (isCorrupt(probability) == true) {
         //break
      }
      else{
         //dont break
      }
              
      return packet;
   }
   
   public boolean isCorrupt(float randSeed){
      Random rand = new Random();
      float n = rand.nextFloat() * 100; //gen rand# b/w 0-100
      
      //takes rand %, if rand% is less than prob, return true
      boolean value = (n <= randSeed) ? (true) : (false);
         
      //test
      System.out.println(n);
      System.out.print(value);
         
      return value; 
   }
}