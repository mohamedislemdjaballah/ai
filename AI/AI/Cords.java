package AI;
public class Cords{
    private int x;
    private int y;
    private int distance;
    private int cost;

    private int ref;
    public Cords(int x,int y,int cost){
        this.x= x;
        this.y=y;
        this.cost = cost;
    }
    void setDistance(Cords a,Cords b){
        int x1 = a.getX();
        int y1 = a.getY();
        int x2 = b.getX();
        int y2 = b.getY();
        
        
        this.distance = Math.abs((x2-x1))+Math.abs(+(y2-y1)); 
    }
    public void setRef(int ref) {
        this.ref = ref;
    }
    public int getRef() {
        return ref;
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
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
}