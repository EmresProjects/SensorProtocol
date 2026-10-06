/**
 * Bearbeitet von: Murat Süleyman Özbek
 * Matrikelnummer: 819482
 */
package sp;

import java.io.IOException;

import core.Configuration;
import core.Msg;
import core.Protocol;
import exceptions.IWProtocolException;
import exceptions.IllegalMsgException;
import phy.PhyConfiguration;
import phy.PhyProtocol;


public class SPProtocol implements Protocol {
	private final PhyProtocol phy;

	public SPProtocol(PhyProtocol proto) {
		this.phy = proto;
	}

	@Override
	public void send(String s, Configuration config) throws IOException, IWProtocolException {
		SPMsg parser = new SPMsg();
		send((SPMsg) parser.parse(s), config);
	}

	
	public void send(SPMsg msg, Configuration config) throws IOException, IWProtocolException {
		if (msg.getData() == null) {
			msg.create();
		}
		// Die Protocol-Klasse übergibt nur die fertige String-Nachricht an die PHY-Schicht.
        this.phy.send(msg.toString(), config);
	}

	
	@Override
	public Msg receive() throws IOException, IWProtocolException {
		Msg in = this.phy.receive();

		if (!(in.getConfiguration() instanceof PhyConfiguration phyConfig)
				|| phyConfig.getPid() != proto_id.SP) {
			throw new IllegalMsgException();
		}

		SPMsg parser = new SPMsg();
		parser.setConfiguration(in.getConfiguration());
		SPMsg msg = (SPMsg) parser.parse(in.getData());
		msg.setConfiguration(in.getConfiguration());
		return msg;
	}
}