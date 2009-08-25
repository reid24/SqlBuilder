package com.beckettit.sqlbuilder;


/**
 * @author reid
 *
 */
public interface OrderClause {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    
    /**
     * @return
     * @throws HerculesException 
     */
    public String toSql(Query query);
}
