package client;
import java.net.DatagramPacket;
public class SegmentationImpl implements ISegmentation {
    // accepts data in bytes, converts into equally sized packets
    public DatagramPacket[] segmentPackets(byte[] data, int packetSize) {
        // code here
        return new DatagramPacket[1];
    }
    //accepts an array of packets and reassembles them into a byte array
    public byte[] reassemblePackets(DatagramPacket[] packets) {
        // code here
        return new byte[1];
    }
}