package com.neotys.xmpp.XmppPacket;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

public class XmppPacket {
	
	private EntityBareJid Jid;
	private Message message;
	private Chat chat;
	public XmppPacket(EntityBareJid jid, Message message, Chat chat) {
		super();
		Jid = jid;
		this.message = message;
		this.chat = chat;
	}
	public String GetMessageConent()
	{
		return message.getBody();
	}
	
	public EntityBareJid getDest()
	{
		return Jid;
	}
	
}
