package com.neotys.util.xmpp;

import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Created by hrexed on 18/06/18.
 */
public class Base64EncodeAndZlibCompressActionEngine implements ActionEngine {
    private String  Content;
    @Override
    public SampleResult execute(Context context, List<ActionParameter> parameters) {
        final SampleResult sampleResult = new SampleResult();
        final StringBuilder requestBuilder = new StringBuilder();
        final StringBuilder responseBuilder = new StringBuilder();

        for(ActionParameter parameter:parameters) {
            switch(parameter.getName()) {


                case Base64EncodeAction.Content:
                    Content = parameter.getValue();
                    break;

            }
        }
        if (Strings.isNullOrEmpty(Content)) {
            return getErrorResult(context, sampleResult, "Invalid argument: Content cannot be null "
                    + Base64EncodeAction.Content + ".", null);
        }

        try
        {

                sampleResult.sampleStart();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DeflaterOutputStream dos = new DeflaterOutputStream(baos);
                dos.write(  Base64.encodeBase64(Content.getBytes()));
                dos.flush();
                dos.close();

                appendLineToStringBuilder(responseBuilder, baos.toByteArray().toString());
                sampleResult.sampleEnd();



        }
        catch(Exception e)
        {
            return getErrorResult(context, sampleResult, "Technical Error :  "
                    + e.getMessage() + ".", e);

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
        result.setStatusCode("NL-Base64EncodeAndZlib_ERROR");
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
