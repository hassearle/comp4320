package server;
import java.net.*;
public class UDPPacket extends DatagramPacket {
	public int checksum;
	public int sequenceNumber;
	public UDPPacket(byte[] buf, int length, InetAddress address, int port, int sequenceNumber) {
		this.checksum = this.calculateChecksum(buf);
		this.sequenceNumber = sequenceNumber;
		buf = this.includeHeaderLines(buf);
		super(buf, buf.length, address, port);
	}
	public int calculateChecksum(byte[] buf) {
		int sum = 0;
		for (byte b in buf) {
			sum += (int) b;
		}
		return sum;
	}
	// adds checksum and ssequence number to data buffer
	public byte[] includeHeaderLines(byte[] buf) {
		String str = new String(buf);
		str = "Checksum: " + this.checksum + "\r\n"
			+ "Sequence Number: " + this.sequenceNumber +"\r\n\r\n"
			+ str;
		return str.getBytes();
	}
}