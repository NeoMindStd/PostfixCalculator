package postfixCalculator;

import com.sun.istack.internal.NotNull;

public class DataStack 
{	
	// Constants
	// protected final static char NULL_CHAR = '\0';
	// protected final static int STACK_SIZE = 100;
	
	// Variables
	protected int mTop = -1;
	protected int mStackSize = 0;
	private String[] mStack = null;
	
	public DataStack(int size) 
	{
		this.mStackSize = size;
		mStack = new String[mStackSize];
	}
	
	public void Push(@NotNull String data)
	{
		try {
			if(mTop < mStackSize-1) {	
				mStack[++mTop] = data;
			}
		} catch(Exception e) {
		}
		return;
	}
	
	public String Pop()
	{
		try {
			if(!IsEmpty()) {
				return mStack[mTop--];
			}
		} catch(Exception e) {
		}
		return " ";
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
		}
		return;
	}

	public String Peek()
	{
		try {
			if(!IsEmpty()) {
				return mStack[mTop];
			}
		} catch(Exception e) {
		}
		return " ";
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
