package com.neotys.xmpp.XmppReceiveMessage;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.AbstractXMPPConnection;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.xmpp.XmppPacket.XmppPacket;

public final class XmppReceiveMessageActionEngine implements ActionEngine {
	public int Timeout;
	public int NumberofMessage;
	LinkedBlockingQueue<XmppPacket> MessageQueue;
	
	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();
		
		String StrTimeout = null;
		String StrNumberofMessage = null;
		
		for(ActionParameter parameter:parameters) {
			switch(parameter.getName()) {
			
			
			case XmppReceiveMessageAction.NumberOfMessage:
				StrNumberofMessage = parameter.getValue();
				break;
			case XmppReceiveMessageAction.TimeOut:
				StrTimeout = parameter.getValue();
				break;
				
			}
		}
		if (Strings.isNullOrEmpty(StrNumberofMessage)) {
			return getErrorResult(context, sampleResult, "Invalid argument: NumberOfmessage cannot be null "
					+ XmppReceiveMessageAction.NumberOfMessage + ".", null);
		}
		else
			NumberofMessage=Integer.parseInt(StrNumberofMessage);
		if (Strings.isNullOrEmpty(StrTimeout)) {
			return getErrorResult(context, sampleResult, "Invalid argument: TimeOut cannot be null "
					+ XmppReceiveMessageAction.TimeOut + ".", null);
		}
		else
			Timeout=Integer.parseInt(StrTimeout);
		
		MessageQueue= (LinkedBlockingQueue<XmppPacket>)context.getCurrentVirtualUser().get("receviequeue");
		sampleResult.sampleStart();
		try {
			if (MessageQueue != null) {
				for (int i = 0; i < NumberofMessage; i++) {
					XmppPacket packet = MessageQueue.poll(Timeout, TimeUnit.SECONDS);
					if (packet != null) {
						appendLineToStringBuilder(responseBuilder, "Message received :");
						appendLineToStringBuilder(responseBuilder, "Content : " + packet.GetMessageConent());
						appendLineToStringBuilder(responseBuilder, "From :" + packet.getDest());

					}
				}
			}
			// TODO perform execution.

			sampleResult.sampleEnd();

			sampleResult.setRequestContent(requestBuilder.toString());
			sampleResult.setResponseContent(responseBuilder.toString());
		}
		catch(Exception e) {
			return getErrorResult(context, sampleResult, "Technical Error: "
					, e);
		}
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
		result.setStatusCode("NL-XmppReceiveMessage_ERROR");
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
