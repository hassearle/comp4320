package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Z_TestGremlin {
   public static void main(String args[]) throws Exception 
   { 
   
      Scanner reader = new Scanner(System.in);  // Reading from System.in
      System.out.println("Enter a number: ");
      float n = reader.nextFloat(); // Scans the next token of the input as an int.
      reader.close();
      
      byte[] receiveData = new byte[1024]; 
      DatagramPacket packet = 
         new DatagramPacket(receiveData, receiveData.length);
      
      GremlinImpl obj = new GremlinImpl();
      obj.corruptPackets(packet, n);
   }
}