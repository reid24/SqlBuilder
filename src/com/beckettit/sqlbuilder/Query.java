/**
 * 
 */
package com.beckettit.sqlbuilder;

import java.util.List;

import com.beckettit.sqlbuilder.Expression;


/**
 * @author reid
 *
 */
public interface Query {
	public String toSql();
	public List<Object> getParameters();
//	public Query build(Closure closure); 
	public List<Expression> getExpression();
	public Query addExpression(Expression expression);
	public Query addOrderClause(OrderClause orderClause);
	public Query addAlias(String property, String alias);
	public Query setMaxResults(Integer maxResults);
	public Query setFirstResult(Integer firstResult);
	String getAlias(String property);
	public boolean hasConditionOn(String property);
	public Query clear();
}
