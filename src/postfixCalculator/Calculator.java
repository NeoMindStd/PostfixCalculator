package postfixCalculator;

import java.util.Scanner;
import com.sun.istack.internal.NotNull;

/**
 * 
 * @author NeoMind at GNU CS
 * Contact me by sending an e-mail to: dwj0705@gmail.com
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * Github : PostfixCalculator (https://github.com/NeoMindStd/PostfixCalculator)
 * Class separation seems to be necessary, but it was omitted.
 * Because Goal was to implement the task function.
 * This code is messy. So it needs to be cleaned up but omitted for the same reason.
 *
 */

public class Calculator {
	
	// Constants, character type.
	protected final static char NULL_CHAR = '\0';
	protected final static char SEPARATOR = '_';
	protected final static int EXPRESSION_SIZE = 100;
	protected final static int UNDEFINED = -999;
	protected final static int END = 1000;
	protected final static int SPACE = 1001;
		// Values are following priority.
	protected final static int OPERAND = 1004; // 0~9
	protected final static int OPERATOR_PM = 1005; // +, -
	protected final static int OPERATOR_MD = 1006; // *, /
	protected final static int OPERATOR_P = 1007; // ^
	protected final static int RIGHT_BRACKET = 1008; // )
	protected final static int LEFT_BRACKET = 1009; // (
		// Gap of Stack and Expression
	protected final static int GAP = 100;

	protected static int ArraySize(@NotNull char[] array)
	{
		int i;
		for(i = 0; i < array.length; i++) {
			if(array[i] == NULL_CHAR) break;
		}
		return i;
	}
	protected static int ArraySize(@NotNull String[] array)
	{
		int i;
		for(i = 0; i < array.length; i++) {
			if((array[i] == null)) break;
		}
		return i;
	}
	
	protected static void OperatorStack(
			@NotNull DataStack postfix, @NotNull char operator, @NotNull String[] postExp)
	{
		if((!postfix.IsEmpty()) && ((IsWhatType(postfix.Peek()) == LEFT_BRACKET ? 
				LEFT_BRACKET-GAP : IsWhatType(postfix.Peek())) >= IsWhatType(operator))) {
			postExp[ArraySize(postExp)] = postfix.Pop();
			OperatorStack(postfix, operator, postExp);
		}
		else postfix.Push(String.valueOf(operator));
	}
	
	protected static void OperatorStack(
			@NotNull DataStack postfix, @NotNull String operator, @NotNull String[] postExp)
	{
		if((!postfix.IsEmpty()) && ((IsWhatType(postfix.Peek()) == LEFT_BRACKET ? 
				LEFT_BRACKET-GAP : IsWhatType(postfix.Peek())) >= IsWhatType(operator))) {
			postExp[ArraySize(postExp)] = postfix.Pop();
			OperatorStack(postfix, operator, postExp);
		}
		else postfix.Push(operator);
	}
	
	@SuppressWarnings("resource")
	protected static void Getter(@NotNull DataStack postfix, @NotNull String[] postExp) 
	{
		Scanner scanner = new Scanner(System.in);
		char[] originExp = scanner.nextLine().toCharArray();
		char[] transExp = new char[EXPRESSION_SIZE*2];
		
		// String split routine
		// For case which first character is operator.
		transExp[0] = '0';
		int transI = 0;
		for(char oe : originExp) {
			try {
				switch(IsWhatType(oe)) {
				case END :
					break;
				case SPACE :
					continue;
				case OPERAND :
					if(IsWhatType(transExp[transI]) != OPERAND) transExp[++transI] = SEPARATOR;
					break;
				case LEFT_BRACKET :
				case RIGHT_BRACKET :
				case OPERATOR_PM : 
				case OPERATOR_MD : 
				case OPERATOR_P : 
					 transExp[++transI] = SEPARATOR;
					break;
				case UNDEFINED :
					System.out.println("A undefined character was input.");
					System.exit(1);
				default : 
					System.out.println("An error has been occured at Getter() in Calculator.java");
					System.exit(1);
				}
			} catch(Exception e) {}
			 transExp[++transI] = oe;
		}
		String[] infix = String.valueOf(transExp).trim().split("_");
		
		for(String ie : infix) {
			try {
				switch(IsWhatType(ie)) {
				case END :
					//i = infix.length;
					break;
				case SPACE :
					continue;
				case OPERAND :
					postExp[ArraySize(postExp)] = ie;
					break;
				case OPERATOR_PM : 
				case OPERATOR_MD : 
				case OPERATOR_P : 
				case LEFT_BRACKET :
					OperatorStack(postfix, ie, postExp); // postfix is called by reference.
					break;
				case RIGHT_BRACKET :
					String s;
					while(true) {
						s = postfix.Pop();
						if(IsWhatType(s) == LEFT_BRACKET) break;
						postExp[ArraySize(postExp)] = s;
					}
					break;
				case UNDEFINED :
					System.out.println("A undefined character was input.");
					System.exit(1);
				default : 
					System.out.println("An error has been occured at Getter() in Calculator.java");
					System.exit(1);
				}
			} catch(Exception e) {}
		}
		while(postfix.GetSize() > 0) {
			postExp[ArraySize(postExp)] = postfix.Pop();
		}
	}
	
