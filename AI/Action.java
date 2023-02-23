package AI;

public class Action {
    int x;
    int y;
    String move;
    public Action(String action,int x,int y){
        this.move = action;
        this.x = x;
        this.y = y;
    }
    Cords Up(int row,int col){
        Cords e = new Cords((row-1),col);
        return e;
    }
    Cords Down(int row,int col){
        Cords e = new Cords((row+1),col);
        return e;   
    }
    Cords Left(int row,int col){
        Cords e = new Cords(row,col-1);
        return e; 
    }
    Cords Right(int row,int col){
        Cords e = new Cords(row,col+1);
        return e; 
    }
}
