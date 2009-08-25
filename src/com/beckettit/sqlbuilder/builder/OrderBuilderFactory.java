/**
 * 
 */
package com.beckettit.sqlbuilder.builder;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.beckettit.sqlbuilder.ExpressionFactory;
import com.beckettit.sqlbuilder.OrderFactory;

/**
 * @author reid
 *
 */
public class OrderBuilderFactory extends AbstractFactory {

	/**
	 * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object name, Object value, Map map){
		
		if(value instanceof Collection){
			String property = (String) ((List)value).get(0);
			String direction = (String) ((List)value).get(1);
			
			if("asc".equalsIgnoreCase(direction)) 
				return OrderFactory.asc(property);
			else 
				return OrderFactory.desc(property);
		}
		
		return null;
	}

}
