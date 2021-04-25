import java.util.HashMap;

/* issues:
 * error handling might be poor:
 * does not handle wrong usage of brackets in some scenarios e.g. repeated usage of conjunction and disjunction without brackets
 * when syntax error occurs, returns syntax error but also a truth value afterwards (the truth value of the formula before error is reached)
 * 
 */
public class Parser {

	int length;

	String input;

	int charAt;          		// to save index when going through characters of the input string
	int wahrheitswert; 			// the truth value of the logical formula
	HashMap map; 				// truth values of the variables

	Parser(String input, HashMap map) {
		this.map = map;
		this.length = input.length();
		this.input = input;
		this.charAt = 0;
		try {
			this.wahrheitswert = this.s();
		} catch (SyntaxErrorException see) {
			System.out.println(see.getMessage());
		}

	}

	// @return truth value of the logical formula
	public int getWahrheitswert() {
		return wahrheitswert;
	}

	/*
	 * here i am using a context free grammar ALGr to represent logical formulas 
	 * S -> I->S | I 
	 * I -> F&I | F/I | F 
	 * F -> (S) | !S | Terminals 
	 * Terminals -> variables | 1 | 0 
	 * method to represent S in the context free grammar ALGr
	 * @return truth value of S
	 */
	public int s() throws SyntaxErrorException {
		wahrheitswert = this.i();
		if (charAt < length && input.charAt(charAt) == '>') {
			charAt++;
			return max(1 - wahrheitswert, this.s());
		} 
		return wahrheitswert;

	}

	/* method to represent I in the context free grammar ALGr
	 * @return truth value of I
	 */
	public int i() throws SyntaxErrorException {
		wahrheitswert = this.f();
		if (charAt < length && input.charAt(charAt) == '&') {
			charAt++;
			return min(wahrheitswert, this.i());
		} else if (charAt < length && input.charAt(charAt) == '/') {
			charAt++;
			return max(wahrheitswert, this.i());
		}
		return wahrheitswert;
	}

	/*
	 * method to represent F in ALGr
	 * @return truth value of F
	 */
	public int f() throws SyntaxErrorException {
		if (charAt < length && input.charAt(charAt) == '(') {
			charAt++;
			wahrheitswert = this.s();
			if (charAt < length && input.charAt(charAt) == ')') {
				charAt++;
			} else {
				throw new SyntaxErrorException();
			}
		} else if (charAt < length && input.charAt(charAt) == '!') {
			charAt++;
			return 1 - this.s();
		} else {
			return this.terminals();
		}
		return wahrheitswert;
	}

	/*
	 * method to represent Terminals in ALGr
	 * 
	 * @return truth value of Terminals
	 */
	public int terminals() throws SyntaxErrorException {
		if (charAt < length && input.charAt(charAt) == '0') {
			charAt++;
			return 0;
		} else if (charAt < length && input.charAt(charAt) == '1') {
			charAt++;
			return 1;
		} else if (charAt < length && map.containsKey(input.charAt(charAt))) {
			int pre = charAt;
			charAt++;
			return ((Integer) map.get(input.charAt(pre)));
		} else {
			throw new SyntaxErrorException();
		}
	}

	/*
	 * method to calculate maximum of two integers
	 * 
	 * @param w,s: integers to compare
	 * 
	 * @return maximum
	 */
	private int max(int w, int s) {
		if (w > s)
			return w;
		else
			return s;
	}

	/*
	 * method to calculate minimum of two integers
	 * 
	 * @param w,s: integers to compare
	 * 
	 * @return minimum
	 */
	private int min(int w, int s) {
		if (w < s)
			return w;
		else
			return s;
	}
}
