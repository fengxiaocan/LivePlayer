package com.evil.liveplayer.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    /**
     * 将未知类型list转成arraylist
     *
     * @param orig
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> convertListToArrayList(List<T> orig) {
        //如果为null直接返回，这里也可以把size=0加上
        if (null == orig) {
            return null;
        }
        if (orig instanceof ArrayList) {//判断是否就是ArrayList,如果是，则强转
            return (ArrayList)orig;
        } else {
            ArrayList<T> returnValue = new ArrayList<>(orig.size());
            for (T t : orig) {
                returnValue.add(t);
            }
            //jdk1.8及以上可以使用这样的循环遍历
            //orig.forEach(t -> returnValue.add(t));
            return returnValue;
        }
    }

    public static <T> T getLastData(List<T> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }
    public static <T> T getFirstData(List<T> list) {
        if (ObjectUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
