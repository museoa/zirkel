package rene.zirkel.objects;

public interface MoveableObject
{	public void move (double x, double y);
	public boolean startDrag (double x, double y);
	public boolean dragTo (double x, double y);
	public boolean moveable ();
}
