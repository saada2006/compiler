package vars;

public class StringVar implements Variable {

	String data;
	
	public StringVar(String s) throws VariableException {
		String raw = s.substring(1, s.length() - 1); // remove quotes
		
		data = "";
		for(int i = 0; i < raw.length(); i++) {
			if(raw.charAt(i) != '\\') {
				data += raw.charAt(i);
			} else {
				if(i + 1 == raw.length()) {
					throw new VariableException("Escape sequence needs extra character!");
				}
				char escape = raw.charAt(i + 1);
				
				if(escape == 'n') {
					data += '\n';
				} else {
					throw new VariableException("Unknown escape sequence " + escape);
				}
				i++;
			}
		}
	}
	
	public String getString() {
		return data;
	}
	
	public void setString(String s) {
		data = s;
	}

	@Override
	public boolean equalOp(Variable other) {
		StringVar sv = (StringVar)other;
		return data.equals(sv.getString());
	}

	@Override
	public boolean nequalOp(Variable other) {
		StringVar sv = (StringVar)other;
		return !data.equals(sv.getString());
	}

	@Override
	public boolean gequalOp(Variable other) {
		StringVar sv = (StringVar)other;
		return (data.compareTo(sv.getString()) >= 0);
	}

	@Override
	public boolean lequalOp(Variable other) {
		StringVar sv = (StringVar)other;
		return (data.compareTo(sv.getString()) <= 0);
	}

	@Override
	public boolean greaterOp(Variable other) {
		StringVar sv = (StringVar)other;
		return (data.compareTo(sv.getString()) > 0);
	}

	@Override
	public boolean lessOp(Variable other) {
		StringVar sv = (StringVar)other;
		return (data.compareTo(sv.getString()) < 0);
	}

}
