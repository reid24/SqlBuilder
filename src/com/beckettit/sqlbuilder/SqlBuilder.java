/**
 * 
 */
package com.beckettit.sqlbuilder;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.beckettit.sqlbuilder.impl.BaseQueryImpl;
import com.beckettit.sqlbuilder.Query;

/**
 * @author reid
 *
 */
public class SqlBuilder {
	@SuppressWarnings("unchecked")
	private static Map queryRegistry;
	public static final String GROOVY_CONFIG_PROPERTY = "sqlbuilder.groovy.config";
	public static final String DEFAULT_QUERY_CONFIG_SCRIPT = "src/java/samples/queries.groovy";
	
	public static void clear(){
		queryRegistry = null;
	}
	
	@SuppressWarnings("unchecked")
	private static void init(){
		if(queryRegistry == null){
			queryRegistry = new HashMap();
			
			try {
				GroovyScriptEngine gse = new GroovyScriptEngine(new String[]{"."}, SqlBuilder.class.getClassLoader());
				Binding binding = new Binding();
				GroovyShell shell = new GroovyShell(binding);
				String configLocations = System.getProperty("sqlbuilder.groovy.config");
				if(configLocations != null){
					String[] configs = configLocations.split(",");
					for(String config : configs){
						System.out.println("Loading SqlBuilder configuration from '" + config + "'");
						Script script = null;
						if(config.endsWith(".groovy")){
							script = shell.parse(new File(config));
						}else{
							script = (Script)Class.forName(config).newInstance();
						}
						script.run();
						for(Object _key : script.getBinding().getVariables().keySet()){
							String key = (String)_key;
							queryRegistry.put(key, script.getBinding().getVariable(key));
						}
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param baseSql
	 * @return
	 */
	public static Query query(String baseSql){
		init();
		Query query = new BaseQueryImpl(baseSql);
		return query;
	}
	
	public static Query query(Closure closure){
		init();
		Query query = new BaseQueryImpl(closure);
		return query;
	}

	public static Query namedQuery(String queryName){
		init();
		if(!queryRegistry.containsKey(queryName)) throw new RuntimeException("Query " + queryName + " is not registered");
		Object baseSql = queryRegistry.get(queryName);
		if(baseSql instanceof String){
			return new BaseQueryImpl((String)baseSql);
		}else{
			return new BaseQueryImpl((Closure)baseSql);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void registerQuery(String key, String baseSql){
		init();
		queryRegistry.put(key, baseSql);
	}
	
	@SuppressWarnings("unchecked")
	public static void registerQuery(String key, Closure closure){
		init();
		queryRegistry.put(key, closure);
	}
}
