/*
 * Created on 07.11.2006
 *
 */
package rene.index;

import rene.util.*;
import java.util.*;
import java.io.*;

class FileItem
{	public int n;
	public String filename;
	public String encoding;
	
	public void print (PrintWriter out)
	{	String name=filename.replace('\\','/');
		out.println(name.substring(2)+" "+encoding);
	}
}

class Word
{	public String Word;
	public int N;
	public int Nf[];
	
	public Word (String word)
	{	Nf=new int[4];
		N=0;
		Word=word.toLowerCase();
	}
	
	public boolean note (int nf)
	{	for (int i=0; i<N; i++)
			if (Nf[i]==nf) return false;
		if (N>=Nf.length)
		{	int Nfnew[]=new int[Nf.length*2];
			System.arraycopy(Nf, 0, Nfnew, 0, Nf.length);
			Nf=Nfnew;
		}
		Nf[N++]=nf;
		return true;
	}
	
	public void print (PrintWriter out)
	{	out.print(Word+" ");
		for (int i=0; i<N; i++) out.print(Nf[i]+" ");
		out.println();
	}
}

public class Index 
{	Vector Files;
	Hashtable Words;
	String Dir;
	String Filter="*.html";
	String NegFilter="*_vti_*";
	String IndexName="index.dat";
	
	public Index (String dir)
	{	Dir=dir;
		readIndex();
	}
	
	public void readIndex ()
	{	FileList FL=new FileList(Dir,"*.html",true);
		FL.search();
		Files=new Vector();
		Words=new Hashtable();
		Enumeration e=FL.files();
		int count=1;
		while (e.hasMoreElements())
		{	FileItem fi=new FileItem();
			fi.n=count;
			fi.filename=((File)e.nextElement()).toString();
			fi.encoding="";
			if (FileName.match(fi.filename,NegFilter)) continue;
			Files.add(fi);
			count++;
			indexFile(fi);
		}
		Dir=FL.getDir();
	}
	
	public void indexFile (FileItem fi)
	{	try
		{	BufferedReader in=new BufferedReader(new InputStreamReader(
				new FileInputStream(fi.filename)));
			while (true)
			{	String line=in.readLine();
				if (line==null) break;
				char c[]=line.toCharArray();
				int n=0;
				while (n<c.length)
				{	while (n<c.length && !Character.isLetter(c[n])) n++;
					int start=n;
					while (n<c.length && Character.isLetter(c[n])) n++;
					int end=n;
					if (end<=start+2) continue;
					String word=new String(c,start,end-start);
					if (Words.containsKey(word))
					{	Word w=(Word)Words.get(word);
						w.note(fi.n);
					}
					else
					{	Word w=new Word(word);
						w.note(fi.n);
						Words.put(word,w);
					}
				}
			}
			in.close();
		}
		catch (Exception e)
		{	e.printStackTrace();
		}
	}
	
	public void printFiles (PrintWriter out)
	{	Enumeration e=Files.elements();
		while (e.hasMoreElements())
		{	FileItem fi=(FileItem)e.nextElement();
			fi.print(out);
		}
		out.flush();
	}
	
	public void printWords (PrintWriter out)
	{	Enumeration e=Words.elements();
		while (e.hasMoreElements())
		{	Word w=(Word)e.nextElement();
			w.print(out);
		}
	}
	
	public void createIndex ()
	{	try
		{	PrintWriter out=new PrintWriter(
				new OutputStreamWriter(
						new FileOutputStream(IndexName),"utf-8"));
			printFiles(out);
			out.println();
			printWords(out);
			out.close();
		}
		catch (Exception e)
		{	e.printStackTrace();
		}
	}

	public static void main (String args[])
	{	Index ind=new Index(".");
		//PrintWriter out=new PrintWriter(System.out);
		//ind.printFiles(out);
		//ind.printWords(out);
		ind.createIndex();
		System.out.println("Finished");
	}

}
