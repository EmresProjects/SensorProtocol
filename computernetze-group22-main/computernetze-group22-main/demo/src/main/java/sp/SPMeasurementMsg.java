/**
 * Bearbeitet von: Murat Süleyman Özbek
 * Matrikelnummer: 819482
 */
package sp;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;


public class SPMeasurementMsg extends SPMsg {
	protected static final String SP_MEASUREMENT_HEADER = "meas";

	private double value;
	private boolean valueSet;

	public SPMeasurementMsg() {
	}

	public SPMeasurementMsg(String sender, String receiver, double value) {
		this.sender = sender;
		this.receiver = receiver;
		setValue(value);
		create();
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
		this.valueSet = true;
	}

	@Override
	public void create() {
		requireToken(this.sender);
		requireToken(this.receiver);
		if (!this.valueSet) {
			throw new IllegalArgumentException("Measurement value is missing");
		}
// Für die Prüfsumme werden Nachrichtentyp, Sender, Empfänger und Messwert verwendet.
		String crcData = SP_MEASUREMENT_HEADER + " " + this.sender + " " + this.receiver + " "
				+ Double.toString(this.value);
		this.crc = calculateCrc(crcData);
		super.create(crcData + " " + this.crc);
	}

	/*
	 * Create from the data fields without protocol header and without CRC:
	 * <sender> <receiver> <value>
	 */
	@Override
	public void create(String data) {
		String[] parts = data.trim().split("\\s+");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Measurement messages require sender, receiver and value");
		}

		this.sender = parts[0];
		this.receiver = parts[1];
		try {
			setValue(Double.parseDouble(parts[2]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Measurement value must be a number", e);
		}
		create();
	}

	@Override
	public SPMeasurementMsg parse(String sentence) throws IWProtocolException {
		String[] parts = sentence.trim().split("\\s+");
		if (parts.length != 5 || !SP_MEASUREMENT_HEADER.equals(parts[0])) {
			throw new IllegalMsgException();
		}

		SPMeasurementMsg msg = new SPMeasurementMsg();
		msg.sender = parts[1];
		msg.receiver = parts[2];
		try {
			msg.value = Double.parseDouble(parts[3]);
			msg.valueSet = true;
			msg.crc = Long.parseLong(parts[4]);
		} catch (NumberFormatException e) {
			throw new IllegalMsgException();
		}

		String crcData = SP_MEASUREMENT_HEADER + " " + parts[1] + " " + parts[2] + " " + parts[3];
		// Die empfangene CRC wird vor der Rückgabe der Nachricht validiert.
        msg.validateCrc(crcData);
		msg.setConfiguration(this.config);
		msg.createFromParsedSentence(sentence.trim());
		return msg;
	}

	private void createFromParsedSentence(String sentence) {
		super.create(sentence);
	}
}