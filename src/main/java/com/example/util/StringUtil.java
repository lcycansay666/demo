package com.example.util;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 *
 * @author yq
 * @date 2018/4/26
 */
public class StringUtil {

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     *
     * @param str 下划线字符串
     * @return 驼峰字符串
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param str 驼峰字符串
     * @return 下划线字符串
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine(String)})
     *
     * @param str 驼峰字符串
     * @return 下划线字符串
     */
    public static String humpToLine2(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }


    /**
     * object转String
     *
     * @param object object
     * @return String
     */
    public static String getString(Object object) {
        return getString(object, "");
    }

    /**
     * object转String，提供默认值
     *
     * @param object       object
     * @param defaultValue 默认值
     * @return String
     */
    public static String getString(Object object, String defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return object.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转int
     *
     * @param object object
     * @return int
     */
    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    /**
     * object转int，提供默认值
     *
     * @param object       object
     * @param defaultValue 默认值
     * @return int
     */
    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Boolean
     *
     * @param object object
     * @return boolean
     */
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    /**
     * object转boolean，提供默认值
     *
     * @param object       object
     * @param defaultValue 默认值
     * @return boolean
     */
    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if ("null".equals(str)) {
            return true;
        }
        return "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        if (str != null && str.length() != 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取随机四位验证码
     */
    public static String getRandomCode() {
        return (int) ((Math.random() * 9 + 1) * 1000) + "";

    }


    public static List<Long> stringToLongList(String ids) {
        String[] split = ids.split(",");
        List<Long> list = new ArrayList<>();
        for (String string : split) {
            list.add(Long.parseLong(string));
        }
        return list;
    }


    public static List<String> stringToStringList(String ids) {
        String[] split = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String string : split) {
            list.add(string);
        }
        return list;
    }

    public static List<Integer> stringToIntegerList(String ids) {
        if (StringUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        String[] split = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String string : split) {
            list.add(Integer.parseInt(string));
        }
        return list;
    }

    public static List<Double> stringToDoubleList(String ids) {
        String[] split = ids.split(",");
        List<Double> list = new ArrayList<>();
        for (String string : split) {
            list.add(Double.parseDouble(string));
        }
        return list;
    }




    public String splitComma(String source) {
        if (StringUtil.isNotEmpty(source)) {
            String regex = "^,*|,*$";
            String result = source.replaceAll(regex, "");
            return result;
        } else {
            return "";
        }
    }

    /**
     * 图片原图改为小图
     *
     * @param imgs
     * @return
     */
    public static String imgsToZoomImg(String imgs) {
        List<String> list = StringUtil.stringToStringList(imgs);
        String newImg = "";
        if (list.size() > 0) {
            for (String s : list) {
                String replace = s.replace(".", "zoom.");
                newImg = newImg + replace + ",";
            }
            return newImg.substring(0, newImg.length() - 1);
        } else {
            return newImg;
        }
    }




    public static String listToString(List list) {
        String res = "";
        if (list == null) {
            return "";
        }
        for (int i = 0; i < list.size(); i++) {
            res = res + list.get(i);
            if(i < list.size()-1){
                res= res +",";
            }
        }
        return res;
    }

    /**
     * 将阿拉伯数字转换为中文(12以内)
     *
     * @param number
     * @return
     */
    public static String convertNumber(Integer number) {
        switch (number) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            case 10:
                return "十";
            case 11:
                return "十一";
            case 12:
                return "十二";
            default:
                return "未知";
        }
    }

}
