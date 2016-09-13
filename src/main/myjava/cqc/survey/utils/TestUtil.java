package cqc.survey.utils;

import java.io.IOException;

public class TestUtil
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Process proc;
		try
		{
			proc = Runtime.getRuntime().exec("python  E:\\SQLInterface.py");
			proc.waitFor();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
