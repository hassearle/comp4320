package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
      IGremlin gremlin = new GremlinImpl();
      IErrorDetection errorDetec = new ErrorDetectionImpl();
      ISegmentation segmentation = new SegmentationImpl();
      float probabilityOfError = 0.3f;
      int highestSequenceReceived;
      clientSocket.send(sendPacket);
      System.out.println("Client sent HTTP request");
      DatagramPacket receivePacket, corruptedPacket; 
      ArrayList<DatagramPacket> packets = new ArrayList<DatagramPacket>();
      DatagramPacket[] packetsArr = new DatagramPacket[1];
      boolean isCorrupt = false, nullArrived = false, allArrived = false;
      while(!allArrived && !nullArrived) {
        receivePacket = new DatagramPacket(receiveData, receiveData.length);

        //read datafram from server
        clientSocket.receive(receivePacket);
        String receivedData =
            new String(receivePacket.getData());
        if (receivedData.length() != 0) {
          System.out.println("\n\nPacket received from server: " + receivedData);
          // PACKET PROCESSING HERE
          corruptedPacket = gremlin.corruptPackets(receivePacket, probabilityOfError);
          isCorrupt = !errorDetec.detectErrors(corruptedPacket);
          if(isCorrupt) {
            System.out.println("Error detected in packet");
          }

          packets.add(receivePacket);
          packetsArr = (DatagramPacket[])packets.toArray();
          highestSequenceReceived = segmentation.highestSequenceCheck(packetsArr);

          if(segmentation.checkNullArrived(receivePacket)){
            nullArrived = true;
          }
          if(segmentation.checkAllArrived(packetsArr, highestSequenceReceived)){
            allArrived = true;
          }
        }
        segmentation.reassemblePackets(packetsArr, packetsArr.length);

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