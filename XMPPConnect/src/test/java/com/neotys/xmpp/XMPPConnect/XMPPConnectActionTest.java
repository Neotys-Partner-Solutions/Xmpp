package com.neotys.xmpp.XMPPConnect;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XMPPConnectActionTest {
	@Test
	public void shouldReturnType() {
		final XMPPConnectAction action = new XMPPConnectAction();
		assertEquals("XMPPConnect", action.getType());
	}

}
