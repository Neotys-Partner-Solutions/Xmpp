package com.neotys.util.xmpp;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by hrexed on 18/06/18.
 */
public class Base64EncodeAction implements Action {
    private static final String BUNDLE_NAME =  "com.neotys.util.xmpp.bundle";
    private static final String DISPLAY_NAME = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayBase64Name");
    private static final String DISPLAY_PATH = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault()).getString("displayPath");
    public static final String Content="Content";
    @Override
    public String getType() {
        return "Base64Encode";
    }

    @Override
    public List<ActionParameter> getDefaultActionParameters() {
        final List<ActionParameter> parameters = new ArrayList<ActionParameter>();
        // TODO Add default parameters.
        parameters.add(new ActionParameter(Content,"Content to encode"));

        return parameters;
    }

    @Override
    public Class<? extends ActionEngine> getEngineClass() {
        return Base64EncodeActionEngine.class;
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
        description.append("Base64EncodeAction will Encode in base64 the Value Content.\n");
        description.append("\tContent: Content to Encode in Base 64\n");

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
