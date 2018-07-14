package client;

import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String args[]) throws Exception 
    { 
      
      //create client socket
      DatagramSocket clientSocket = new DatagramSocket(); 
  
      //translate hostname to IP address USING DNS 
      InetAddress IPAddress = InetAddress.getByName("tux055");
  
      byte[] sendData = new byte[1024]; 
      byte[] receiveData = new byte[1024]; 
  
      sendData = new String("GET TestFile.html HTTP/1.0").getBytes();

      //create datagram with data-to-send, length, IP addr, port
      DatagramPacket sendPacket = 
         new DatagramPacket(sendData, sendData.length, IPAddress, 10024);
  
      //send datagram to server
      clientSocket.send(sendPacket);
      System.out.println("Client sent HTTP request");
      DatagramPacket receivePacket; 
      while(true) {
        receivePacket = new DatagramPacket(receiveData, receiveData.length); 
        //read datafram from server
        clientSocket.receive(receivePacket); 
        String receivedData =
            new String(receivePacket.getData());
            if (receivedData.length() != 0) {
              System.out.println("Packet received from server: " + receivedData);
            } 
      }
    }
    public int calculateChecksum(byte[] buf) {
        int sum = 0;
        for (byte b : buf) {
            sum += (int) b;
        }
        return sum;
    }
}