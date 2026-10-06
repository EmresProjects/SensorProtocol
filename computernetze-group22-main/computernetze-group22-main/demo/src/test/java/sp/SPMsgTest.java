package sp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Msg;
import exceptions.BadChecksumException;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;

class SPMsgTest {
	@Test
	@DisplayName("Measurement message creation includes header and CRC")
	void createMeasurementTest() {
		SPMeasurementMsg msg = new SPMeasurementMsg();
		msg.create("sensor01 server 42.5");

		long crc = SPMsg.calculateCrc("meas sensor01 server 42.5");
		assertEquals("sp meas sensor01 server 42.5 " + crc, msg.toString());
	}

	@Test
	@DisplayName("Measurement message parsing validates fields and CRC")
	void parseMeasurementTest() throws IWProtocolException {
		SPMeasurementMsg created = new SPMeasurementMsg("sensor01", "server", 42.5);

		Msg parsed = new SPMsg().parse(created.toString());

		SPMeasurementMsg measurement = assertInstanceOf(SPMeasurementMsg.class, parsed);
		assertEquals("sensor01", measurement.getSender());
		assertEquals("server", measurement.getReceiver());
		assertEquals(42.5, measurement.getValue());
		assertEquals(created.getCrc(), measurement.getCrc());
	}

	@Test
	@DisplayName("ACK message creation and parsing")
	void parseAckTest() throws IWProtocolException {
		SPAckMsg created = new SPAckMsg("server", "sensor01", "ok");

		Msg parsed = new SPMsg().parse(created.toString());

		SPAckMsg ack = assertInstanceOf(SPAckMsg.class, parsed);
		assertEquals("server", ack.getSender());
		assertEquals("sensor01", ack.getReceiver());
		assertEquals("ok", ack.getStatus());
		assertEquals(created.getCrc(), ack.getCrc());
	}

	@Test
	@DisplayName("Invalid SP header is rejected")
	void invalidHeaderTest() {
		assertThrows(IllegalMsgException.class, () -> new SPMsg().parse("slp meas sensor01 server 42.5 1"));
	}

	@Test
	@DisplayName("Invalid CRC is rejected")
	void invalidCrcTest() {
		assertThrows(BadChecksumException.class, () -> new SPMsg().parse("sp meas sensor01 server 42.5 1"));
	}
}
