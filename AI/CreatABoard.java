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
    JButton StartSolving = new JButton("Start Game");
    
    /* ImagePanel cells holds the matrix cells of our Game */
    ArrayList<JLabel> cells = new ArrayList<JLabel>();
    JLabel cell;

    // x and y store the JSpinner Object values to needed use
    JSpinner x = new JSpinner();
    JSpinner y = new JSpinner();
    
    /* colors array list  */
    ArrayList<Color> colors = new ArrayList<Color>();
    
    /*Random value to chose from the colors list */
    Random rand = new Random(4);
    
    /* Matrix Array and some well needed variables*/
    int ref;
    Cords cordinate,state,parent,start,goal;
    ArrayList<Cords> matrixCells = new ArrayList<Cords>();
    /* Lets Draw some images  */
    ImageIcon fire = new ImageIcon("fire.png");
    ImageIcon water = new ImageIcon("sea.png");
    ImageIcon sand = new ImageIcon("sand.png");
    ImageIcon wall = new ImageIcon("wall.png");
    // Image grass = Toolkit.getDefaultToolkit().createImage("../grass.png");
    //gamePanel.drawImage(background, 0, 0, null);
    /* Array List to Store images  */
    // ArrayList<Image> obstacles = new ArrayList<Image>();
    ArrayList<ImageIcon> obstacles = new ArrayList<ImageIcon>();

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

        /* colors list initialising */
        colors.add(Color.red);
        colors.add(Color.black);
        colors.add(Color.white);
        colors.add(Color.blue);
        colors.add(Color.yellow);
        colors.add(Color.green);
        
        /*image List initialising */
        obstacles.add(fire);
        obstacles.add(wall);
        obstacles.add(sand);
        obstacles.add(water);
        

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
        if(e.getSource()==DrawBoard)
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
                        cell = new JLabel();
                        cell.setOpaque(true);
                        cell.setVerticalAlignment(JLabel.CENTER);
                        cell.setHorizontalAlignment(JLabel.CENTER);
                        cell.setSize(100, 100);
                        cells.add(cell);
                        cordinate = new Cords(i, j);
                        matrixCells.add(cordinate);
                    }
            int x=0;
            for (int i=0 ;i<width;i++)
                for (int j=0;j<height;j++)
                    {
                        Image image;
                        ImageIcon scaled;
                        Color color = colors.get(rand.nextInt(6));
                        cells.get(x).setBackground(color);
                        Color clr = cells.get(x).getBackground();
                        System.out.println("width"+cells.get(x));
                        if(clr == Color.red){ 
                            image = fire.getImage();
                            Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                            scaled = new ImageIcon(Simage);
                            cells.get(x).setIcon(scaled);
                        }else if(clr == Color.blue)
                        { 
                            image = water.getImage();
                            Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                          

                            scaled = new ImageIcon(Simage);
                            cells.get(x).setIcon(scaled);
                        }else if(clr == Color.BLACK)
                        { 
                            image = wall.getImage();
                            Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                           
                            scaled = new ImageIcon(Simage);
                            cells.get(x).setIcon(scaled);
                        }else if(clr == Color.yellow)
                        { 
                            image = sand.getImage();
                            Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                            
                            scaled = new ImageIcon(Simage);
                            cells.get(x).setIcon(scaled);
                        }
                        // cells.get(x) = obstacles.get(rand.nextInt(obstacles.size()));
                        gameBoard.add(cells.get(x));
                        x++;
                    }
            space.add(gameBoard,BorderLayout.CENTER);
            solve();
            space.setVisible(true);
        }else if(e.getSource() == StartSolving)
        {

            int R = rand.nextInt(matrixCells.size());
            cordinate = matrixCells.get(R);
            // get a Starting Point
            start = cordinate;
            R = rand.nextInt(matrixCells.size());
            cordinate = matrixCells.get(R);
            //get a Goal Point
            goal = cordinate;
            while(start == goal){
                R = rand.nextInt(matrixCells.size());
                cordinate = matrixCells.get(R);
                //get a Goal Point
                goal = cordinate;
            }
            ref =0;
            for (int i=0 ;i<width;i++)
                for (int j=0;j<height;j++)
                    {
                        if((i == start.getX()) && (j == start.getY()))
                        {
                             cells.get(ref).setBackground(Color.orange);
                        
                         }else 
                             if((i == goal.getX()) && (j == goal.getY()))
                                 cells.get(ref).setBackground(Color.MAGENTA);
                        
                    
                    ref ++;
                }
        /*end of Start Solving  */
        }

    }
    /* Solving method */
    void solve()
    {
        int width = (int)x.getValue();
        int height = (int)y.getValue();

        /* printing the matrix */
        for (Cords cord : matrixCells)
            System.out.print("("+(cord.getX()+1)+","+(cord.getY()+1)+")");
        StartSolving.setFont(font);
        StartSolving.addActionListener(this);
        cordPanel.add(StartSolving);
        
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
