package client;

import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String args[]) throws Exception 
    { 
      //create input stream
      BufferedReader inFromUser = 
        new BufferedReader(new InputStreamReader(System.in)); 
  
      //create client socket
      DatagramSocket clientSocket = new DatagramSocket(); 
  
      //translate hostname to IP address USING DNS 
      InetAddress IPAddress = InetAddress.getByName("hostname"); 
  
      byte[] sendData = new byte[1024]; 
      byte[] receiveData = new byte[1024]; 
  
      String sentence = inFromUser.readLine(); 
      sendData = sentence.getBytes();         

      //create datagram with data-to-send, length, IP addr, port
      DatagramPacket sendPacket = 
         new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 
  
      //send datagram to server
      clientSocket.send(sendPacket); 
  
      DatagramPacket receivePacket = 
         new DatagramPacket(receiveData, receiveData.length); 
  
      //read datafram from server
      clientSocket.receive(receivePacket); 
  
      String modifiedSentence = 
          new String(receivePacket.getData()); 
  
      System.out.println("FROM SERVER:" + modifiedSentence); 
      clientSocket.close(); 
      } 
} 