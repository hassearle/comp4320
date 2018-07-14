package client;
import java.net.DatagramPacket;
public class SegmentationImpl implements ISegmentation {
    // accepts data in bytes, converts into equally sized packets
    public DatagramPacket[] segmentPackets(byte[] data, int packetSize) {
        // code here
        return new DatagramPacket[1];
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
}