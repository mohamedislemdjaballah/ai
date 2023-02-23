package AI;

public class Cords{
    private int x;
    private int y;
    private int distance;
    public Cords(int x,int y){
        this.x= x;
        this.y=y;
    }
    void setDistance(Cords a,Cords b){
        int x1 = a.getX();
        int y1 = a.getY();
        int x2 = b.getX();
        int y2 = b.getY();
        
        
        this.distance = Math.abs((x2-x1))+Math.abs(+(y2-y1)); 
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
    int getdistance(){
        return this.distance;
    }
}