package AI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
// import java.util.Stack;
// import java.util.Queue;


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
    ImageIcon fire = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/fire.png");
    ImageIcon water = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/sea.png");
    ImageIcon sand = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/sand.png");
    ImageIcon wall = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/wall.png");
    ImageIcon finishLine = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/finish-line.png");
    ImageIcon Rsand = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/Rsand.png");
    ImageIcon Rgrass = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/Rgrass.png");
    ImageIcon robot = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/robot.png");
    ImageIcon Rempty = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/Rempty.png");
    ImageIcon RfinishLine = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/RfinishLine.png");
    ImageIcon road = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/intersection.png");
    ImageIcon grass = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/grass.png");
    
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
        colors.add(Color.LIGHT_GRAY);
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
                    System.out.println("frame width"+this.getWidth()/width);
                    cell.setSize(this.getWidth()/width, this.getHeight()/height);
                    cells.add(cell);
                    cordinate = new Cords(i, j);
                    matrixCells.add(cordinate);
                }
        int x=0;
        /*// Let's loop the maze we created previously and make it more like a real maze by adding 
        some obstacles 
        */
        for (int i=0 ;i<width;i++)
            for (int j=0;j<height;j++)
                {
                    
                    /* image and scaled are just object we will store the images in*/
                    Image image;
                    ImageIcon scaled;

                    /*lets add for each cell in the board a background color randomly so each color describes 
                        * the kind of the obstacle as black for walls blue for water and so on ...
                        */
                    Color color = colors.get(rand.nextInt(6));
                    cells.get(x).setBackground(color);
                    color = cells.get(x).getBackground();
                    
                    
                    /* this if statement concern seting for each cell its propre background image 
                    depending on the background colors */
                    if(color == Color.red)
                    {
                        /*SETING THE BG IMAGE PROPERLY  */ 
                        image = fire.getImage();
                        Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                    
                        scaled = new ImageIcon(Simage);
                        cells.get(x).setIcon(scaled);
                    }else 
                        if(color == Color.blue)
                        { 
                            try {
                                image = water.getImage();
                                Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                            
    
                                scaled = new ImageIcon(Simage);
                                cells.get(x).setIcon(scaled);
                            } catch (Exception ex) {
                                // TODO: handle exception
                                System.err.println("water inage could not be loaded ");
                            }
                        }else 
                            if(color == Color.BLACK)
                            { 

                                try {
                                    image = wall.getImage();
                                    Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                                
                                    scaled = new ImageIcon(Simage);
                                    cells.get(x).setIcon(scaled);
                                } catch (Exception ex) {
                                    // TODO: handle exception
                                    System.err.println("wall inage could not be loaded ");
                                }
                            }else 
                                if(color == Color.yellow)
                                { 
                                    try {
                                        image = sand.getImage();
                                        Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                                    
                                        scaled = new ImageIcon(Simage);
                                        cells.get(x).setIcon(scaled);
                                    } catch (Exception ex) {
                                        // TODO: handle exception
                                        System.err.println("sand inage could not be loaded ");
                                    }
                                    
                                }else 
                                if(color == Color.green)
                                { 
    
                                    try {
                                        image = grass.getImage();
                                        Image Simage = image.getScaledInstance(cells.get(x).getWidth(), cells.get(x).getHeight(), Image.SCALE_SMOOTH);
                                    
                                        scaled = new ImageIcon(Simage);
                                        cells.get(x).setIcon(scaled);
                                    } catch (Exception ex) {
                                        // TODO: handle exception
                                        System.err.println("wall inage could not be loaded ");
                                    }
                                }
                        // cells.get(x) = obstacles.get(rand.nextInt(obstacles.size()));
                    gameBoard.add(cells.get(x));
                    x++;
                }
        space.add(gameBoard,BorderLayout.CENTER);
        solve();
        space.setVisible(true);
        
        
        /*end of clicking on DrawBoard 
        
        Button Event */
    
    
    }else if(e.getSource() == StartSolving)
    {
        /* Seting startSolving Button Enable to false so we interupt any try to click
            * it again and 
            */
        StartSolving.setEnabled(false);
        
        /* R holds the the instant of the object Ramnom rand 
        and the cordinate hold a random cell (x,y)
        */
        int R = rand.nextInt(matrixCells.size());
        cordinate = matrixCells.get(R);
        
        // Passing in the first random value in the start point 
        start = cordinate;

        R = rand.nextInt(matrixCells.size());
        cordinate = matrixCells.get(R);
        
        // Passing in the Second random value in the target point 
        goal = cordinate;

        /*if the start and the goal cord are somehow equal 
            * thene loop intell we get a diffrent cell then the start cell
            */
        while(start == goal){
            R = rand.nextInt(matrixCells.size());
            cordinate = matrixCells.get(R);
            //get a Goal Point
            goal = cordinate;
        }
        /* ref is an index var for the matrixcell */
        ref =0;
        /* Image are just needed to scale the image tothe probre size if the label  */
        Image image;
        ImageIcon scaled;
        
        
        /* looping the matrix to get the cells wich are equal
            * to the start and the target cords
            */
        for (int i=0 ;i<width;i++)
            for (int j=0;j<height;j++)
                {
                    if((i == start.getX()) && (j == start.getY()))
                    {

                        try {

                            // an image is used to store the image we want to set as background
                            image = robot.getImage();

                            // the S referse to Scale and by mean we want to hold the scaled image we get from above in image  
                            Image Simage = image.getScaledInstance(cells.get(ref).getWidth(), cells.get(ref).getHeight(), Image.SCALE_SMOOTH);
                            
                            
                            /*scaled is the ImageIcon we will set as a background to the label*/
                            scaled = new ImageIcon(Simage);
                            cells.get(ref).setIcon(scaled);

                            /*and we should remove the backgorund also */
                            cells.get(ref).setBackground(Color.white);

                        } catch (Exception ex) {
                            // TODO: handle exception
                            System.err.println("Image could not be loaded");
                        }
                    
                        }else 
                            if((i == goal.getX()) && (j == goal.getY()))
                                {
                                try {
                                    image = finishLine.getImage();
                                Image Simage = image.getScaledInstance(cells.get(ref).getWidth(), cells.get(ref).getHeight(), Image.SCALE_SMOOTH);
                                
                                scaled = new ImageIcon(Simage);
                                cells.get(ref).setIcon(scaled);
                                cells.get(ref).setBackground(Color.white);
                                } catch (Exception ex) {
                                    // TODO: handle exception
                                    System.err.println("Image could not be loaded");
                                }
                            }
                    
                
                ref ++;
            }

            /*  After seting the cords to the ending and the goal lets try calcuating the distance
            to each cell to the goal
            */
            for (Cords cord : matrixCells)
                cord.setDistance(cord, goal);
            for (Cords cord : matrixCells)
                System.out.println("("+(cord.getX()+1)+","+(cord.getY()+1)+"):"+cord.getdistance());
            
    /*end of Start Solving  */
    }

}
    /* Solving method */
void solve()
{
    // width and height are just meant to store the matrix demonsion
    width = (int)x.getValue();
    height = (int)y.getValue();

    // /* printing the matrix just to comfort it is correct */
    // for (Cords cord : matrixCells)
    //     System.out.print("("+(cord.getX()+1)+","+(cord.getY()+1)+")");
    
    /*Add Start solving to the event if all is clear and values are set */
    StartSolving.setFont(font);
    StartSolving.addActionListener(this);
    cordPanel.add(StartSolving);
}
    public static void main(String argv[]){
        try {
           CreatABoard a = new CreatABoard();
           a.Board();
        } catch (Exception e) {
            System.out.println("Programe could not be runned ");
        }
    }
}