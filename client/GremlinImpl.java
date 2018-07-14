package client;
import java.net.DatagramPacket;
import java.util.Random;

public class GremlinImpl implements IGremlin {
   // corrupts a packet if the provided probability is met
   public DatagramPacket corruptPackets(DatagramPacket packet, float probability) {
      // code here
      float corruptArr[] = new float[pcktSz];
      float cl = pcktSz * probability;
      int corruptsLeft = (cl % 0 == 0) ? ((int)cl) : ((int) cl++);
      
      //fix out of bounds
      boolean temp = recursCorrupt(corruptArr, corruptsLeft);
              
      return packet;
   }
   
   public boolean recursCorrupt(float[] cArr, float count){
      if(count <= 0){
         return true;
      }
      else{
         Random rand = new Random();
         int n = rand.nextInt((100)+1);
         if(cArr[n] == 0){
            //corrupt
            cArr[n] = 1;
            count--;
         }
      }
      return recursCorrupt(cArr, count);
   }
   
   public int ttlCorrupt() {
      Random rand = new Random();
      int n = rand.nextInt((100)+1);
      if(n <= 50){
         return 1;
      }
      if(n > 50 || n <= 70 ){
         return 2;
      }
      if(n > 70 || n <= 100){
         return 3;
      }
      return -1;
   }
}