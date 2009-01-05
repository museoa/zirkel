/*
 * Created on 08.11.2006
 *
 */
package rene.index;

import java.util.*;
import java.io.*;

public class Search 
{	String IndexName="index.dat";
	Vector Files;
	Hashtable Words;
	
	public Search ()
	{	readIndex();
	}
	
	public void readIndex ()
	{	Files=new Vector();
		Words=new Hashtable();
		try
		{	BufferedReader in=new BufferedReader(
				new InputStreamReader (
				new FileInputStream(IndexName),"utf-8"));
			while (true)
			{	String line=in.readLine();
				if (line==null) return;
				if (line.equals("")) break;
				Files.add(line);
			}
			while (true)
			{	String line=in.readLine();
				if (line==null) return;
				if (line.equals("")) break;
				StringTokenizer t=new StringTokenizer(line);
				if (t.hasMoreElements()) 
				{	Word w=new Word(t.nextToken());
					while (t.hasMoreElements())
					{	try
						{	w.note(Integer.parseInt(t.nextToken()));
						}
						catch (Exception e)
						{	e.printStackTrace();
							break;
						}
					}
					Words.put(w.Word,w);
				}
			}
			in.close();
		}
		catch (Exception e)
		{	e.printStackTrace();
		}
	}
	
	public Vector find (String word)
	{	Vector v=new Vector();
		if (Words.containsKey(word))
		{	Word w=(Word)Words.get(word);
			for (int i=0; i<w.N; i++)
			{	v.add(Files.elementAt(w.Nf[i]-1));
			}
		}
		return v;
	}

	public static void main (String args[])
	{	Search s=new Search();
		Enumeration e=s.find("virtual").elements();
		while (e.hasMoreElements())
		{	System.out.println(e.nextElement());
		}
		System.out.println("Finished");
	}
}
