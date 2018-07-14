package server;
import java.net.DatagramPacket;
public class ErrorDetectionImpl implements IErrorDetection {
    // checks the validity of a packet given a hash/checksum
    public boolean detectErrors(DatagramPacket packet) {
        String data = new String(packet.getData());
        String[] headerLinesAndData = data.split("\r\n\r\n");
        String[] headers = headerLinesAndData[0].split("\r\n");
        String payload = headerLinesAndData[1];
        int checksum = Integer.parseInt(headers[0].split(" ")[1]);
        int sum = 0;
        for (byte b : payload.getBytes()) {
            sum += (int) b;
        }
        return sum == checksum;
    }
}