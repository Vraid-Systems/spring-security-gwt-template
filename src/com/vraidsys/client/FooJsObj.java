/**
 * Build-Id: EMPTY
 */
package com.vraidsys.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;

/**
 * @author jzerbe
 */
public class FooJsObj extends JavaScriptObject {

    public static final native JsArray<FooJsObj> asArrayOfApplicationObj(
                    String theJson) /*-{ if (theJson.indexOf("<") > -1) { return null; } else { return JSON.parse(theJson); } }-*/;

    protected FooJsObj() {
    }

    public final native int getId() /*-{ return this.Id; }-*/;

    public final List<String> getList() {
        final ArrayList<String> aRetList = new ArrayList<String>();

        final JsArrayString aJsArrayString = this.getRawList();
        for (int i = 0; i < aJsArrayString.length(); i++) {
            aRetList.add(aJsArrayString.get(i));
        }

        return aRetList;
    }

    public final native String getName() /*-{ return this.Name; }-*/;

    private final native JsArrayString getRawList() /*-{ return this.List; }-*/;
}
