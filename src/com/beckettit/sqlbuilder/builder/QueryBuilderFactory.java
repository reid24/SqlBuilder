/**
 * 
 */
package com.beckettit.sqlbuilder.builder;

import java.util.Map;

import com.beckettit.sqlbuilder.Expression;
import com.beckettit.sqlbuilder.OrderClause;
import com.beckettit.sqlbuilder.Query;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

/**
 * @author reid
 *
 */
public class QueryBuilderFactory extends AbstractFactory {

	/**
	 * @see groovy.util.Factory#newInstance(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object name, Object value, Map map){
		return factoryBuilderSupport; //this is a Query object
	}

	/**
	 * @see groovy.util.AbstractFactory#setChild(groovy.util.FactoryBuilderSupport, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
		super.setChild(builder, parent, child);
		if(child instanceof OrderClause){
			((Query)parent).addOrderClause((OrderClause)child);
		}else if((child instanceof Expression)){
			((Query)parent).addExpression((Expression)child);
		}
	}

}
