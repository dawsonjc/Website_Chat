package com.brewery.web.stringutils;

public class StringUtils {
    public String escapeSpecialCharacters(String input) {
        // List of special characters to escape
        String specialCharacters = "\\*+?|{}[]()^$.!<>";

        StringBuilder escapedString = new StringBuilder();

        // Loop through each character in the input string
        for (char c : input.toCharArray()) {
            // If the character is a special character, prepend a backslash
            if (specialCharacters.contains(String.valueOf(c))) {
                escapedString.append("\\");
            }
            escapedString.append(c);
        }

        return escapedString.toString();
    }

    public static String tickAndEscape(String s) {
        if(s == null) {
            return "null";
        }

        String s1 = escapeChars("'", "''", s);
        s1 = escapeChars("\\", "\\\\", s1);

        return s1;
    }

    public static String escapeChars(String escape, String escapeWith, String input) {
        if(input == null) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();

        TokenJumper tj = new TokenJumper(input, escape);

        while(tj.hasMoreTokens()) {
            buffer.append(tj.nextToken());
            if(tj.remainingTokens() > 0) {
                buffer.append(escapeWith);
            }
        }

        return buffer.toString();
    }

    public static String cleanseString(String string) {
        String retString = string.replaceAll("<", "&lt;");
        retString = retString.replaceAll(">", "&gt;");
        retString = retString.replaceAll("\"", "&#34;");
        retString = retString.replaceAll("'", "&#39;");

        return retString;
    }
}
