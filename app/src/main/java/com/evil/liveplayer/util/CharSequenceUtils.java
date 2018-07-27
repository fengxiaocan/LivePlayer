package com.evil.liveplayer.util;

/**
 * @author 风小灿
 * @date 2016-6-25
 */
public class CharSequenceUtils {
    /**
     * 字符串局部对比匹配
     *
     * @param cs
     * @param ignoreCase
     * @param thisStart
     * @param substring
     * @param start
     * @param length
     *
     * @return
     */
    public static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
        if ((cs instanceof String) && (substring instanceof String)) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            char c1 = cs.charAt(index1++);
            char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!(ignoreCase)) {
                return false;
            }

            if ((Character.toUpperCase(c1) != Character.toUpperCase(c2)) &&
                (Character.toLowerCase(c1) != Character.toLowerCase(c2))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 所有字母转为大写
     *
     * @param character 待转字符串
     *
     * @return 大写字符串
     */
    public static CharSequence toUpperCase(CharSequence character) {
        if (character == null || character.equals("")) {
            return character;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Character.toUpperCase(character.charAt(i)));
        }
        return buffer.toString();
    }

    /**
     * 所有字母转为小写
     *
     * @param character 待转字符串
     *
     * @return 小写字符串
     */
    public static CharSequence toLowerCase(CharSequence character) {
        if (character == null || character.equals("")) {
            return character;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Character.toLowerCase(character.charAt(i)));
        }
        return buffer.toString();
    }
}