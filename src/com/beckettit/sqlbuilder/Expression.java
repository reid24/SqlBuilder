/**
 * 
 */
package com.beckettit.sqlbuilder;

import java.util.Collection;

import com.beckettit.sqlbuilder.Query;

/**
 * @author reid
 *
 */
public interface Expression {
	public String toSql(Query query);
	public Collection<Object> getParameters();
	public boolean hasConditionOn(String property);
}
