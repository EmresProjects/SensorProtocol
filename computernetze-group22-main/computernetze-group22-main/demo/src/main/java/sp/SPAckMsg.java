/**
 * Bearbeitet von: Murat Süleyman Özbek
 * Matrikelnummer: 819482
 */
package sp;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;

public class SPAckMsg extends SPMsg {
	protected static final String SP_ACK_HEADER = "ack";

	private String status;

	public SPAckMsg() {
	}

	public SPAckMsg(String sender, String receiver, String status) {
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
		create();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void create() {
		requireToken(this.sender);
		requireToken(this.receiver);
		requireToken(this.status);

		String crcData = SP_ACK_HEADER + " " + this.sender + " " + this.receiver + " " + this.status;
		this.crc = calculateCrc(crcData);
		super.create(crcData + " " + this.crc);
	}

	
	@Override
	public void create(String data) {
		String[] parts = data.trim().split("\\s+");
		if (parts.length != 3) {
			throw new IllegalArgumentException("ACK messages require sender, receiver and status");
		}

		this.sender = parts[0];
		this.receiver = parts[1];
		this.status = parts[2];
		create();
	}

	@Override
	public SPAckMsg parse(String sentence) throws IWProtocolException {
		String[] parts = sentence.trim().split("\\s+");
		if (parts.length != 5 || !SP_ACK_HEADER.equals(parts[0])) {
			throw new IllegalMsgException();
		}

		SPAckMsg msg = new SPAckMsg();
		msg.sender = parts[1];
		msg.receiver = parts[2];
		msg.status = parts[3];
		try {
			msg.crc = Long.parseLong(parts[4]);
		} catch (NumberFormatException e) {
			throw new IllegalMsgException();
		}
// Das CRC-Feld selbst darf nicht in die CRC-Berechnung einfließen.
		String crcData = SP_ACK_HEADER + " " + parts[1] + " " + parts[2] + " " + parts[3];
		msg.validateCrc(crcData);
		msg.setConfiguration(this.config);
		msg.createFromParsedSentence(sentence.trim());
		return msg;
	}

	private void createFromParsedSentence(String sentence) {
		super.create(sentence);
	}
}
