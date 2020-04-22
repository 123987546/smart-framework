package org.smart4j.framework.util;

import java.util.Map;
import java.util.Set;

public class CollectionUtil {
    public static boolean isNotEmpty(Map<Class<?>, Object> beanMap) {
        return (!beanMap.isEmpty());
    }

    public static boolean isNotEmpty(Set<Class<?>> controllerClassSet) {
        return (!controllerClassSet.isEmpty());
    }
}
