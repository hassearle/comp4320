package client;
import java.net.DatagramPacket;
public class ErrorDetectionImpl implements IErrorDetection {
    // checks the validity of a packet given a hash/checksum
    public boolean detectErrors(DatagramPacket packet, int hash) {
        return packet.hashCode() == hash;
    }
}