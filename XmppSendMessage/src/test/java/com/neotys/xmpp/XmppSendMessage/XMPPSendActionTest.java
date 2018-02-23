package com.neotys.xmpp.XmppSendMessage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XMPPSendActionTest {
	@Test
	public void shouldReturnType() {
		final XMPPSendAction action = new XMPPSendAction();
		assertEquals("XMPPSend", action.getType());
	}

}
