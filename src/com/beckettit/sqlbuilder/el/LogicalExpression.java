/**
 * 
 */
package com.beckettit.sqlbuilder.el;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beckettit.sqlbuilder.Expression;
import com.beckettit.sqlbuilder.el.Operators.Operator;
import com.beckettit.sqlbuilder.Query;

/**
 * @author reid
 *
 */
public class LogicalExpression implements Expression {
	private List<Expression> expressions;
	private Operator operator;
	
	public LogicalExpression(Operator operator) {
		super();
		this.operator = operator;
		this.expressions = new ArrayList<Expression>();
	}

	public LogicalExpression(Operator operator, Expression expression1, Expression expression2) {
		super();
		this.operator = operator;
		this.expressions = new ArrayList<Expression>();
		this.expressions.add(expression1);
		this.expressions.add(expression2);
	}
	
	public LogicalExpression(Operator operator, List<Expression> expressions) {
		super();
		this.operator = operator;
		this.expressions = expressions;
	}

	public void addExpression(Expression expression){
		this.expressions.add(expression);
	}

	/**
	 * @see com.beckettit.sqlbuilder.Expression#getParameters()
	 */
	public Collection<Object> getParameters() {
		ArrayList<Object> parameters = new ArrayList<Object>();
		for(Expression e : expressions){
			parameters.addAll(e.getParameters());
		}
		return parameters;
	}

	/**
	 * @see com.beckettit.sqlbuilder.Expression#toSql(com.beckettit.sqlbuilderg.Query)
	 */
	public String toSql(Query query) {
		StringBuilder sql = new StringBuilder();
		if(this.expressions.size() > 1) sql.append("(");
		ArrayList<String> expressionsSql = new ArrayList<String>();
		for(Expression e : expressions){
			expressionsSql.add(e.toSql(query));
		}
		sql.append(StringUtils.join(expressionsSql, " " + this.operator.toString() + " " ));
		if(this.expressions.size() > 1) sql.append(")");
		return sql.toString();
	}
	
	/**
	 * @see com.beckettit.sqlbuilder.Expression#hasConditionOn(java.lang.String)
	 */
	public boolean hasConditionOn(String property) {
		boolean has = false;
		for(Expression expression : this.expressions){
			if(expression.hasConditionOn(property)) has = true;
		}
		return has;
	}

}
