/**
 * Build-Id: EMPTY
 *
 * string functions implemented not found in standard Java libs
 */
package com.vraidsys.shared.helpers;

/**
 * @author jzerbe
 */
public class StringExt {

    /**
     * turn all the elements in a String array into a single String with the
     * given separator String
     * 
     * @param theArray T[]
     * @param theSeparator String
     * @return String
     */
    public static <T> String implode(final T[] theArray,
                    final String theSeparator) {
        String aRetStr = "";
        for (int i = 0; i < theArray.length; i++) {
            final String aImplodeEle = String.valueOf(theArray[i]);

            if (i == 0) {
                aRetStr += aImplodeEle;
            } else {
                aRetStr += theSeparator + aImplodeEle;
            }
        }
        return aRetStr;
    }

    /**
     * if null || "" then return true
     * 
     * @param theStr String
     * @return boolean
     */
    public static boolean isEmpty(final String theStr) {
        if (theStr == null) {
            return true;
        } else if ("".equals(theStr)) {
            return true;
        }

        return false;
    }

    /**
     * recursively removes all occurrences of theNeedleStr starting from the
     * right side of theHaystackStr. operation stops when theHaystackStr no
     * longer ends with theNeedleStr.
     * 
     * @param theHaystackStr String
     * @param theNeedleStr String
     * @return String
     */
    public static String rstrip(final String theHaystackStr,
                    final String theNeedleStr) {
        if (theHaystackStr.endsWith(theNeedleStr)) {
            StringExt.rstrip(theHaystackStr.substring(0,
                            (theHaystackStr.length() - theNeedleStr.length())),
                            theNeedleStr);
        }
        return theHaystackStr;
    }
}
