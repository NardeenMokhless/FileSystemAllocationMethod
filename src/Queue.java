public class Queue {

    public int f, n;
    public Directory[] elems = new Directory[1000];

    public Queue() {
        f=0;
        n=0;
        elems=new Directory[1000];
    }

    public Queue(int size)
    {
        f=0;
        n=0;
        elems=new Directory[size];
    }
    public void Destroy()
    {
        f=0;
        n=0;
    }
    public void Add(Directory t)
    {
        elems[n] = t;
        n++;
    }
    public  Directory Pop()
    {
        Directory t=elems[f];
        f++;
        return t;
    }
    public Directory Peek()
    {
        return elems[f];
    }
    public  int Num()
    {
        return (n-f);
    }

}
