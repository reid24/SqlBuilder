/**
 * 
 */
package com.beckettit.sqlbuilder;

import com.beckettit.sqlbuilder.impl.BaseOrderClauseImpl;

/**
 * @author reid
 *
 */
public class OrderFactory {

	/**
	 * @param property
	 * @return
	 */
	public static final OrderClause asc(String property){
		return new BaseOrderClauseImpl(property, OrderClause.ASC);
	}

	/**
	 * @param property
	 * @return
	 */
	public static final OrderClause desc(String property){
		return new BaseOrderClauseImpl(property, OrderClause.DESC);
	}
}
