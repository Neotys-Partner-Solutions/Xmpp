package com.neotys.xmpp.XmppTransfertFileContent;

import static org.junit.Assert.assertEquals;

import com.neotys.xmpp.XmppTransfertFileContent.XmppTransferFileContentAction;
import org.junit.Test;

public class XmppTransferFileContentActionTest {
	@Test
	public void shouldReturnType() {
		final XmppTransferFileContentAction action = new XmppTransferFileContentAction();
		assertEquals("XmppTransferFileContent", action.getType());
	}

}
