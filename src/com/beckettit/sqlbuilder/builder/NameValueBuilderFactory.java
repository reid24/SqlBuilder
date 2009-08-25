/**
 * 
 */
package com.beckettit.sqlbuilder.builder;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.beckettit.sqlbuilder.Query;

/**
 * @author reid
 *
 */
public class NameValueBuilderFactory extends AbstractFactory {

	/**
	 * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object name, Object value, Map map){
		Map nvMap = new HashMap();
		nvMap.put("name", name);
		nvMap.put("value", value);
		return nvMap;
	}
	
	/**
	 * @see groovy.util.AbstractFactory#setParent(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void setParent(FactoryBuilderSupport factoryBuilderSupport, Object parentNode, Object childNode){
		Map map = (Map)childNode;
		String name = (String)map.get("name");
		Query query = (Query)parentNode;
		if("maxResults".equals(name)){
			query.setMaxResults((Integer)map.get("value"));
		}else if("firstResult".equals(name)){
			query.setFirstResult((Integer)map.get("value"));
		}
	}

}
