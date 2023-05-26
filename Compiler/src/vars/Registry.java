package vars;

import java.util.*;

public class Registry {

	private HashMap<String, Variable> registry;
	
	public Registry() {
		registry = new HashMap<String, Variable>();
	};
	
	public boolean hasVar(String s) {
		return registry.containsKey(s);
	}
	
	public void declareVar(String s, Variable v) throws VariableException {
		if(hasVar(s)) {
			throw new VariableException("Variable " + s + " already exists in the registry");
		}
		
		registry.put(s, v);
	}
	
	public Variable retreiveVar(String s) throws VariableException {
		if(!hasVar(s)) {
			throw new VariableException("Variable " + s + " does not exist in the registry");
		}
		
		return registry.get(s);
	}
	
}
