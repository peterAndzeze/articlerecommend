package com.article.recommend.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 子定义hashmap用于json操作
 * @param <key>
 * @param <value>
 */
public class CustomerHashMap<K,V> extends HashMap<K,V> {
    @Override
    public String toString(){
        String toString="{";
        Iterator<K> keyIte=this.keySet().iterator();
        while(keyIte.hasNext()){
            K key=keyIte.next();
            toString+="\""+key+"\":"+this.get(key)+",";
        }
        if(toString.equals("{")){
            toString="{}";
        }
        else{
            toString=toString.substring(0, toString.length()-1)+"}";
        }
        return toString;

    }

    /**
     *
     * @param linkedHashMap
     * @return
     */
    public CustomerHashMap<K,V> copyFromLinkedHashMap(LinkedHashMap<K,V> linkedHashMap){
//		Iterator<K> ite = linkedHashMap.keySet().iterator();
//		while(ite.hasNext()){
//			K key=ite.next();
//			this.put(key,linkedHashMap.get(key));
//		}
        this.putAll(linkedHashMap);
        return this;
    }
}
