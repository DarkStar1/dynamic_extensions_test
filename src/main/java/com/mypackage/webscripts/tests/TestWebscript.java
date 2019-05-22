package com.mypackage.webscripts.tests;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.Resolution;
import com.mypackage.webscripts.utils.WebScriptUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
@WebScript(baseUri = "/mypackage/api", families = "DE Tests", defaultFormat = "json", value = "Test Webscript")
public class TestWebscript {

    @Uri(value = "/test", method = HttpMethod.GET, defaultFormat = "json")
    public Resolution returnSomething() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("message", "Dynamic extensions test!");
        return WebScriptUtils.jsonResolution(jsonObject);

    }
}
