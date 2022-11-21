package lab13;

abstract class Media {
    private int Size;
    private String location;

    public abstract int getSize();
    public abstract void setSize(int a);
    public abstract String getLocation();
    public abstract void setLocation(String a);
    public abstract String getType();
}
