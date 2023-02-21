package AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class CreatABoard extends JFrame implements ActionListener{
    static int height,width;
    JPanel space = new JPanel(new BorderLayout());
    JPanel gameBoard ;
    JButton DrawBoard = new JButton("Confirme Cords");
    /* JPanel is the matrix of our Game */
    ArrayList<JPanel> cells = new ArrayList<JPanel>();
    JPanel cell;
    // x and y store the JSpinner Object values to needed use
    JSpinner x = new JSpinner();
    JSpinner y = new JSpinner();
    /* colors array list  */
    ArrayList<Color> colors = new ArrayList<Color>();
    /*Random value to chose from the colors list */
    Random rand = new Random(4);
    
    // let's cret a board first we need a method for that
    void Board() {
        setTitle("Robot");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        x.setBounds(30, 5, 45, 10);
        y.setBounds(110, 5, 45, 10);
        space.add(x,BorderLayout.NORTH);
        space.add(y,BorderLayout.LINE_END);
        space.add(DrawBoard);
        colors.add(Color.red);
        colors.add(Color.black);
        colors.add(Color.white);
        colors.add(Color.blue);
        colors.add(Color.yellow);
        colors.add(Color.green);
        
        setContentPane(space);
        DrawBoard.addActionListener(this);
        
    setVisible(true);
    }
    
    void confirmCords(int width,int height){
        x.setVisible(false);
        y.setVisible(false);
        gameBoard = new JPanel(new GridLayout(width,height));
        for (int i=0 ;i<3;i++)
            for (int j=0; j<3;j++){
                cell = new JPanel();
                cells.add(cell);    
            }
    int x=0;
    for (int i=0 ;i<3;i++)
        for (int j=0;j<3;j++)
            {
                gameBoard.add(cells.get(x));
                x++;
            }
    space.add(gameBoard,BorderLayout.CENTER);
    setVisible(true);
    }

    public void actionPerformed(ActionEvent e){  
        width = (int)x.getValue();
        height = (int)y.getValue();
        x.setVisible(false);
        y.setVisible(false);
        DrawBoard.setVisible(false);
        gameBoard = new JPanel(new GridLayout(width,height));
        for (int i=0 ;i<width;i++)
            for (int j=0; j<height;j++)
            {
                cell = new JPanel();
                cells.add(cell);    
            }
    int x=0;
    for (int i=0 ;i<width;i++)
        for (int j=0;j<height;j++)
            {
                Color color = colors.get(rand.nextInt(4));
                cells.get(x).setBackground(color);
                gameBoard.add(cells.get(x));
                x++;
            }
    space.add(gameBoard,BorderLayout.CENTER);
    solve();
    space.setVisible(true);
}  
    void solve(){
        int width = (int)x.getValue();
        int height = (int)y.getValue();
        System.out.println("("+width+","+height+")");
    }
    public static void main(String argv[]){
        try {
           CreatABoard a = new CreatABoard();
           a.Board();
        } catch (Exception e) {
            System.out.println("no file");
        }
    }
}
