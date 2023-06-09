// Standard Library, contains basic functions like print and whatnot
package exec;

import java.util.*;
import vars.*;

public class StdLib {

	private static Scanner input;
	
	public static void print(ArrayList<Variable> args) {
		String output = "";
		for(Variable v : args) {
			output += v.getString();
		}
		
		System.out.print(output);
	}
	
	public static void read(ArrayList<Variable> args) throws FunctionException {
		for(int i = 0; i < args.size(); i++) {
			Variable v = args.get(i);
			
			if(v instanceof StringVar) {
				StringVar sv = (StringVar)v;
				sv.setString(input.nextLine());
			} else if(v instanceof IntVar) {
				IntVar iv = (IntVar)v;
				iv.setInt(input.nextInt());
			} else {
				throw new FunctionException("The read function only supports strings at the moment.");
			}
		}
	}
	
	public static void execFunc(String func, ArrayList<Variable> args) throws FunctionException {
		if(func.equals("print")) {
			print(args);
		} else if(func.equals("read")) {
			read(args);
		} else {
			throw new FunctionException("No such standard library function " + func);
		}
	}
	
	static {
		input = new Scanner(System.in);
	}
	
}
