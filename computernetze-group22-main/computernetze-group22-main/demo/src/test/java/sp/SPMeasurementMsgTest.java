package sp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import exceptions.BadChecksumException;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;

class SPMeasurementMsgTest {
	@Test
	void createBuildsMeasurementSentenceWithCrc() {
		SPMeasurementMsg msg = new SPMeasurementMsg("sensor01", "server", 42.5);

		long crc = SPMsg.calculateCrc("meas sensor01 server 42.5");
		assertEquals("sp meas sensor01 server 42.5 " + crc, msg.getData());
		assertEquals(crc, msg.getCrc());
	}

	@Test
	void parseReturnsMeasurementFields() throws IWProtocolException {
		SPMeasurementMsg created = new SPMeasurementMsg("sensor01", "server", 42.5);

		SPMeasurementMsg parsed = new SPMeasurementMsg().parse("meas sensor01 server 42.5 " + created.getCrc());

		assertEquals("sensor01", parsed.getSender());
		assertEquals("server", parsed.getReceiver());
		assertEquals(42.5, parsed.getValue());
		assertEquals(created.getCrc(), parsed.getCrc());
	}

	@Test
	void parseRejectsInvalidValue() {
		assertThrows(IllegalMsgException.class,
				() -> new SPMeasurementMsg().parse("meas sensor01 server loud 123"));
	}

	@Test
	void parseRejectsInvalidCrc() {
		assertThrows(BadChecksumException.class,
				() -> new SPMeasurementMsg().parse("meas sensor01 server 42.5 123"));
	}
}