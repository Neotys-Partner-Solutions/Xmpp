package com.neotys.xmpp.XmppSendMessage;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.xmpp.XmppPacket.XmppPacket;



public final class XMPPSendActionEngine implements ActionEngine {
	private String ChatWith;
	private String Message;
	private AbstractXMPPConnection XMPPconnection;
	
	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();

		for(ActionParameter parameter:parameters) {
			switch(parameter.getName()) 
			{
			case  XMPPSendAction.ChatWith:
				ChatWith = parameter.getValue();
				break;
			case  XMPPSendAction.Message:
				Message = parameter.getValue();
				break;
						
				
			}
		}
		
		if (Strings.isNullOrEmpty(ChatWith)) {
			return getErrorResult(context, sampleResult, "Invalid argument: ChatWith cannot be null "
					+ XMPPSendAction.ChatWith + ".", null);
		}
		if (Strings.isNullOrEmpty(Message)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Message cannot be null "
					+ XMPPSendAction.Message + ".", null);
		}
		
		XMPPconnection= (AbstractXMPPConnection)context.getCurrentVirtualUser().get("XMPPConnection");
		if(XMPPconnection==null)
			return getErrorResult(context, sampleResult, "There is no connection open with the XMPP server with this VU ",null);
		
		if(XMPPconnection.isConnected())
		{
			sampleResult.sampleStart();

			ChatManager chatManager = ChatManager.getInstanceFor(XMPPconnection);
			LinkedBlockingQueue<XmppPacket> packetqueue=new LinkedBlockingQueue<XmppPacket>();
			chatManager.addIncomingListener(new IncomingXMPPMessageListner(packetqueue));
    		chatManager.addOutgoingListener(new OutGoingMessageLIstener(context));
			context.getCurrentVirtualUser().put("receviequeue",packetqueue);

			try {
				EntityBareJid dest= JidCreate.entityBareFrom(ChatWith);
				Chat chat=chatManager.chatWith(dest);
				chat.send(Message);
			} catch (XmppStringprepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			
			appendLineToStringBuilder(requestBuilder, "XMPPSend request.");
			appendLineToStringBuilder(responseBuilder, "XMPPSend response.");
			// TODO perform execution.

			sampleResult.sampleEnd();

	
		}
		sampleResult.setRequestContent(requestBuilder.toString());
		sampleResult.setResponseContent(responseBuilder.toString());

		return sampleResult;
	}

	private void appendLineToStringBuilder(final StringBuilder sb, final String line){
		sb.append(line).append("\n");
	}

	/**
	 * This method allows to easily create an error result and log exception.
	 */
	private static SampleResult getErrorResult(final Context context, final SampleResult result, final String errorMessage, final Exception exception) {
		result.setError(true);
		result.setStatusCode("NL-XMPPSend_ERROR");
		result.setResponseContent(errorMessage);
		if(exception != null){
			context.getLogger().error(errorMessage, exception);
		} else{
			context.getLogger().error(errorMessage);
		}
		return result;
	}

	@Override
	public void stopExecute() {
		// TODO add code executed when the test have to stop.
	}

}
