/**
 * 
 */
package com.beckettit.sqlbuilder.builder;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import com.beckettit.sqlbuilder.Query;

/**
 * @author reid
 *
 */
public class AliasesBuilderFactory extends AbstractFactory {

	/**
	 * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object name, Object value, Map map){
		return map;
	}
	
	/**
	 * @see groovy.util.AbstractFactory#setParent(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void setParent(FactoryBuilderSupport factoryBuilderSupport, Object parentNode, Object childNode){
		Map map = (Map)childNode;
		Query query = (Query)parentNode;
		for(Object _key : map.keySet()){
			String key = (String)_key;
			query.addAlias(key, (String)map.get(key));
		}
	}

}
