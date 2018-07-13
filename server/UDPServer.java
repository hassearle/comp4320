package server;
import java.io.*;
import java.net.*; 
  
class UDPServer { 
  public static void main(String args[]) throws Exception 
    { 
      //create datagram socket at port 9876
      DatagramSocket serverSocket = new DatagramSocket(10024);
  
      byte[] receiveData = new byte[1024]; 
      byte[] sendData  = new byte[1024]; 
  
      while(true) 
        { 
        //create space for recieved datagram
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
           serverSocket.receive(receivePacket); 

        //recieve datagram
            String sentence = new String(receivePacket.getData()); 
  
        //Get IP addr port #, of sender
          InetAddress IPAddress = receivePacket.getAddress(); 
        //^
          int port = receivePacket.getPort(); 
  
                      String capitalizedSentence = sentence.toUpperCase(); 

          sendData = capitalizedSentence.getBytes(); 
  
          //create datagram to send to client
          DatagramPacket sendPacket = 
             new DatagramPacket(sendData, sendData.length, IPAddress, 
                               port); 

         //write out datagram to socket
          serverSocket.send(sendPacket);
        }
        //end of while loop, loop pack and wait for another datagram 
    } 
}  