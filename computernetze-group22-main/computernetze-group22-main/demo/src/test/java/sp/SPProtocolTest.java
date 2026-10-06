package sp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.jupiter.api.Test;

import core.Configuration;
import core.Msg;
import core.Protocol;
import exceptions.IWProtocolException;
import phy.PhyConfiguration;
import phy.PhyProtocol;

class SPProtocolTest {
	    // Prüft, ob SPProtocol die vollständige Nachricht an die PHY-Schicht weitergibt.

    @Test
	void sendDelegatesMessageStringToPhy() throws IOException, IWProtocolException {
		PhyProtocol phy = mock(PhyProtocol.class);
		SPProtocol protocol = new SPProtocol(phy);
		SPMeasurementMsg msg = new SPMeasurementMsg("sensor01", "server", 42.5);
		PhyConfiguration config = new PhyConfiguration(InetAddress.getByName("localhost"), 9999, Protocol.proto_id.SP);

		protocol.send(msg, config);
// Kontrolliert, ob PHY mit der richtigen Nachricht und Konfiguration aufgerufen wurde.

		verify(phy).send(eq(msg.toString()), eq(config));
	}

// Prüft, ob eine empfangene PHY-Nachricht geparst wird und ihre Konfiguration behält.
	@Test
	void receiveParsesIncomingPhyDataAndKeepsConfiguration() throws IOException, IWProtocolException {
		// Der Mockito-Test fuer receive() wurde mit KI-Unterstuetzung entworfen und hier angepasst.
		PhyProtocol phy = mock(PhyProtocol.class);
		SPProtocol protocol = new SPProtocol(phy);
		SPMeasurementMsg created = new SPMeasurementMsg("sensor01", "server", 42.5);
		PhyConfiguration config = new PhyConfiguration(InetAddress.getByName("localhost"), 7777, Protocol.proto_id.SP);
		when(phy.receive()).thenReturn(new IncomingMsg(created.toString(), config));

		Msg received = protocol.receive();

		SPMeasurementMsg measurement = assertInstanceOf(SPMeasurementMsg.class, received);
		assertEquals("sensor01", measurement.getSender());
		assertEquals("server", measurement.getReceiver());
		assertEquals(42.5, measurement.getValue());
		assertEquals(config, measurement.getConfiguration());
	}

    // Simuliert eine eingehende Nachricht der PHY-Schicht ohne echte Netzwerkverbindung.
	private static class IncomingMsg extends Msg {
		IncomingMsg(String data, Configuration config) {
			this.data = data;
			this.dataBytes = data.getBytes();
			this.config = config;
		}

		@Override
		protected void create(String sentence) {
		}

		@Override
		protected Msg parse(String sentence) {
			return this;
		}
	}
}