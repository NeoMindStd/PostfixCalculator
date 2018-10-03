package calculator;

import com.sun.istack.internal.NotNull;

public class DataStack 
{	
	// Constants
	protected final static char NULL_CHAR = '\0';
	//protected final static int STACK_SIZE = 100;
	
	// Variables
	protected int mTop = -1;
	protected int mStackSize = 0;
	private char[] mStack = null;
	
	public DataStack(int size) 
	{
		this.mStackSize = size;
		mStack = new char[mStackSize];
	}
	
	public void Push(@NotNull char data)
	{
		try {
			if(mTop < mStackSize-1) {	
				mStack[++mTop] = data;
			}
		} catch(Exception e) {
			//e.printStackTrace();
			//System.out.println("Stack is full!");
			//throw e;
		}
		return;
	}
	
	public char Pop()
	{
		try {
			if(!IsEmpty()) {
				return mStack[mTop--];
			}
		} catch(Exception e) {
			//e.printStackTrace();
			//System.out.println("Stack is Empty!");
			//throw e;
		}
		return NULL_CHAR;
	}
	
	public boolean IsEmpty()
	{
		return (mTop == -1);
	}
	
	public void Delete()
	{
		try {
			if(!IsEmpty()) {
				mTop--;
			}
		} catch(Exception e) {
			//e.printStackTrace();
			//System.out.println("Stack is Empty!");
			//throw e;
		}
		return;
	}

	public char Peek()
	{
		try {
			if(!IsEmpty()) {
				return mStack[mTop];
			}
		} catch(Exception e) {
			//e.printStackTrace();
			//System.out.println("Stack is Empty!");
			//throw e;
		}
		return NULL_CHAR;
	}

	public int GetSize()
	{
		return (mTop+1);
	}
	
	public int GetmStackSize()
	{
		return mStackSize;
	}
}
