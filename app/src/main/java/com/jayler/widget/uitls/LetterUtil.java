package com.jayler.widget.uitls;

import android.text.TextUtils;

import java.util.Locale;

public class LetterUtil {

    /**
     * 名字转拼音,取首字母
     */
    public static String getSortLetter(String sortName) {
        String letter = "#";
        if (TextUtils.isEmpty(sortName)) {
            return letter;
        }
        String namePingyin = CharacterParser.getInstance().getSelling(sortName);
        String sortString = namePingyin.substring(0, 1).toUpperCase(Locale.CHINESE);
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }

}
