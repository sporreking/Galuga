package org.galuga.server.cmd;

public class Command {
	
	public final String NAME;
	
	public final Parameter<?>[] PARAMS;
	
	private final Action ACTION;
	
	public Command(Action action, String name, Parameter<?>... params) {
		this.ACTION = action;
		this.NAME = name;
		this.PARAMS = params;
	}
	
	public void fire(String... args) {
		try {
			ACTION.fire(args);
		} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("Syntax: ");
			
			sb.append(NAME);
			
			for(Parameter<?> p : PARAMS)
				sb.append(" <" + p.TYPE.getSimpleName() + ": " + p.NAME + ">");
			
			System.err.println(sb.toString());
		}
	}
}