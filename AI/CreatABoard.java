package AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Queue;


public class CreatABoard extends JFrame implements ActionListener{
    /* The Hegiht number of row cells and Width number of col cels never Change 
     * So i just declared as static 'consts'
     */
    static int height,width;

    /* Some Colors and Fonts to use */
    Font font = new Font("DIALOG",Font.PLAIN,20);
    Color red = new Color(225, 0, 0);

    /* Space ,cordPanel and gameboard are just panels to help put 
    in compenentsin he frame */

    JPanel space = new JPanel(new BorderLayout());
    JPanel gameBoard ;
    JPanel cordPanel = new JPanel(new FlowLayout());
    
    /* Some needed Labels to Represent some details */
    JLabel cordinatLabel = new JLabel("Give The Cordinates X(col) then Y(row)");
    /*DrawBoard Button */
    JButton DrawBoard = new JButton("Confirme Cords");
    
    /* JPanel cells holds the matrix cells of our Game */
    ArrayList<JPanel> cells = new ArrayList<JPanel>();
    JPanel cell;

    // x and y store the JSpinner Object values to needed use
    JSpinner x = new JSpinner();
    JSpinner y = new JSpinner();
    
    /* colors array list  */
    ArrayList<Color> colors = new ArrayList<Color>();
    /*Random value to chose from the colors list */
    Random rand = new Random(4);
    /* Matrix Array*/
    Cords cordinate;
    ArrayList<Cords> matrixCells = new ArrayList<Cords>();
    
    
    /* This method Creat the initial State of the game 
     * where we hold the x and y cordinates
    */
    void Board() {
        setTitle("Robot");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        x.setSize(getPreferredSize());
        
        /* Adding the components to the space panel */
        cordinatLabel.setFont(font);
        x.setFont(font);
        y.setFont(font);
        
        x.setBounds(30,1,60,20);
        y.setBounds(90,1,40,20);
        cordPanel.add(cordinatLabel );
        cordPanel.add(x);
        cordPanel.add(y);
       
        space.add(cordPanel,BorderLayout.NORTH);
        space.add(DrawBoard);

        /* colors list declaration and initialising */
        colors.add(Color.red);
        colors.add(Color.black);
        colors.add(Color.white);
        colors.add(Color.blue);
        colors.add(Color.yellow);
        colors.add(Color.green);
        
        /* Adding space to the Frame */
        setContentPane(space);
        DrawBoard.addActionListener(this);

        /*Seting Frame visibility to true */
        setVisible(true);
    }
    /*Whene we Click on any event on the Frane this method runs
     *by default to initiate the following commands
     */
    public void actionPerformed(ActionEvent e)
    {  
        /* Width and height takes the value of the 
         * JSpinner 
         */
        if((int)x.getValue() > 0 && (int)y.getValue() > 0)
        {
            width = (int)x.getValue();
            height = (int)y.getValue();
            x.setVisible(false);
            y.setVisible(false);
            DrawBoard.setVisible(false);
            cordinatLabel.setVisible(false);
        }else{
            /* */
            System.err.println("enter a valid and x and y arguments");
            JDialog errour = new JDialog();
            errour.setTitle("Errour");
            JLabel err = new JLabel("NO CORDINATES FOR X AND Y");
            
            err.setFont(font);
            err.setForeground(red);
            errour.add(err);
            errour.pack();
            setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
            errour.setVisible(true);
            

        }

        gameBoard = new JPanel(new GridLayout(width,height));
        for (int i=0 ;i<width;i++)
            for (int j=0; j<height;j++)
            {
                cell = new JPanel();
                cells.add(cell);
                cordinate = new Cords(i, j);
                matrixCells.add(cordinate);
            }
    int x=0;
    for (int i=0 ;i<width;i++)
        for (int j=0;j<height;j++)
            {
                Color color = colors.get(rand.nextInt(6));
                cells.get(x).setBackground(color);
                gameBoard.add(cells.get(x));
                x++;
            }
    space.add(gameBoard,BorderLayout.CENTER);
    solve();
    space.setVisible(true);
    }
    /* Solving method */
    void solve(){
        int width = (int)x.getValue();
        int height = (int)y.getValue();

        /* printing the matrix */
        for (Cords cord : matrixCells)
            System.out.print("("+(cord.getX()+1)+","+(cord.getY()+1)+")");
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
