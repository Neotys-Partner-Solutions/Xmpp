package com.neotys.xmpp.XmppTransferFileContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public final class XMPPSendAction implements Action{
	static final String ChatWith="ChatWith";
	static final String Message="Message";
	
	private static final String BUNDLE_NAME = "com.neotys.xmpp.XmppTransferFileContent.bundle";
	private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
	private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");

	@Override
	public String getType() {
		return "XMPPSend";
	}

	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		final List<ActionParameter> parameters = new ArrayList<ActionParameter>();
		// TODO Add default parameters.
		parameters.add(new ActionParameter(ChatWith,"Xmpp adress for the recipient"));
		parameters.add(new ActionParameter(Message,"Content of your message"));
		return parameters;
	}

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		return XMPPSendActionEngine.class;
	}

	@Override
	public Icon getIcon() {
		// TODO Add an icon
		return null;
	}

	@Override
	public String getDescription() {
		final StringBuilder description = new StringBuilder();
		// TODO Add description
		description.append("XMPPSend will send a Message to a speciif xmpp recipient.\n")
		.append("The parameters are : \n")
		.append("ChatWith : Adress of the recipient \n")
		.append("Message  : Content of the message \n");
		return description.toString();
	}

	@Override
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	@Override
	public String getDisplayPath() {
		return DISPLAY_PATH;
	}

	@Override
	public Optional<String> getMinimumNeoLoadVersion() {
		return Optional.of("5.4");
	}

	@Override
	public Optional<String> getMaximumNeoLoadVersion() {
		return Optional.absent();
	}
}
