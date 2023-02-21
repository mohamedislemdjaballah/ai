package AI;

public class Directions {
    int x;
    int y;
    String move;
    public Directions(String action,int x,int y){
        this.move = action;
        this.x = x;
        this.y = y;
    }
    Directions Up(int row,int col){
        return new Directions("up",row-1,col);
    }
    Directions Down(int row,int col){
        return new Directions("down",row+1,col);
    }
    Directions Left(int row,int col){
        return new Directions("left",row,col-1);
    }
    Directions Right(int row,int col){
        return new Directions("right",row,col+1);
    }
}
