import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws SyntaxErrorException{
		
		String ls=System.lineSeparator();
		//gets user input
		String input=SimpleIO.getString("Please enter the propositional formula to be evaluated." + ls 
										+"Use & for logical and, / for logical or, > for implication, ! for logical not and use bracets.");
		
		//removes space 
		input=removeChar(input, ' ');
		
		//gets truth values of variables from user
		String variables= SimpleIO.getString("Please enter your variable names and corresponding wahrheitswerte." +ls +
										     "Syntax to use: X -> 1, Y -> 0...");
		
		//removes insignificant characters 
		variables=removeChar(variables, ' ');
		variables=removeChar(variables, '-');
		variables=removeChar(variables,'>');
		variables=removeChar(variables,',');
		
		//creates a map to store truth values of variables
		HashMap <Character, Integer> map=new  HashMap <Character, Integer>();
		
		//initialization of map
		for (int i=0; i<variables.length(); i=i+2) {
			if(!map.containsKey(variables.charAt(i))) {
				map.put(variables.charAt(i), Integer.valueOf(variables.charAt(i+1))-48);
			} else {
				throw new SyntaxErrorException("truth value defined two times for one variable");
			}
			
		}
		
		Parser parsedinput=new Parser(input,map);
		System.out.println(parsedinput.getWahrheitswert());
		 
	}
	
	
	/* helper method to remove all occurrences of a specific character from a string
	 * @param s: string to remove the character from
	 * @param c: character to remove from string
	 * @return: string without any occurrence of character c
	 */
	public static String removeChar(String s, char c) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<s.length(); i++) {
			if (s.charAt(i)!=c) {
				res.append(s.charAt(i));
			} 
		}
		return res.toString();	
	}
	
	
}
