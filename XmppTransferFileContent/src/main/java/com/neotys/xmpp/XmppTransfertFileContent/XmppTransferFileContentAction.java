package com.neotys.xmpp.XmppTransfertFileContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public final class XmppTransferFileContentAction implements Action{
	static final String ChatWith="ChatWith";
	static final String ContentToTransfert="ContentToTransfert";
	static final String FileName="FileName";
	static final String Description="Description";
	private static final String BUNDLE_NAME = "com.neotys.xmpp.XmppTransfertFileContent.bundle";
	private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayName");
	private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");

	@Override
	public String getType() {
		return "XmppTransferFileContent";
	}

	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		final List<ActionParameter> parameters = new ArrayList<ActionParameter>();
		// TODO Add default parameters.
		parameters.add(new ActionParameter(ChatWith,"Xmpp adress for the recipient"));
		parameters.add(new ActionParameter(ContentToTransfert,"Content of your message"));
		parameters.add(new ActionParameter(FileName,"Name of the file to transfert"));
		parameters.add(new ActionParameter(Description,"Desciption "));

		return parameters;
	}

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		return XmppTransferFileContentActionEngine.class;
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
		description.append("XmppTransferFileContent will transfert A content a speciif xmpp recipient.\n")
		.append("The parameters are : \n")
		.append("ChatWith : Adress of the recipient \n")
		.append("ContentToTransfert  : Content to transfert \n")
		.append("FileName  :name of the file \n")
		.append("Description  : description \n");
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
