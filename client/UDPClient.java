package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.nio.file.Files;
public class UDPClient {
public static final String FILE_NAME = "TestFile.html";
    public static void main(String args[]) throws Exception 
    { 
      
      //create client socket
      DatagramSocket clientSocket = new DatagramSocket(); 
  
      //translate hostname to IP address USING DNS 
      InetAddress IPAddress = InetAddress.getByName("tux055");
  
      byte[] sendData = new byte[1024]; 

      IGremlin gremlin = new GremlinImpl();
      IErrorDetection errorDetec = new ErrorDetectionImpl();
      IAssembler assembler = new AssemblerImpl();

      float probabilityOfError = Float.parseFloat(args[0]);
  
      sendData = new String("GET " + FILE_NAME + " HTTP/1.0").getBytes();

      //create datagram with data-to-send, length, IP addr, port
      DatagramPacket sendPacket = 
         new DatagramPacket(sendData, sendData.length, IPAddress, 10024);
  

      //send datagram to server
      clientSocket.send(sendPacket);
      System.out.println("Client sent HTTP request");;//prepare to receive packets 
      while(!assembler.isComplete()) {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        //read datagram from server
        clientSocket.receive(receivePacket);
        String receivedData = new String(receivePacket.getData());
        if (receivedData.length() != 0) {
          assembler.newPacketIn(receivePacket);
        }
      }
      writeDataToFile(assembler.getAssembledDocument(), FILE_NAME);
    }
    
    public int calculateChecksum(byte[] buf) {
        int sum = 0;
        for (byte b : buf) {
            sum += (int) b;
        }
        return sum;
    }

    public boolean writeDataToFile(byte[] data, String fileName) {
        try {
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            System.out.println("Wrote data to " + FILE_NAME);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}