package postfixCalculator;

import java.util.Scanner;

import com.sun.istack.internal.NotNull;

public class Calculator {
	
	// Constants, character type.
	protected final static char NULL_CHAR = '\0';
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
	
	protected static void OperatorStack(
			@NotNull DataStack postfix, @NotNull char operator, @NotNull char[] postExp)
	{
		if((!postfix.IsEmpty()) && ((isWhatType(postfix.Peek()) == LEFT_BRACKET ? 
				LEFT_BRACKET-GAP : isWhatType(postfix.Peek())) >= isWhatType(operator))) {
			postExp[ArraySize(postExp)] = postfix.Pop();
			OperatorStack(postfix, operator, postExp);
		}
		else postfix.Push(operator);
	}
	
	@SuppressWarnings("resource")
	protected static void Getter(@NotNull DataStack postfix, @NotNull char[] postExp) 
	{
		Scanner scanner = new Scanner(System.in);
		char[] infix = scanner.nextLine().toCharArray();
		
		for(int i = 0; i < infix.length; i++) {
			try {
				switch(isWhatType(infix[i])) {
				case END :
					i = infix.length;
					break;
				case SPACE :
					continue;
				case LEFT_BRACKET :
					OperatorStack(postfix, infix[i], postExp); // postfix is called by reference.
					break;
				case RIGHT_BRACKET : 
					break;
				case OPERAND :
					postExp[ArraySize(postExp)] = infix[i];
					break;
				case OPERATOR_PM : 
				case OPERATOR_MD : 
				case OPERATOR_P : 
					OperatorStack(postfix, infix[i], postExp); // postfix is called by reference.
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
	
	protected static int isWhatType(char c)
	{
		if ((c  >= '0') && (c <= '9')) {
			return OPERAND;
		} else {
			switch(c) {
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
	
	public static void main(String[] args)
	{
		DataStack postfix = new DataStack(EXPRESSION_SIZE);
		char[] postExp = new char[EXPRESSION_SIZE];
		Getter(postfix, postExp);

		System.out.print("\nPostfix Expression : ");
		for(int i = 0; i < postExp.length; i++) {
			System.out.print(postExp[i]);
		}
		
		System.out.println();
		System.out.print("\nResult : ");
	}
}
