/**
 * 
 */
package com.beckettit.sqlbuilder.impl;

import com.beckettit.sqlbuilder.OrderClause;
import com.beckettit.sqlbuilder.Query;

/**
 * @author reid
 *
 */
public class BaseOrderClauseImpl implements OrderClause {
	private String property;
	private String direction;
	
	
	public BaseOrderClauseImpl(String property, String direction) {
		super();
		this.property = property;
		this.direction = direction;
	}

	/**
	 * @see com.beckettit.sqlbuilder.OrderClause#toSql(com.beckettit.sqlbuilder.Query)
	 */
	public String toSql(Query query) {
		return query.getAlias(property) + " " + this.direction;
	}

}
