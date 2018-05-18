package com.neotys.xmpp.XmppTransferFileContent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.xmpp.XmppPacket.XmppPacket;
import org.jxmpp.util.XmppStringUtils;


public final class XmppTransferFileContentActionEngine implements ActionEngine {
	private String ChatWith;
	private String Message;
	private String FileName;
	private String Description;
	private AbstractXMPPConnection XMPPconnection;
	
	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();

		FileTransferManager filetransfert;

		for(ActionParameter parameter:parameters) {
			switch(parameter.getName()) 
			{
			case  XmppTransferFileContentAction.ChatWith:
				ChatWith = parameter.getValue();
				break;
			case  XmppTransferFileContentAction.ContentToTransfert:
				Message = parameter.getValue();
				break;
			case    XmppTransferFileContentAction.Description:
				Description = parameter.getValue();
				break;
			case XmppTransferFileContentAction.FileName:
				FileName = parameter.getValue();
			break;
				
			}
		}
		
		if (Strings.isNullOrEmpty(ChatWith)) {
			return getErrorResult(context, sampleResult, "Invalid argument: ChatWith cannot be null "
					+ XmppTransferFileContentAction.ChatWith + ".", null);
		}
		if (Strings.isNullOrEmpty(Message)) {
			return getErrorResult(context, sampleResult, "Invalid argument: ContentToTransfert cannot be null "
					+ XmppTransferFileContentAction.ContentToTransfert + ".", null);
		}
		if (Strings.isNullOrEmpty(Description)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Description cannot be null "
					+ XmppTransferFileContentAction.Description + ".", null);
		}
		if (Strings.isNullOrEmpty(FileName)) {
			return getErrorResult(context, sampleResult, "Invalid argument: Filename cannot be null "
					+ XmppTransferFileContentAction.FileName + ".", null);
		}
		XMPPconnection= (AbstractXMPPConnection)context.getCurrentVirtualUser().get("XMPPConnection");
		if(XMPPconnection==null)
			return getErrorResult(context, sampleResult, "There is no connection open with the XMPP server with this VU ",null);
		
		if(XMPPconnection.isConnected())
		{
			sampleResult.sampleStart();

			filetransfert =FileTransferManager.getInstanceFor(XMPPconnection);




			try {
				OutgoingFileTransfer output =filetransfert.createOutgoingFileTransfer(JidCreate.entityFullFrom(ChatWith));
				InputStream data=new ByteArrayInputStream(Message.getBytes());
				output.sendStream(data,FileName,Message.length(),Description);
				while (!output.isDone())
				{
					switch (output.getStatus()) {
						case error:
							return getErrorResult(context, sampleResult, "Filetransfer error: " + output.getError(), null);

						default:
							appendLineToStringBuilder(responseBuilder, "Filetransfer status: " + output.getStatus() + ". Progress: " + output.getProgress());
							break;
					}
					Thread.sleep(1000);
				}
				sampleResult.sampleEnd();

			} catch (XmppStringprepException e) {
				// TODO Auto-generated catch block
				return getErrorResult(context, sampleResult, "TEchnical Error: " + e.getMessage(), e);

			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return getErrorResult(context, sampleResult, "TEchnical Error: " + e.getMessage(), e);
			}

			
			appendLineToStringBuilder(requestBuilder, "XMPPSend request.");
			appendLineToStringBuilder(responseBuilder, "XMPPSend response.");
			// TODO perform execution.



	
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
