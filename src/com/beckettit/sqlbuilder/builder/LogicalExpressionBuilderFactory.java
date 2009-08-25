/**
 * 
 */
package com.beckettit.sqlbuilder.builder;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import com.beckettit.sqlbuilder.Expression;
import com.beckettit.sqlbuilder.ExpressionFactory;
import com.beckettit.sqlbuilder.el.LogicalExpression;

/**
 * @author reid
 *
 */
public class LogicalExpressionBuilderFactory extends AbstractFactory {

	/**
	 * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object name, Object value, Map map){
		if("and".equals(name)){
			return ExpressionFactory.and();
		}else if("or".equals(name)){
			return ExpressionFactory.or();
		}
		return null;
	}

	/**
	 * @see groovy.util.AbstractFactory#setChild(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object)
	 */
	public void setChild(FactoryBuilderSupport factoryBuilderSupport, Object parentNode, Object childNode){
		((LogicalExpression)parentNode).addExpression((Expression)childNode);
	}

}
