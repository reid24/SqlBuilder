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

/**
 * @author reid
 *
 */
public class ExpressionBuilderFactory extends AbstractFactory {

	/**
	 * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object name, Object value, Map map){
		if(name instanceof String && value instanceof List){
			String operator = (String)name;
			List values = (List)value;
			if("eq".equals(operator)){
				return ExpressionFactory.eq((String)values.get(0), values.get(1));
			}else if("ne".equals(operator)){
				return ExpressionFactory.ne((String)values.get(0), values.get(1));
			}else if("gt".equals(operator)){
				return ExpressionFactory.gt((String)values.get(0), values.get(1));
			}else if("gte".equals(operator)){
				return ExpressionFactory.gte((String)values.get(0), values.get(1));
			}else if("lt".equals(operator)){
				return ExpressionFactory.lt((String)values.get(0), values.get(1));
			}else if("lte".equals(operator)){
				return ExpressionFactory.lte((String)values.get(0), values.get(1));
			}else if("inList".equals(operator)){
				return ExpressionFactory.in((String)values.get(0), (Collection)values.get(1));
			}else if("notInList".equals(operator)){
				return ExpressionFactory.notIn((String)values.get(0), (Collection)values.get(1));
			}else if("like".equals(operator)){
				return ExpressionFactory.like((String)values.get(0), values.get(1));
			}
		}
		return null;
	}

}
