package AI;

public class Action {
    int x;
    int y;
    String move;
    
    public Action(){
    }
    Cords Up(Cords e,int width){
        Cords k = new Cords(e.getX()-1, e.getY(),e.getCost());
        k.setRef(e.getRef()-(width));
        
        k.setCords(e.getX()-1, e.getY());
        return k;
    }
    Cords Down(Cords e,int width){
        Cords k = new Cords(e.getX()+1, e.getY(),e.getCost());
        k.setRef(e.getRef()+(width));
        k.setCords(e.getX()+1, e.getY());
        return k;
    }
    Cords Left(Cords e){
        Cords k = new Cords(e.getX(), e.getY()-1,e.getCost());
        k.setRef(e.getRef()-1);
        k.setCords(e.getX(), e.getY()-1);
        return k;
    }
    Cords Right(Cords e){
        Cords k = new Cords(e.getX(), e.getY()+1,e.getCost());
        k.setRef(e.getRef()+1);
        k.setCords(e.getX(), e.getY()+1);
        return k;
    }
}
