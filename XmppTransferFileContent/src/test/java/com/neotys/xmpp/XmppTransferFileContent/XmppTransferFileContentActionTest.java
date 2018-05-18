package com.neotys.xmpp.XmppTransferFileContent;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XmppTransferFileContentActionTest {
	@Test
	public void shouldReturnType() {
		final XmppTransferFileContentAction action = new XmppTransferFileContentAction();
		assertEquals("XmppTransferFileContent", action.getType());
	}

}
