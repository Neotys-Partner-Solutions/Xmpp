package com.neotys.xmpp.XmppReceiveMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public final class XmppReceiveMessageAction implements Action{
	static final String NumberOfMessage="NumberOfMessage";
	static final String TimeOut="TimeOut";
	private static final String BUNDLE_NAME = "com.neotys.xmpp.XmppReceiveMessage.bundle";
	private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
	private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");

	@Override
	public String getType() {
		return "XmppReceiveMessage";
	}

	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		final List<ActionParameter> parameters = new ArrayList<ActionParameter>();
		parameters.add(new ActionParameter("NumberOfMessage","1"));
		parameters.add(new ActionParameter("TimeOut","30"));
		return parameters;
	}

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		return XmppReceiveMessageActionEngine.class;
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
		description.append("XmppReceiveMessage is extracting x message received from the Chat.\n")
		.append("Parameters are:\n")
		.append("- "+NumberOfMessage+": Number of message to extract.\n")
		.append("- "+TimeOut+": Maximum duraction in seconds to listen for messages.\n");

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
