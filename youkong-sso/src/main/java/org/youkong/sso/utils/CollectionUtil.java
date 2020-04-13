package org.youkong.sso.utils;

import java.util.Iterator;
import java.util.Map;

public class CollectionUtil {
	public static String getMapValue(Map<String,Object> map,String key){
        String result = null;
        if(map != null){
            Iterator<String> iterable = map.keySet().iterator();
            while (iterable.hasNext()){
                Object object = iterable.next();
                if(key.equals(object))
                    if(map.get(object) != null)
                        result = map.get(object).toString();
            }
        }
 
        return result;
    }
}
