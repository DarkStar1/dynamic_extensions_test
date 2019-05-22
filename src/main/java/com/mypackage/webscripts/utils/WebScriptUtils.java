package com.mypackage.webscripts.utils;

import com.github.dynamicextensionsalfresco.webscripts.AnnotationWebscriptResponse;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.Resolution;
import org.alfresco.repo.content.MimetypeMap;
import org.json.simple.JSONObject;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.io.OutputStream;

public class WebScriptUtils {

    private static final MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();

    public static final String CONTENT_ENCODING_UTF_8 = "UTF-8";

    private static HttpOutputMessage getHttpOutputMessage(WebScriptResponse res) {
        return new HttpOutputMessage() {
            @Override
            public HttpHeaders getHeaders() {
                return new HttpHeaders();
            }

            @Override
            public OutputStream getBody() throws IOException {
                return res.getOutputStream();
            }
        };
    }

    /**
     * prepares response and executes action
     *
     * @param res
     * @param action
     * @throws IOException
     */
    private static void write(AnnotationWebscriptResponse res, WriteAction action) throws Exception {
        res.setContentType(MimetypeMap.MIMETYPE_JSON);
        res.setContentEncoding(CONTENT_ENCODING_UTF_8);
        res.setHeader("Cache-Control", "no-cache,no-store");
        action.execute();
    }

    private interface WriteAction {
        void execute() throws Exception;
    }


    public static Resolution jsonResolution(org.json.JSONObject o) {
        return (req, res, params) -> write(res, ()
                -> o.write(res.getWriter()));
    }

    /**
     *
     * @param o - {@link JSONObject}, {@link org.json.simple.JSONArray} and etc.
     * @return
     */
    public static Resolution jsonResolution(org.json.simple.JSONStreamAware o) {
        return (req, res, params) -> write(res, ()
                -> o.writeJSONString(res.getWriter()));
    }
}
