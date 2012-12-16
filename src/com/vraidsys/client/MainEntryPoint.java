/**
 * Build-Id: EMPTY
 */
package com.vraidsys.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.vraidsys.shared.constants.RequestParameterConstants;
import com.vraidsys.shared.helpers.StringExt;

/**
 * @author jzerbe
 */
public class MainEntryPoint implements EntryPoint {

    public static final void displayAlert(final String theAlertText) {
        Window.alert(theAlertText);
    }

    private static final void displayPassedMessage() {
        final String aPassedMessage = MainEntryPoint
                        .getUrlParamValue(RequestParameterConstants.kMessageParamKey);
        if (!StringExt.isEmpty(aPassedMessage)) {
            MainEntryPoint.displayAlert(aPassedMessage);
        }
    }

    public static final String getFullUrl(String theRelativeUrl) {
        if (theRelativeUrl.startsWith("/")) {
            theRelativeUrl = theRelativeUrl.replaceFirst("/", "");
        }

        String aRetUrlStr = GWT.getHostPageBaseURL(); // always ends with slash
        aRetUrlStr += theRelativeUrl;

        if (!GWT.isProdMode()) {
            if (aRetUrlStr.contains("?")) {
                aRetUrlStr += "&gwt.codesvr=127.0.0.1:9997";
            } else {
                aRetUrlStr += "?gwt.codesvr=127.0.0.1:9997";
            }
        }

        return aRetUrlStr;
    }

    private static final String getUrlParamValue(final String theUrlParamKey) {
        return Window.Location.getParameter(theUrlParamKey);
    }

    public static final void loadPageById(final int thePageId) {
        switch (thePageId) {
        // TODO: add domain specific page loader
            default:
                MainEntryPoint.displayAlert("thePageId=" + thePageId
                                + " is not valid");
                break;
        }
    }

    public static final void redirectToRelativeUrl(final String theUrlStr) {
        Window.Location.replace(MainEntryPoint.getFullUrl(theUrlStr));
    }

    private native void initControlJS() /*-{
                                        $wnd.loadPageById = function (thePageId) {
                                        @com.vraidsys.client.MainEntryPoint::loadPageById(I)(thePageId);
                                        };
                                        }-*/;

    @Override
    public void onModuleLoad() {
        this.setPageMeta();

        // TODO: add domain specific page loader

        this.initControlJS();

        MainEntryPoint.displayPassedMessage();
    }

    private void setPageMeta() {
        Window.setTitle("Foo");
    }

}
