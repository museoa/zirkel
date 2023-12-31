package rene.util.sound;

import java.io.InputStream;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Sound11 implements Sound
{	AudioData Data;
	AudioDataStream A;
	String Name;
	int Length;
	public Sound11 (String name)
	{	setName(name);
		load(name);
	}
	public void setName (String name)
	{	Name=name;
	}
	public String getName ()
	{	return Name;
	}
	public void load (String file)
	{	try
		{	InputStream in = 
				getClass().getResourceAsStream(file);
			load(in);
		}
		catch (Exception e)
		{	System.out.println(e);
			Data=null;
		}
	}
	public void load (InputStream in)
	{	try
		{	AudioStream AS=new AudioStream(in);
			Length=AS.available();
			Data=AS.getData();
		}
		catch (Exception e)
		{	System.out.println(e);
		}
	}
	public void start ()
	{	if (Data!=null) 
		{	try
			{	if (A==null) A=new AudioDataStream(Data);
				else A.reset();
				AudioPlayer.player.start(A);
				Thread.sleep(Length*1000/8000+500);
			}
			catch (Exception e)
			{	System.out.println(e);
			}
		}
	}
	public static void main (String args[])
	{	Sound11 s=new Sound11("/jagoclient/au/message.au");
		s.start();
		s.start();
		try { Thread.sleep(5000); } catch (Exception e) {}
	}
}
