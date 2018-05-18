package com.neotys.util.LoadXmlFromFile;

import java.util.List;

import org.jivesoftware.smack.AbstractXMPPConnection;

import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;

public final class XmppDisconnectActionEngine implements ActionEngine {
	AbstractXMPPConnection XMPPconnection;
	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();

		XMPPconnection= (AbstractXMPPConnection)context.getCurrentVirtualUser().get("XMPPConnection");
		if(XMPPconnection==null)
			return getErrorResult(context, sampleResult, "There is no connection open with the XMPP server with this VU ",null);
		
		
		sampleResult.sampleStart();
		if(XMPPconnection.isConnected())
		{
			XMPPconnection.disconnect();
		}

		appendLineToStringBuilder(responseBuilder, "Vu Disconnected from XMPP server.");
		// TODO perform execution.

		sampleResult.sampleEnd();

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
		result.setStatusCode("NL-XmppDisconnect_ERROR");
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