	protected static int IsWhatType(char c)
	{
		if ((c  >= '0') && (c <= '9')) {
			return OPERAND;
		} else {
			switch(c) {
			case '.' :
				return OPERAND;
			case '=' : 
				return END;
			case ' ' :
				return SPACE;
			case '(' : 
				return LEFT_BRACKET;
			case ')' : 
				return RIGHT_BRACKET;
			case '+' :
			case '-' :
				return OPERATOR_PM;
			case '*' :
			case '/' :
				return OPERATOR_MD;
			case '^' :
				return OPERATOR_P;
			default :
				return UNDEFINED; 
			}
		}
	}
	protected static int IsWhatType(String s)
	{
		try{
			Double.parseDouble(s);
			return OPERAND;
		} catch(Exception e) {
			switch(s) {
			case "=" : 
				return END;
			case " " :
				return SPACE;
			case "(" : 
				return LEFT_BRACKET;
			case ")" : 
				return RIGHT_BRACKET;
			case "+" :
			case "-" :
				return OPERATOR_PM;
			case "*" :
			case "/" :
				return OPERATOR_MD;
			case "^" :
				return OPERATOR_P;
			default :
				return UNDEFINED; 
			}
		}
	}
	
	protected static double Calculate(String[] postExp)
	{
		DataStack calcStack = new DataStack(EXPRESSION_SIZE);
		
		double or1, or2;
		for(String pe : postExp) {
			if(pe == null) break;
			switch(IsWhatType(pe)) {
			case END :
			case NULL_CHAR :
				break;
			case SPACE :
				continue;
			case LEFT_BRACKET :
				break;
			case RIGHT_BRACKET : 
				break;
			case OPERAND : 
				calcStack.Push(pe);
				break;
			case OPERATOR_PM : 
			case OPERATOR_MD : 
			case OPERATOR_P :
				or2 = Double.parseDouble(String.valueOf(calcStack.Pop()));
				or1 = Double.parseDouble(String.valueOf(calcStack.Pop()));
				switch(pe) {
				case "+" :
					calcStack.Push(Double.toString(or1 + or2));
					break;
				case "*" :
					calcStack.Push(Double.toString(or1 * or2));
					break;
				case "-" :
					calcStack.Push(Double.toString(or1 - or2));
					break;
				case "/" :
					calcStack.Push(Double.toString(or1 / or2));
					break;
				case "^" :
					calcStack.Push(Double.toString(Math.pow(or1, or2)));
					break;
				}
				break;
			case UNDEFINED :
				System.out.println("A undefined character was input.");
				System.exit(1);
			default : 
				System.out.println("An error has been occured at Getter() in Calculator.java");
				System.exit(1);
			}
		}
		return Double.parseDouble(calcStack.Pop());
	}
	
	public static void main(String[] args)
	{
		DataStack postfix = new DataStack(EXPRESSION_SIZE);
		String[] postExp = new String[EXPRESSION_SIZE];
		
		Getter(postfix, postExp);

		System.out.print("\nPostfix Expression : ");
		for(int i = 1; i < postExp.length; i++) {
			if(postExp[i] == null) break;
			System.out.print(postExp[i] + " ");
		}
		
		System.out.println();
		System.out.print("\nResult : ");
		System.out.println(Calculate(postExp));
	}
}
