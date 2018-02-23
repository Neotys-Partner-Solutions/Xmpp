package com.neotys.xmpp.XmppReceiveMessage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XmppReceiveMessageActionTest {
	@Test
	public void shouldReturnType() {
		final XmppReceiveMessageAction action = new XmppReceiveMessageAction();
		assertEquals("XmppReceiveMessage", action.getType());
	}

}
