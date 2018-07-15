package client;
import java.net.DatagramPacket;
public class SegmentationImpl implements ISegmentation {
    SegmentationImpl(){
    }
    // accepts data in bytes, converts into equally sized packets
    public DatagramPacket[] segmentPackets(byte[] data, int packetSize) {
        /* Calculate the number of empty items there will be in the new array
         * if necessary where the new array is a size that is a multiple 
         * of packetSize
         */
        int remainingItems = 0;
        if(data.length % packetSize != 0){
            remainingItems = packetSize - (data.length % packetSize);
        }

        /* Creates a new byte array, dataNew, which is just a copy of data,
         * but it;s size is a multiple of packetSize any extra bytes are
         * extended with zeros.
         */
        byte[] dataNew = new byte[data.length +remainingItems];
        for(int i = 0; i < dataNew.length; i++){
            if(i < data.length) {
                dataNew[i] = data[i];
            } else {
                dataNew[i] = 0;
            }
        }

        int numberOfPackets = dataNew.length / packetSize;
        //if(data.length % packetSize != 0) {
        //    numberOfPackets++;
        //}

        /* Create new DatagramPacket array, packetsOut, that will be
         * returned by the function. Create a new byte array, temp,
         * that will record the byte arrays stored in the packetOut.
         */
        DatagramPacket[] packetsOut = new DatagramPacket[numberOfPackets];
        byte[] temp = new byte[packetSize];

        /* The outer for loop iterates through each DatagramPacket in
         * packetsOut implementing the byte array made from the inner loop
         * to each item. The inner loop assigns temp to byte arrays made up
         * all the bytes in data.
         */
        for(int i = 0; i < numberOfPackets; i++){
            for(int j = 0; j < packetSize; j++){
                //add code for out of bounds of data when copying to temp
                temp[j] = dataNew[(i*packetSize) + j];
            }
            this.includeHeaderLines(temp, i);
            packetsOut[i] = new DatagramPacket(temp, packetSize);
            System.out.println("Created segment packet: " + new String(packetsOut[i].getData()));
        }
        return packetsOut;
    }
    //accepts an array of packets and reassembles them into a byte array
    public byte[] reassemblePackets(DatagramPacket[] packets, int packetSize) {
        // code here
        DatagramPacket[] packetsNew = new DatagramPacket[packets.length];
        for(int i = 0; i < packetsNew.length; i++){
            packetsNew[packets[i].getOffset()] = packets[i];
        }

        byte[] bytesOut = new byte[packetsNew.length * packetSize];
        byte[] temp;
        for(int i = 0; i < packetsNew.length; i++){
            temp = packetsNew[i].getData();
            for(int j = 0; j < packetSize; j++){
                bytesOut[(i * packetsNew.length) + j] = temp[j];
            }
        }
        return bytesOut;
    }

    public boolean checkAllArrived(DatagramPacket[] packets, int highestNumber) {
        return packets.length == highestNumber + 1;
    }

    public boolean checkNullArrived(DatagramPacket packet){
        if(packet.getData().length == 1 && packet.getData()[1] == 0) {
            return true;
        }
        return false;
    }

    public int highestSequenceCheck(DatagramPacket[] packets){
        int highestSequence = 0;
        for(DatagramPacket packet : packets) {
            if(highestSequence < checkSequenceNumber(packet)){
                highestSequence = checkSequenceNumber(packet);
            }
        }
        return highestSequence;
    }

    public int calculateChecksum(byte[] buf) {
        int sum = 0;
        for (byte b : buf) {
            sum += (int) b;
        }
        return sum;
    }
    // adds checksum and sequence number to data buffer
    public byte[] includeHeaderLines(byte[] buf, int sequenceNumber) {
        String str = new String(buf);
        str = "Checksum: " + this.calculateChecksum(buf)+ "\r\n"
                + "Sequence Number: " + sequenceNumber +"\r\n\r\n"
                + str;
        System.out.println(str);
        return str.getBytes();
    }

    public int checkSequenceNumber(DatagramPacket packet) {
        String data = new String(packet.getData());
        String[] headerLinesAndData = data.split("\r\n\r\n");
        String[] headers = headerLinesAndData[0].split("\r\n");
        int sequenceNumber = Integer.parseInt(headers[1].split(" ")[1]);

        return sequenceNumber;
    }
}