import java.util.ArrayList;
import java.awt.Color;

public class Cromo {
    public void setOrder(int order) {
    this.order = order;
}
    public void setColor(Color color) {
    this.color = color;
}
    public void setFitness(Double fitness) {
    this.fitness = fitness;
}
    public int getOrder() {
    return this.order;
}
    public Double getFitness() {
    return this.fitness;
}
    public Color getColor() {
    return this.color;
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
