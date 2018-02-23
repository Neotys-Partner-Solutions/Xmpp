package com.neotys.xmpp.XmppSendMessage;

import java.util.concurrent.LinkedBlockingQueue;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import com.neotys.xmpp.XmppPacket.XmppPacket;


public class IncomingXMPPMessageListner implements IncomingChatMessageListener {
	private final LinkedBlockingQueue<XmppPacket> PacketList;
	

	public IncomingXMPPMessageListner(LinkedBlockingQueue<XmppPacket> queue)
	{
		PacketList=queue;
	}
	@Override
	public void newIncomingMessage(EntityBareJid arg0, Message arg1, Chat arg2) {
		// TODO Auto-generated method stub
		PacketList.add(new XmppPacket(arg0, arg1, arg2));
	}

}
