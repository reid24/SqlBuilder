/**
 * 
 */
package com.beckettit.sqlbuilder.el;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import com.beckettit.sqlbuilder.Query;
import com.beckettit.sqlbuilder.el.Operators.Operator;

/**
 * @author reid
 *
 */
public class NotInExpression extends ComparisonExpression {
	
	public NotInExpression(String property, Collection<?> values) {
		super(property, Operator.NOT_IN, values);
	}
	
	/**
	 * @see com.beckettit.sqlbuilder.Expression#toSql()
	 */
	public String toSql(Query query) {
		return query.getAlias(this.property) + " " + operator.toString() + " (" + StringUtils.join(CollectionUtils.collect(getParameters(), new Transformer(){

			public Object transform(Object arg0) {
				return "?";
			}
			
		}),",") + ")";
	}

}
