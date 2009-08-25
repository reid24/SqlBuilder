package com.beckettit.sqlbuilder;

import java.util.Collection;
import java.util.List;

import com.beckettit.sqlbuilder.el.ComparisonExpression;
import com.beckettit.sqlbuilder.el.InExpression;
import com.beckettit.sqlbuilder.el.LogicalExpression;
import com.beckettit.sqlbuilder.el.NotInExpression;
import com.beckettit.sqlbuilder.el.Operators.Operator;

/**
 * @author reid
 *
 */
public class ExpressionFactory {
	
	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression eq(String property, Object value){
		return new ComparisonExpression(property, Operator.EQ, value);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression ne(String property, Object value){
		return new ComparisonExpression(property, Operator.NE, value);
	}
	
	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression gt(String property, Object value){
		return new ComparisonExpression(property, Operator.GT, value);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression gte(String property, Object value){
		return new ComparisonExpression(property, Operator.GTE, value);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression lt(String property, Object value){
		return new ComparisonExpression(property, Operator.LT, value);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression lte(String property, Object value){
		return new ComparisonExpression(property, Operator.LTE, value);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression in(String property, Collection<?> values){
		return new InExpression(property, values);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression notIn(String property, Collection<?> values){
		return new NotInExpression(property, values);
	}

	/**
	 * @param property
	 * @param value
	 * @return
	 */
	public static Expression like(String property, Object value){
		return new ComparisonExpression(property, Operator.LIKE, value);
	}
	
	/**
	 * @param e1
	 * @param e2
	 * @return
	 */
	public static Expression and(Expression e1, Expression e2){
		return new LogicalExpression(Operator.AND, e1, e2);
	}

	/**
	 * @return
	 */
	public static Expression and(){
		return new LogicalExpression(Operator.AND);
	}
	
	/**
	 * @return
	 */
	public static Expression and(List<Expression> expressions){
		return new LogicalExpression(Operator.AND, expressions);
	}

	/**
	 * @param e1
	 * @param e2
	 * @return
	 */
	public static Expression or(Expression e1, Expression e2){
		return new LogicalExpression(Operator.OR, e1, e2);
	}

	/**
	 * @return
	 */
	public static Expression or(){
		return new LogicalExpression(Operator.OR);
	}
	
	/**
	 * @param expressions
	 * @return
	 */
	public static Expression or(List<Expression> expressions){
		return new LogicalExpression(Operator.OR, expressions);
	}
}
