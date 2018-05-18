package com.neotys.xmpp.XMPPConnect;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public final class XMPPConnectAction implements Action{
	private static final String BUNDLE_NAME = "com.neotys.xmpp.XMPPConnect.bundle";
	private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
	private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");
	public static final String Host="Host";
	public static final String Port="Port";
	public static final String Domain="Domain";
	public static final String XmppUserName="XmppUserName";
	public static final String XmppPassword="XmppPassword";
	public static final String XmppRequiresToCreateUser="XmppRequiresToCreateUser";
	@Override
	public String getType() {
		return "XMPPConnect";
	}

	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		final List<ActionParameter> parameters = new ArrayList<ActionParameter>();
		// TODO Add default parameters.
		// TODO Add default parameters.
		parameters.add(new ActionParameter(Host,"Host"));
		parameters.add(new ActionParameter(Port,"Port"));
		parameters.add(new ActionParameter(Domain,"Domain"));
		parameters.add(new ActionParameter(XmppUserName,"XmppUserName"));
		parameters.add(new ActionParameter(XmppPassword,"XmppPassword"));
		parameters.add(new ActionParameter(XmppRequiresToCreateUser,"false"));

		return parameters;
	}

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		return XMPPConnectActionEngine.class;
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
		description.append("XMPPConnect description will create the XMPP session , requires the following parametersn");
		description.append("\tHost : Host of the xmpp server");
		description.append("\tPort : Port of the xmpp server");
		description.append("\tDomain : Optionnal Xmpp Domain ");
		description.append("\tXmppUserName : Optionnal XmppUsername requires to connect");
		description.append("\tXmppPassword : Optionnal XmppPassword requires to connect");
		description.append("\tXmppRequiresToCreateUser : Optionnal if true the custom action will try to create a new user ");
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
		return Optional.absent();
	}

	@Override
	public Optional<String> getMaximumNeoLoadVersion() {
		return Optional.absent();
	}
}
