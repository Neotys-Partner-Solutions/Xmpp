package com.neotys.xmpp.XMPPConnect;

import java.util.List;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;

public final class XMPPConnectActionEngine implements ActionEngine {
	private String Host=null;
	private String sPort=null;
	private int port;
	private String Username=null;
	private String Password=null;
	private String CreateUser=null;
	private boolean HasTocreateUser=false;

	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();
		for(ActionParameter parameter:parameters) {
			switch(parameter.getName()) {


				case XMPPConnectAction.Host:
					Host = parameter.getValue();
					break;
				case XMPPConnectAction.Port:
					sPort = parameter.getValue();
					break;
				case XMPPConnectAction.XmppPassword:
					Username = parameter.getValue();
					break;
				case XMPPConnectAction.XmppUserName:
					Password = parameter.getValue();
					break;
				case XMPPConnectAction.XmppRequiresToCreateUser:
					CreateUser = parameter.getValue();
					break;
			}
		}
		if (Strings.isNullOrEmpty(Host)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Host cannot be null "
					+ XMPPConnectAction.Host + ".", null);
		}
		if (Strings.isNullOrEmpty(sPort)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Port cannot be null "
					+ XMPPConnectAction.Port + ".", null);
		}
		else
		{
			try
			{
				port=Integer.parseInt(sPort);
			}
			catch(NumberFormatException e)
			{
				return getErrorResult(context, sampleResult, "Invalid argument: Port cannot needs to be digit "
						+ XMPPConnectAction.Port + ".", null);
			}
		}
		if (Strings.isNullOrEmpty(CreateUser)) {
			HasTocreateUser=false;
		}
		else
		{
			if(CreateUser.equalsIgnoreCase("true"))
				HasTocreateUser=true;
			else
				HasTocreateUser=false;
		}

		if(HasTocreateUser)
		{
			if (Strings.isNullOrEmpty(Username)) {
				return getErrorResult(context, sampleResult, "Invalid argument: XmppUserName cannot be null if  it requires to create the login "
						+ XMPPConnectAction.XmppUserName + ".", null);
			}
			if (Strings.isNullOrEmpty(Password)) {
				return getErrorResult(context, sampleResult, "Invalid argument: Password cannot be null if it requires if it requires to create the login "
						+ XMPPConnectAction.XmppPassword + ".", null);
			}
		}
		try {
			sampleResult.sampleStart();


			XMPPTCPConnectionConfiguration conf = XMPPTCPConnectionConfiguration.builder()
					.setHost(Host)
					.setPort(port)
					.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
					.build();

			AbstractXMPPConnection connection = new XMPPTCPConnection(conf);
			connection.connect();
			if(HasTocreateUser)
			{
				AccountManager accountManager = AccountManager.getInstance(connection);
				if(accountManager.supportsAccountCreation()) {
					Localpart localpart = Localpart.from(Username);
					accountManager.createAccount(localpart, Password);
					connection.login(Username,Password);
				}
			}
			else
			{
				if(Strings.isNullOrEmpty(Username))
				{
					connection.login();
				}
				else
				{
					connection.login(Username,Password);
				}
			}
			sampleResult.sampleEnd();
			appendLineToStringBuilder(responseBuilder, "Vu Connected from XMPP server.");
			// TODO perform execution.

			context.getCurrentVirtualUser().put( "XMPPConnection",connection);

			appendLineToStringBuilder(requestBuilder, "XMPPConnect request.");
			appendLineToStringBuilder(responseBuilder, "XMPPConnect response.");
			// TODO perform execution.


		}
		catch(Exception e)
		{
			return getErrorResult(context, sampleResult, "Technical error" +e.getMessage(),e);

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
		result.setStatusCode("NL-XMPPConnect_ERROR");
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
