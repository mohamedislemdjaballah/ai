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
    Action Up(int row,int col){
        return new Action("up",row-1,col);
    }
    Action Down(int row,int col){
        return new Action("down",row+1,col);
    }
    Action Left(int row,int col){
        return new Action("left",row,col-1);
    }
    Action Right(int row,int col){
        return new Action("right",row,col+1);
    }
}
