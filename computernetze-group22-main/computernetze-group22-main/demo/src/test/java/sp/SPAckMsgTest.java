package sp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import exceptions.BadChecksumException;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;

class SPAckMsgTest {
	@Test
	void createBuildsAckSentenceWithCrc() {
		SPAckMsg msg = new SPAckMsg("server", "sensor01", "ok");

		long crc = SPMsg.calculateCrc("ack server sensor01 ok");
		assertEquals("sp ack server sensor01 ok " + crc, msg.getData());
		assertEquals(crc, msg.getCrc());
	}

	@Test
	void parseReturnsAckFields() throws IWProtocolException {
		SPAckMsg created = new SPAckMsg("server", "sensor01", "ok");

		SPAckMsg parsed = new SPAckMsg().parse("ack server sensor01 ok " + created.getCrc());

		assertEquals("server", parsed.getSender());
		assertEquals("sensor01", parsed.getReceiver());
		assertEquals("ok", parsed.getStatus());
		assertEquals(created.getCrc(), parsed.getCrc());
	}

	@Test
	void parseRejectsInvalidFormat() {
		assertThrows(IllegalMsgException.class, () -> new SPAckMsg().parse("ack server sensor01"));
	}

	@Test
	void parseRejectsInvalidCrc() {
		assertThrows(BadChecksumException.class, () -> new SPAckMsg().parse("ack server sensor01 ok 123"));
	}
}
