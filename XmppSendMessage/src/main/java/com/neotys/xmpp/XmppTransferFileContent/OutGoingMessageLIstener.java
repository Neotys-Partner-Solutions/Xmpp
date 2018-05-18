package com.neotys.xmpp.XmppTransferFileContent;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;

import com.neotys.extensions.action.engine.Context;

public class OutGoingMessageLIstener implements OutgoingChatMessageListener {

	Context NLContext;
	
	public OutGoingMessageLIstener(Context context)
	{
		NLContext=context;
	}
	@Override
	public void newOutgoingMessage(EntityBareJid arg0, Message arg1, Chat arg2) {
		// TODO Auto-generated method stub
		NLContext.getLogger().debug("Message "+arg1.getBody()+ "sent to "+arg0);
	}

}
