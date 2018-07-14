package server;
import java.net.DatagramPacket;
public interface ISegmentation {
	// accepts data in bytes, converts into equally sized packets
	DatagramPacket[] segmentPackets(byte[] data, int packetSize);
	//accepts an array of packets and reassembles them into a byte array
	byte[] reassemblePackets(DatagramPacket[] packets, int packetSize);
}