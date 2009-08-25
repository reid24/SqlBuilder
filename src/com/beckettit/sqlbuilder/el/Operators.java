/**
 * 
 */
package com.beckettit.sqlbuilder.el;

/**
 * @author reid
 *
 */
public interface Operators {
	public static enum Operator { 
		EQ {
			@Override
			public String toString() {
				return "=";
			} 
		},
		NE {
			@Override
			public String toString() {
				return "<>";
			} 
		},
		LIKE {
			@Override
			public String toString() {
				return "LIKE";
			} 
		},
		GT {
			@Override
			public String toString() {
				return ">";
			} 
		},
		GTE {
			@Override
			public String toString() {
				return ">=";
			} 
		},
		LT {
			@Override
			public String toString() {
				return "<";
			} 
		},
		LTE {
			@Override
			public String toString() {
				return "<=";
			} 
		},
		IN {
			@Override
			public String toString() {
				return "IN";
			} 
		},
		NOT_IN {
			@Override
			public String toString() {
				return "NOT IN";
			} 
		},
		AND {
			@Override
			public String toString() {
				return "AND";
			} 
		},
		OR {
			@Override
			public String toString() {
				return "OR";
			} 
		};
	}
}
