package apps;

import java.io.IOException;

import core.Msg;
import exceptions.IWProtocolException;
import phy.PhyProtocol;
import sp.SPAckMsg;
import sp.SPMeasurementMsg;
import sp.SPProtocol;

/*
 * Sensor server application that receives measurements and acknowledges them.
 */
public class SensorServer {
	private static final int DEFAULT_SERVER_PORT = 4999;
	private static final String DEFAULT_SERVER_ID = "server";
	private static final String ACK_STATUS_OK = "ok";

	public static void main(String[] args) {
		int serverPort = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_SERVER_PORT;
		String serverId = args.length > 1 ? args[1] : DEFAULT_SERVER_ID;

		PhyProtocol phy = new PhyProtocol(serverPort);
		SPProtocol sp = new SPProtocol(phy);

		while (true) {
			try {
				Msg msg = sp.receive();
				if (msg instanceof SPMeasurementMsg measurement) {
					System.out.println("Measurement from " + measurement.getSender()
							+ " to " + measurement.getReceiver()
							+ ": " + measurement.getValue());

					SPAckMsg ack = new SPAckMsg(serverId, measurement.getSender(), ACK_STATUS_OK);
					sp.send(ack, measurement.getConfiguration());
				}
			} catch (IOException | IWProtocolException e) {
				e.printStackTrace();
			}
		}
	}
}