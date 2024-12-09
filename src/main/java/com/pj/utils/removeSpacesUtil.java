package com.pj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class removeSpacesUtil {

    public static String removeSpaces(String str) {
        // 使用Pattern和Matcher去除所有空白字符，包括Unicode空白字符
        Pattern pattern = Pattern.compile("[\\p{Zs}\\t\\n\\r\\f]");
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

}
