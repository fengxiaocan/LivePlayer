package com.evil.liveplayer.util;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     time  : 2016/08/16
 *     desc  : 字符串相关工具类
 * </pre>
 */
public final class StringUtils {

    private final static Pattern emailer = Pattern.compile(
            "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length();i < len;++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a,CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0;i < length;i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a,String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * 限制字符串的长度，超过长度的会截取一部分，末尾显示为自定义b字符串
     *
     * @param a 待限制长度字符串a
     * @param b 待替换字符串b
     * @param length 限制长度
     */
    public static String limitString(String a,String b,int length) {
        if (isEmpty(a)) {
            return a;
        }
        if (a.length() > length) {
            String sub = a.substring(0,length - 1) + b;
            return sub;
        }
        return a;
    }

    /**
     * 限制字符串的长度，超过长度的会截取一部分，末尾显示为...
     *
     * @param a 待限制长度字符串a
     * @param length 限制长度
     */
    public static String limitString(String a,int length) {
        return limitString(a,"...",length);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static CharSequence noNull(CharSequence s) {
        return s == null ? "" : s;
    }
    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String noNull(String s) {
        return s == null ? "" : s;
    }

    /**
     * null转为默认的字符串
     *
     * @param str 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static CharSequence noNull(CharSequence str,CharSequence defaultStr) {
        return str == null ? defaultStr : str;
    }


    /**
     * null转为默认的字符串
     *
     * @param str 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String noNull(String str,String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char)(s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 所有字母转为大写
     *
     * @param character 待转字符串
     * @return 大写字符串
     */
    public static CharSequence toUpperCase(CharSequence character) {
        if (character == null || character.equals("")) {
            return character;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0;i < character.length();i++) {
            buffer.append(Character.toUpperCase(character.charAt(i)));
        }
        return buffer.toString();
    }

    /**
     * 所有字母转为小写
     *
     * @param character 待转字符串
     * @return 小写字符串
     */
    public static CharSequence toLowerCase(CharSequence character) {
        if (character == null || character.equals("")) {
            return character;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0;i < character.length();i++) {
            buffer.append(Character.toLowerCase(character.charAt(i)));
        }
        return buffer.toString();
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char)(s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0;i < mid;++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length;i < len;i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char)(chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length;i < len;i++) {
            if (chars[i] == ' ') {
                chars[i] = (char)12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char)(chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0;i < agrs.length;i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    /**
     * 把Stream转换成String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 使用字符缓冲区来拼接字符串
     */
    public static String join(String... s) {
        StringBuffer sb = new StringBuffer();
        for (String s1 : s) {
            sb.append(s1);
        }
        return sb.toString();
    }

    /**
     * 获取流编码
     */
    public static String getTextCode(String filePath) {// 转码
        try {
            File file = new File(filePath);
            return getTextCode(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取流编码
     */
    public static String getTextCode(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            return getTextCode(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取流编码
     */
    public static String getTextCode(InputStream is) {// 转码
        String text = "GBK";
        try {
            BufferedInputStream in = new BufferedInputStream(is);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
            in.reset();
            if (first3bytes[0] == (byte)0xEF &&
                first3bytes[1] == (byte)0xBB &&
                first3bytes[2] == (byte)0xBF)
            {// utf-8

                text = "utf-8";
            } else if (first3bytes[0] == (byte)0xFF && first3bytes[1] == (byte)0xFE) {
                text = "unicode";
            } else if (first3bytes[0] == (byte)0xFE && first3bytes[1] == (byte)0xFF) {
                text = "utf-16be";
            } else if (first3bytes[0] == (byte)0xFF && first3bytes[1] == (byte)0xFF) {
                text = "utf-16le";
            } else {
                text = "GBK";
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 字符串转换unicode
     */
    public static String str2Unicode(String string) {
        if (string == null) {
            return string;
        }
        try {
            StringBuffer unicode = new StringBuffer();
            for (int i = 0;i < string.length();i++) {
                // 取出每一个字符
                char c = string.charAt(i);
                // 转换为unicode
                String hexString = Integer.toHexString(c);
                unicode.append("\\u" + hexString);
            }
            return unicode.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {
        if (unicode == null) {
            return unicode;
        }
        String[] hex = unicode.split("\\\\u");
        if (hex == null) {
            return unicode;
        } else {
            StringBuffer string = new StringBuffer();
            for (int i = 0;i < hex.length;i++) {
                try {
                    // 转换出每一个代码点
                    int data = Integer.parseInt(hex[i],16);
                    // 追加成string
                    string.append((char)data);
                } catch (Exception e) {
                    string.append(hex[i]);
                }

            }
            return string.toString();
        }
    }

    /**
     * 获取url的后缀
     */
    public static String getUrlSuffix(String url) {
        if (isEmpty(url)) {
            return "";
        } else {
            int of = url.lastIndexOf('.');
            String fix = url.substring(of,url.length());
            return fix;
        }
    }

    /**
     * 获取url的中的文件名
     */
    public static String getUrlFileName(String url) {
        if (isEmpty(url)) {
            return "";
        } else {
            int of = url.lastIndexOf('/');
            String fix = url.substring(of + 1,url.length());
            return fix;
        }
    }

    /**
     * 汉字排序
     */
    public static List<String> ChineseCharacterSort(List<String> list) {
        Comparator<Object> com = Collator.getInstance(Locale.CHINA);
        Collections.sort(list,com);
        return list;
    }

    /**
     * 把字符串加密
     *
     * @param code 要加密的信息
     * @param seed 加密的密码
     * @return 还原后的信息
     */
    public static String encode(String code,int seed) {
        // 加密密码
        byte[] bytes = code.getBytes();
        for (int i = 0;i < bytes.length;i++) {
            bytes[i] ^= seed;
        }
        String string = new String(bytes);
        return string;
    }

    /**
     * 解密加密的字符串
     *
     * @param code 要加密的信息
     * @param seed 解密的密码
     * @return 还原后的信息
     */
    public static String decode(String code,int seed) {
        return encode(code,seed);
    }

    /**
     * 返回一个高亮spannable
     *
     * @param content 文本内容
     * @param color 高亮颜色
     * @param start 起始位置
     * @param end 结束位置
     * @return 高亮spannable
     */
    public static CharSequence getHighLightText(String content,int color,int start,int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        start = start >= 0 ? start : 0;
        end = end <= content.length() ? end : content.length();
        SpannableString spannable = new SpannableString(content);
        CharacterStyle span = new ForegroundColorSpan(color);
        spannable.setSpan(span,start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    /**
     * 获取链接样式的字符串，即字符串下面有下划线
     *
     * @param txt 文本
     * @return 返回链接样式的字符串
     */
    public static Spanned getHtmlStyleString(String txt) {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"\"><u><b>").append(txt).append(" </b></u></a>");
        return Html.fromHtml(sb.toString());
    }

    /**
     * 格式化文件大小，不保留末尾的0
     *
     * @param len 大小
     * @return
     */
    public static String formatFileSize(long len) {
        return formatFileSize(len,false);
    }

    /**
     * 格式化文件大小，保留末尾的0，达到长度一致
     *
     * @param len 大小
     * @param keepZero 是否保留小数点
     * @return
     */
    public static String formatFileSize(long len,boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = String.valueOf(len + "B");
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / (float)100) + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = String.valueOf(len * 10 / 1024 / (float)10) + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = String.valueOf(len / 1024) + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = String.valueOf(formatKeepTwoZero.format(len * 100 /
                                                               1024 /
                                                               1024 /
                                                               (float)100)) + "MB";
            } else {
                size = String.valueOf(len * 100 / 1024 / 1024 / (float)100) + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = String.valueOf(formatKeepOneZero.format(len * 10 /
                                                               1024 /
                                                               1024 /
                                                               (float)10)) + "MB";
            } else {
                size = String.valueOf(len * 10 / 1024 / 1024 / (float)10) + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = String.valueOf(len / 1024 / 1024) + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float)100) + "GB";
        }
        return size;
    }

    /**
     * 获取double的后几位小数
     *
     * @param num double值
     * @param decimals 保留几位小数
     * @return 保留后的字符串
     */
    public static String getDoubleAfterDecimals(double num,int decimals) {
        String numStr = String.valueOf(num);
        int last = numStr.lastIndexOf("\\.");
        int i = last + decimals;
        if (i < numStr.length()) {
            numStr.substring(0,i);
        } else if (i == numStr.length()) {
            return numStr;
        } else {
            for (int j = 0;j < i - numStr.length();j++) {
                numStr += "0";
            }
        }
        return numStr;
    }

    /**
     * 格式化音乐时间: 120 000 --> 02:00
     *
     * @param time
     * @return
     */
    public static String formatMusicTime(long time) {
        time = time / 1000;
        String formatTime;
        if (time < 10) {
            formatTime = "00:0" + time;
            return formatTime;
        } else if (time < 60) {
            formatTime = "00:" + time;
            return formatTime;
        } else {
            long i = time / 60;
            if (i < 10) {
                formatTime = "0" + i + ":";
            } else {
                formatTime = i + ":";
            }
            long j = time % 60;
            if (j < 10) {
                formatTime += "0" + j;
            } else {
                formatTime += j;
            }

            return formatTime;
        }
    }

    /**
     * 检测手机号码
     *
     * @param phone
     */
    public static boolean checkoutPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        if (phone.length() < 11) {
            return false;
        }
        if (!phone.startsWith("1")) {
            return false;
        }
        if (phone.startsWith("10") || phone.startsWith("11") || phone.startsWith("12")) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否是邮箱
     *
     * @param email
     */
    public static boolean checkoutEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    /**
     * 检测是否是邮政编码
     *
     * @param postCode
     */
    public static boolean checkIsPostCode(String postCode) {
        if (isEmpty(postCode)) {
            return false;
        }
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg,postCode);
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str,int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(),0);
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static double toDouble(String obj) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
            return 0D;
        }
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBoolean(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断一个字符串是不是数字
     */
    public static boolean isNumber(CharSequence str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * byte[]数组转换为16进制的字符串。
     *
     * @param data 要转换的字节数组。
     * @return 转换后的结果。
     */
    public static String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0;i < len;i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte)((Character.digit(s.charAt(i),16) << 4) +
                              Character.digit(s.charAt(i + 1),16));
        }
        return d;
    }
}