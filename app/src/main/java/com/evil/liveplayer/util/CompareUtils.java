package com.evil.liveplayer.util;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 25/6/18
 * @desc ...
 */
public final class CompareUtils {
    private CompareUtils() {}

    /**
     * a != b && a!= c && a!= d
     *
     * @param compare
     * @param values
     * @return
     */
    public static<T> boolean noEqualsAnd(T compare,T... values) {
        if (compare == null) {
            if (values == null) {
                return false;
            } else {
                for (T value : values) {
                    if (value == null) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            if (values != null) {
                for (T value : values) {
                    if (compare == value) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }



    /**
     * a != b || a!= c || a!= d
     *
     * @param compare
     * @param values
     * @return
     */
    public static <T> boolean noEqualsOr(T compare,T... values) {
        if (compare == null) {
            if (values == null) {
                return false;
            } else {
                for (T value : values) {
                    if (value != null) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            if (values != null) {
                for (T value : values) {
                    if (compare != value) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        }
    }


    /**
     * a == b || a== c || a== d
     *
     * @param compare
     * @param values
     * @return
     */
    public static <T> boolean equalsOr(T compare,T... values) {
        if (compare == null) {
            if (values == null) {
                return true;
            } else {
                for (T value : values) {
                    if (value == null) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            if (values != null) {
                for (T value : values) {
                    if (compare.equals(value)) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        }
    }
}
