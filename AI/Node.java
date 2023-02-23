package AI;

public class Node {
    int x;
    int y;
    String move;
    public Node(String action,int x,int y){
        this.move = action;
        this.x = x;
        this.y = y;
    }
    Node Up(int row,int col){
        return new Node("up",row-1,col);
    }
    Node Down(int row,int col){
        return new Node("down",row+1,col);
    }
    Node Left(int row,int col){
        return new Node("left",row,col-1);
    }
    Node Right(int row,int col){
        return new Node("right",row,col+1);
    }
}
