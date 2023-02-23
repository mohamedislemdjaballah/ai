package AI;

public class Action {
    int x;
    int y;
    String move;
    
       
    Cords Up(Cords e){
        return new Cords(e.getX()-1,e.getY());
    }
    Cords Down(Cords e){
        return new Cords(e.getX()+1,e.getY());
    }
    Cords Left(Cords e){
        return new Cords(e.getX(),e.getY()-1);
    }
    Cords Right(Cords e){
        return new Cords(e.getX(),e.getY()+1);
    }
}
