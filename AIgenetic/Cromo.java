import java.util.ArrayList;
import java.awt.Color;

public class Cromo {public void setFitness(Double fitness) {
    this.fitness = fitness;
}
    public int getOrder() {
    return order;
}
    public Double getFitness() {
    return fitness;
}
    public Color getColor() {
    return color;
}
    private  Color color;
    private Double fitness ;
    private int order ;
    public Cromo(Color color ,Double fitness ,int order){
        this.color = color ;
        this.fitness = fitness ;
        this.order = order;
    }
    
}
