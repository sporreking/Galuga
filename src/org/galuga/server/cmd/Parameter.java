package org.galuga.server.cmd;

public class Parameter<T> {
	
	public final String NAME;
	
	public final Class<T> TYPE;
	
	public Parameter(String name, Class<T> type) {
		this.NAME = name;
		this.TYPE = type;
	}
}