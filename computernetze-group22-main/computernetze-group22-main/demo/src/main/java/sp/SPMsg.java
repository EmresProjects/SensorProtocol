/**
 * Bearbeitet von: Murat Süleyman Özbek
 * Matrikelnummer: 819482
 */
package sp;

import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

import core.Msg;
import exceptions.BadChecksumException;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;


public class SPMsg extends Msg {
	protected static final String SP_HEADER = "sp ";

	protected String sender;
	protected String receiver;
	protected long crc;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public long getCrc() {
		return crc;
	}

	public void setCrc(long crc) {
		this.crc = crc;
	}

	/*
 * Konkrete Nachrichtenklassen überschreiben diese Methode, um die Nachricht
 * aus ihren jeweiligen Feldern zu erzeugen. Die Basisklasse kann nur eine
 * bereits erzeugte Nachricht speichern.
 */
	public void create() {
		if (this.data == null) {
			throw new IllegalArgumentException("Cannot create a generic SP message");
		}
	}

	/*
 * Fügt den SP-Header vor dem Nachrichteninhalt ein.
 */
	@Override
	public void create(String data) {
		String sentence = SP_HEADER + data.trim();
		this.data = sentence;
		this.dataBytes = sentence.getBytes(StandardCharsets.UTF_8);
	}

	/*
 * Prüft den SP-Header, bestimmt den konkreten Nachrichtentyp
 * und delegiert das Parsing an die passende Klasse.
 */
	@Override
	public Msg parse(String sentence) throws IWProtocolException {
		if (sentence == null || !sentence.startsWith(SP_HEADER)) {
			throw new IllegalMsgException();
		}

		String data = sentence.substring(SP_HEADER.length()).trim();
		if (data.isEmpty()) {
			throw new IllegalMsgException();
		}

		String[] parts = data.split("\\s+", 2);
		SPMsg pdu;
		if (SPMeasurementMsg.SP_MEASUREMENT_HEADER.equals(parts[0])) {
			pdu = new SPMeasurementMsg();
		} else if (SPAckMsg.SP_ACK_HEADER.equals(parts[0])) {
			pdu = new SPAckMsg();
		} else {
			throw new IllegalMsgException();
		}

		pdu.setConfiguration(this.config);
		return pdu.parse(data);
	}
// Die CRC32 wird über den UTF-8-codierten Nachrichteninhalt ohne CRC-Feld berechnet.
	protected static long calculateCrc(String data) {
		CRC32 crc32 = new CRC32();
		crc32.update(data.getBytes(StandardCharsets.UTF_8));
		return crc32.getValue();
	}

	protected void validateCrc(String data) throws BadChecksumException {
		long expected = calculateCrc(data);
		if (expected != this.crc) {
			throw new BadChecksumException(expected, this.crc);
		}
	}

	protected static void requireToken(String token) {
		if (token == null || token.isBlank()) {
			throw new IllegalArgumentException("Sensor protocol fields must not be empty");
		}
	}

	@Override
	public String toString() {
		if (this.data != null) {
			return this.data;
		}
		if (this.dataBytes != null) {
			return new String(this.dataBytes, StandardCharsets.UTF_8);
		}
		return "";
	}
}