/**
 * 
 */
package com.beckettit.sqlbuilder.impl;

import groovy.lang.Closure;
import groovy.util.FactoryBuilderSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.beckettit.sqlbuilder.Expression;
import com.beckettit.sqlbuilder.ExpressionFactory;
import com.beckettit.sqlbuilder.OrderClause;
import com.beckettit.sqlbuilder.Query;
import com.beckettit.sqlbuilder.builder.AliasesBuilderFactory;
import com.beckettit.sqlbuilder.builder.ExpressionBuilderFactory;
import com.beckettit.sqlbuilder.builder.LogicalExpressionBuilderFactory;
import com.beckettit.sqlbuilder.builder.NameValueBuilderFactory;
import com.beckettit.sqlbuilder.builder.OrderBuilderFactory;
import com.beckettit.sqlbuilder.builder.QueryBuilderFactory;
import com.beckettit.sqlbuilder.el.LogicalExpression;

/**
 * @author reid
 *
 */
public class BaseQueryImpl extends FactoryBuilderSupport implements Query {
	private List<Expression> expressions;
	private List<OrderClause> orderClauses;
	private Map<String, String> aliases;
	private String baseSql;
	private Closure closure;
	private Integer maxResults;
	private Integer firstResult;	
	
	/**
	 * @param baseSql
	 */
	public BaseQueryImpl(String baseSql) {
		super();
		this.baseSql = baseSql;
		this.expressions = new ArrayList<Expression>();
		this.orderClauses = new ArrayList<OrderClause>();
		this.aliases = new HashMap<String, String>();
		registerFactories();
	}
	
	public BaseQueryImpl(Closure closure) {
		super();
		this.closure = closure;
		this.expressions = new ArrayList<Expression>();
		this.orderClauses = new ArrayList<OrderClause>();
		this.aliases = new HashMap<String, String>();
		registerFactories();
	}
	
	private Expression collapseExpressions(){
		ArrayList<Expression> collapsed = new ArrayList<Expression>();
		for(Expression expression : this.expressions){
			collapsed.add(expression);
		}
		return ExpressionFactory.and(collapsed);
	}

	/**
	 * @see com.beckettit.sqlbuilder.Query#getSql()
	 */
	public String getSql() {
		//TODO: support multiple dialects, this code is kind of specific to mysql
		StringBuilder sql;
		if(this.closure != null){
			sql = new StringBuilder(this.closure.call(this).toString());
		}else {
			sql = new StringBuilder(this.baseSql);
		}

		//conditions
		if(this.expressions.size() > 0){
			sql.append(" \nWHERE\n");
		}
		//collapse conditions into a single AND expression
		String conditions = null;
		if(this.expressions.size() > 1){
			conditions = collapseExpressions().toSql(this); 
		}else if (this.expressions.size() == 1){
			conditions = this.expressions.get(0).toSql(this);
		}else conditions = "";
		//trim unnecessary brackets off
		if(conditions.startsWith("(") && conditions.endsWith(")")){
			conditions = conditions.substring(1, conditions.length()-1);
		}
		sql.append(conditions);
		
		//ordering
		if(this.orderClauses.size() > 0){
			sql.append(" \nORDER BY ");
		}
		
		ArrayList<String> orders = new ArrayList<String>();
		for(OrderClause order : this.orderClauses){
			orders.add(order.toSql(this));
		}
		sql.append(StringUtils.join(orders, ", "));

		//paging
		if(this.maxResults != null){
			sql.append(" \n");
			sql.append("LIMIT ");
			if(this.firstResult != null){
				sql.append(this.firstResult).append(", ");
			}
			sql.append(this.maxResults);
		}
		return sql.toString();
	}
	
	/**
	 * @see com.beckettit.sqlbuilder.Query#getCountSql()
	 */
	public String getCountSql() {
		String sql = getSql();
		int from = sql.toLowerCase().indexOf("from");
		int orderBy = sql.toLowerCase().indexOf("order by");
		int limit = sql.toLowerCase().indexOf("limit ");
		if(from >= 0){
			StringBuilder countSql = new StringBuilder("SELECT COUNT(1) FROM");
			if(orderBy > from + 4){
				countSql.append(sql.substring(from + 4, orderBy));
			}else if(limit > from + 4){
				countSql.append(sql.substring(from + 4, limit));
			}
			return countSql.toString();
		}else throw new RuntimeException("Could not parse out count sql from '" + sql + "'");
	}

	/**
	 * @see com.beckettit.sqlbuilder.Query#getParameters()
	 */
	public List<Object> getParameters(){
		ArrayList<Object> parameters = new ArrayList<Object>();
		for(Expression expression : this.expressions){
			parameters.addAll(expression.getParameters());
		}
		return parameters;
	}

	/**
	 * @see com.beckettit.sqlbuilder.Query#addExpression(com.beckettit.sqlbuilder.Expression)
	 */
	public Query addExpression(Expression expression) {
		this.expressions.add(expression);
		return this;
	}
	
	/**
	 * @see com.beckettit.sqlbuilder.Query#addOrderClause(com.beckettit.sqlbuilder.OrderClause)
	 */
	public Query addOrderClause(OrderClause orderClause) {
		this.orderClauses.add(orderClause);
		return this;
	}
	

	public String getAlias(String property) {
		return this.aliases.containsKey(property) ? this.aliases.get(property) : property;
	}


	public Integer getMaxResults() {
		return maxResults;
	}


	public Query setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
		return this;
	}


	public Integer getFirstResult() {
		return firstResult;
	}


	public Query setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	private void registerFactories(){
		registerFactory("build", new QueryBuilderFactory());
		registerFactory("eq", new ExpressionBuilderFactory());
		registerFactory("ne", new ExpressionBuilderFactory());
		registerFactory("gt", new ExpressionBuilderFactory());
		registerFactory("gte", new ExpressionBuilderFactory());
		registerFactory("lt", new ExpressionBuilderFactory());
		registerFactory("lte", new ExpressionBuilderFactory());
		registerFactory("inList", new ExpressionBuilderFactory());
		registerFactory("notInList", new ExpressionBuilderFactory());
		registerFactory("like", new ExpressionBuilderFactory());
		registerFactory("and", new LogicalExpressionBuilderFactory());
		registerFactory("or", new LogicalExpressionBuilderFactory());
		registerFactory("maxResults", new NameValueBuilderFactory());
		registerFactory("firstResult", new NameValueBuilderFactory());
		registerFactory("order", new OrderBuilderFactory());
		registerFactory("aliases", new AliasesBuilderFactory());
	}

	public List<Expression> getExpression() {
		return this.expressions;
	}

	/**
	 * @see com.beckettit.sqlbuilder.Query#hasConditionOn(java.lang.String)
	 */
	public boolean hasConditionOn(String property) {
		boolean has = false;
		for(Expression e : this.expressions){
			if(e.hasConditionOn(property)) has = true;
		}
		return has;
	}
	
	/**
	 * @see com.beckettit.sqlbuilder.Query#clear()
	 */
	public Query clear(){
		this.expressions.clear();
		this.orderClauses.clear();
		return this;
	}

	public Query addAlias(String property, String alias) {
		this.aliases.put(property, alias);
		return this;
	}
}
