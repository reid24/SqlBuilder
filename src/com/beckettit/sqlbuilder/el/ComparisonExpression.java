package com.beckettit.sqlbuilder.el;

import java.util.ArrayList;
import java.util.Collection;

import com.beckettit.sqlbuilder.Expression;
import com.beckettit.sqlbuilder.Query;
import com.beckettit.sqlbuilder.el.Operators.Operator;

/**
 * @author reid
 *
 */
public class ComparisonExpression implements Expression {

	protected String property;
	protected Operator operator;
	protected Collection<Object> parameters;

	/**
	 * @param property
	 * @param operator
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public ComparisonExpression(String property, Operator operator, Object value) {
		super();
		this.property = property;
		this.operator = operator;
		this.parameters = new ArrayList<Object>();
		if(value instanceof Collection){
			this.parameters.addAll((Collection)value);
		}else{
			this.parameters.add(value);
		}
	}

	/**
	 * @see com.beckettit.sqlbuilder.Expression#getParameters()
	 */
	public Collection<Object> getParameters() {
		return parameters;
	}

	/**
	 * @see com.beckettit.sqlbuilder.Expression#toSql()
	 */
	public String toSql(Query query) {
		return query.getAlias(this.property) + " " + operator.toString() + " ?";
	}

	/**
	 * @see com.beckettit.sqlbuilder.Expression#hasConditionOn(java.lang.String)
	 */
	public boolean hasConditionOn(String property) {
		return this.property != null && this.property.equals(property);
	}

}