package apps;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

import core.Msg;
import core.Protocol;
import exceptions.IWProtocolException;
import phy.PhyConfiguration;
import phy.PhyProtocol;
import sp.SPAckMsg;
import sp.SPMeasurementMsg;
import sp.SPProtocol;

/*
 * Sensor application that periodically sends measurements and waits for ACKs.
 */
public class SensorClient {
	private static final String DEFAULT_SERVER_NAME = "localhost";
	private static final int DEFAULT_SERVER_PORT = 4999;
	private static final int DEFAULT_LOCAL_PORT = 6789;
	private static final String DEFAULT_SENSOR_ID = "sensor01";
	private static final String DEFAULT_SERVER_ID = "server";
	private static final double MIN_SOUND_VALUE = 30.0;
	private static final double MAX_SOUND_VALUE = 90.0;
	private static final long SEND_INTERVAL_MS = 2000;

	public static void main(String[] args) {
		String sensorId = args.length > 0 ? args[0] : DEFAULT_SENSOR_ID;
		int localPort = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_LOCAL_PORT;
		String serverName = args.length > 2 ? args[2] : DEFAULT_SERVER_NAME;
		int serverPort = args.length > 3 ? Integer.parseInt(args[3]) : DEFAULT_SERVER_PORT;
		String serverId = args.length > 4 ? args[4] : DEFAULT_SERVER_ID;

		PhyProtocol phy = new PhyProtocol(localPort);
		SPProtocol sp = new SPProtocol(phy);
		Random random = new Random();

		try {
			PhyConfiguration serverConfig = new PhyConfiguration(
					InetAddress.getByName(serverName), serverPort, Protocol.proto_id.SP);

			while (true) {
				double value = nextSoundValue(random);
				SPMeasurementMsg measurement = new SPMeasurementMsg(sensorId, serverId, value);
				sp.send(measurement, serverConfig);
				System.out.println("Sent measurement: " + value);

				Msg response = sp.receive();
				if (response instanceof SPAckMsg ack) {
					System.out.println("Received ACK from " + ack.getSender() + ": " + ack.getStatus());
				}

				Thread.sleep(SEND_INTERVAL_MS);
			}
		} catch (IOException | IWProtocolException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	static double nextSoundValue(Random random) {
		double value = MIN_SOUND_VALUE + random.nextDouble() * (MAX_SOUND_VALUE - MIN_SOUND_VALUE);
		return Math.round(value * 10.0) / 10.0;
	}
}