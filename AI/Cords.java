package AI;

public class Cords{
    private int x;
    private int y;
    public Cords(int x,int y){
        this.x= x;
        this.y=y;
    }
    void setCords(int x,int y){
        this.x = x;
        this.y = y;
    }
    int getX(){
        return this.x;
    }
    int getY(){
        return this.y;
    }
}