package AI;

import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
// import java.util.Stack;
// import java.util.Queue;


public class CreatABoard extends JFrame implements ActionListener{


    /* A frontier is an Array list that holds the stat<Cords> */
    ArrayList<Cords> frontier = new ArrayList<Cords>();

    /*A solutions to add the probre nods to the path  */
    ArrayList<Cords> solution = new ArrayList<Cords>();

    /*An Explored list of Node to add the unprober nodes  */
    ArrayList<Cords> Explored = new ArrayList<Cords>();
    ArrayList<Cords> UnExplored = new ArrayList<Cords>();
    
    /*The action list we can do  */
    String actions[] = {"up","down","right","left"};
    
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
        
        setResizable(true);
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
            ref=0;
            gameBoard = new JPanel(new GridLayout(width,height));
            
            /*Lets's make the cells depending on demonsion giving and set backgrounds equal to each state of label */
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
                    cordinate.setRef(ref);
                    matrixCells.add(cordinate);
                    // System.out.println("("+i+","+j+")---->reference"+cordinate.getRef());
                    ref++;
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
            
            ;
            for(String action : actions)
                System.err.println("List of Action :"+action);

            
            probreMove(start);
            
            

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
boolean nodeExploreded(Cords state,ArrayList<Cords> Explored){
    
    for (Cords e : Explored) {
    if((state.getX() == e.getX())&&(state.getY() == e.getY()))
    return true;
    }
    return false;
}



/* a method that returns all options */
ArrayList<Cords> allOptions(Cords state,ArrayList<Cords> Explored)
{
Action move = new Action();
Cords newState;
Color bgColor;

        for(String action : actions){
            
                switch(action){
                    /* in each move we make sure we are not getting over the height and width of the maze*/
                    case "up" :
                    
                        if(((state.getX()-1)>=0))
                        {
                            
                            newState = move.Up(state,height);
                            bgColor =cells.get(newState.getRef()).getBackground();
                            // System.out.println(bgColor);
                            
                        if(( bgColor != Color.red && bgColor != Color.black)  )
                        if(!nodeExploreded(newState, Explored))
                        frontier.add(newState);
                        }
                    ;
                    break;
                    case "down" : 
                
                    
                        if(((state.getX()+1)<height))
                        {
                            newState = move.Down(state,height);
                            bgColor =cells.get(newState.getRef()).getBackground();
                            // System.out.println("Down newState"+newState.getRef()+" bg"+bgColor+"oldState bgColor :"+cells.get(newState.getRef()).getBackground());
                            if(( bgColor != Color.red && bgColor != Color.black))
                            if(!nodeExploreded(newState, Explored))
                                frontier.add(newState);
                        }
                        ;
                    break;
                    case "left" : 
                    
                        if((state.getY()-1)>=0){
                            newState = move.Left(state);
                            bgColor =cells.get(newState.getRef()).getBackground();

                            System.out.println("newRed and color "+newState.getRef()+""+bgColor+"old State Red"+state.getRef());
                            if( ( bgColor != Color.red && bgColor != Color.black))
                            if(!nodeExploreded(newState, Explored))
                            frontier.add(newState);
                        }
                    break;
                    
                    case "right" : 
                    
                        System.out.println("before right walking");
                        display(frontier);
                        if(((state.getY()+1)<width))
                        {
                            newState = move.Right(state);
                            bgColor =cells.get(newState.getRef()).getBackground();
                            // System.out.println(bgColor);
                            if( ( bgColor != Color.red && bgColor != Color.black))
                            if(!nodeExploreded(newState, Explored))
                                frontier.add(newState);
                        }
                        System.out.println("after walking right ");
                        display(frontier);
                    break;
            }

         }
return frontier;
}



/*Remove All the Explored Nodes from the frontier */
ArrayList<Cords> removeExplored(ArrayList<Cords> Explored){
    for(Cords e : Explored)
        frontier.remove(e);
    return frontier;
}

/*Find the most optimal spot to reach the goal */
ArrayList<Cords> leastCostSpot(ArrayList<Cords> frontier,Cords state){
    for (Cords cord : frontier)
    cord.setDistance(cord, goal);
    int optimal = frontier.get(frontier.size()-1).getdistance();
        solution.add(frontier.get(frontier.size()-1));
        /*Search for the optimal move in the frontier */
        for(Cords d : frontier)
        {
           System.out.println(d.getdistance());
            System.out.println(solution.get(solution.size()-1).getdistance()+"Compare"+d.getdistance());
                
            /*if the new cords has a less distance thene the previous node we remove
             * than it is not the optimal solution so we remove it
             */
            if(solution.get(solution.size()-1).getdistance() > d.getdistance())
            {
                System.out.println(solution.get(solution.size()-1).getdistance()+"is bigger than"+d.getdistance());
                if(solution.size()>=1)
                solution.remove(solution.size()-1);
                // System.out.println("Cords  removed from solution and added tp explored ("+solution.get(solution.size()-1).getX()+","+solution.get(solution.size()-1).getY()+")reference"+solution.get(solution.size()-1).getRef());
                // System.out.println("Solution added to Cards ("+d.getX()+","+d.getY()+")reference"+d.getRef());
                solution.add(d);
                optimal = d.getdistance();
            }
        }
        
    return solution;
}

/*Adding explored nodes to explored list */
ArrayList<Cords> addToExplored(ArrayList<Cords> solution){
    for(Cords e : solution)
        Explored.add(e);
    return Explored;
}
/*Adding unexplored nodes to explored list */
ArrayList<Cords> addToUnExplored(ArrayList<Cords> frontier){
    for(Cords e : frontier)
        UnExplored.add(e);
    return UnExplored;
}
/*Display Lst contents */
void display(ArrayList<Cords> list){
    for(Cords e : list)
        System.out.println("("+e.getX()+""+e.getY()+")"+e.getRef()+"Ref");
}


/* Veridy if we found a solution */
boolean reachedGoal(ArrayList<Cords> sol,Cords goal){
    for (Cords e : sol) {
        if(e.getRef() ==  goal.getRef())    
            return true;
    }
        
    return false;
}
void displayMove(ArrayList<Cords> solution){
for (Cords e : solution) {
    cells.get(e.getRef()).setBackground(Color.green);
}
}
 void paintExplored(ArrayList<Cords> solution){
    for (Cords e : solution) {
        cells.get(e.getRef()).setBackground(Color.orange);
    }
    }
void probreMove(Cords start){

        
        // // System.out.println("start distance ::"+state.getdistance()+"("+state.getX()+","+state.getY()+")");
        // solution.add(start);
        /* luch state at null */
        state = null;
        boolean started = false ;
    int i=0;
        System.out.println(goal.getRef());
        while(true)
        {
        i++;

            /*if the state iquals to null that means we just started thene set it ti start cords */
            if(state == null){
                System.out.println("state == start");
                state = start;

            }else 
            /*if the state is not null that mean we are looping to find the solution lets just set 
             * the state to the last element in the solution 
             */
            if(frontier.isEmpty() && !UnExplored.isEmpty()){
                solution.remove(solution.size()-1);
                state = UnExplored.get(UnExplored.size()-1);
                Explored.add(state);
                UnExplored.remove(state);
                System.out.println("new state from Unexplored ("+state.getX()+","+state.getY()+")");
            }else 
            /*if the frontier is empty and we have some unexplored cells we should check on theme */
            if(!frontier.isEmpty() && UnExplored.isEmpty()){
                state = solution.get(solution.size()-1);
                System.out.println("new state from solution whene unexplored is empty("+state.getX()+","+state.getY()+")");
            }else
            /*if both list are not empty lets just set the state fron the frontier  */
            if(!frontier.isEmpty() && !UnExplored.isEmpty()){
                display(solution);
                state = solution.get(solution.size()-1);
                System.out.println("new state whene frontier and explored bothare not empty("+state.getX()+","+state.getY()+") ref"+state.getRef());
            }else
            /*if both list are not empty lets just set the state fron the frontier  */
            if(frontier.isEmpty() && UnExplored.isEmpty()){
                System.out.println("no solution was found");
                paintExplored(Explored);
                break;

            }
             
            /*if the state is equal to starting point  */
            if(state == start && started == false)
            {
                started = true;
                /*search for all available spots  */
                frontier =allOptions(state,Explored);
                for (Cords E : frontier) {
                    System.out.println("cords ref alloptient worked just fine ::"+E.getRef());
                }
                /*add start point to solutions */
                solution.add(state);
                // System.out.println("added state"+state.getX()+","+state.getY()+" to solution");
                /*find solution and add it to list */
                if(!frontier.isEmpty())
                solution = leastCostSpot(frontier,state);
                for (Cords E : solution) {
                    System.out.println("cords ref least cost spote worked just fine ::"+E.getRef());
                }
                /*add solutions to explored list  */
                if(!solution.isEmpty())
                Explored = addToExplored(solution);
                for (Cords E : Explored) {
                    System.out.println("cords ref Explored worked just fine ::"+E.getRef());
                }
                /*remove explored points from frontier */
                for (Cords E : frontier) {
                    System.out.println("Frontier  just fine ::"+E.getRef());
                }

                if(!Explored.isEmpty())
                frontier = removeExplored(Explored);
                for (Cords E : frontier) {
                    System.out.println("frontier remover explored worked just fine ::"+E.getRef());
                }
                for (Cords E : frontier) {
                    System.out.println("Frontier  after removing explored ::"+E.getRef());
                }
                /*add unexplored spots from frontier to unexplored list */
                if(!frontier.isEmpty())
                UnExplored = addToUnExplored(frontier);
                for (Cords E : UnExplored) {
                    System.out.println("unexplored worked just fine ::"+E.getRef());
                }
            }
            else
            {
                frontier.clear();

                /*search for all available spots  */
                System.out.println("Explored ::");
                display(Explored);
                frontier =allOptions(state,Explored);
                System.out.println("frontier");
                display(frontier);


                /*remove explored points from frontier */
                if(!Explored.isEmpty())
                frontier = removeExplored(Explored);


                System.out.println("frontier after removing Explored Cells");
                display(frontier);

                /*add state point to solutions */
                solution.add(state);

                /*find solution and add it to list */
                if(!frontier.isEmpty())
                solution = leastCostSpot(frontier,state);
                /*add solutions to explored list  */
                if(!solution.isEmpty())
                Explored = addToExplored(solution);
                /*remove explored points from frontier */
                if(!Explored.isEmpty())
                frontier = removeExplored(Explored);
                /*add unexplored spots from frontier to unexplored list */
                if(!frontier.isEmpty())
                UnExplored = addToUnExplored(frontier);
                System.out.println("frontier");
                display(frontier);
                System.out.println("Unexplored");
                display(UnExplored);
            }
            if(reachedGoal(solution,goal)==true){
                System.out.println("founded the goal solution found");
                displayMove(solution);
                break;
            }
        //     /* Loop to find all available spots */
        //     frontier = allOptions(state); 
            


        // /*If the Explored list is not empty */
        // if(!Explored.isEmpty())
        //     frontier = removeExplored(frontier);

        // /*Find the optimal spote and add it to the solution list  */
        // solution = leastCostSpot(frontier);
        
        // /*Repaint Solutions in Green bg */
        // System.out.println("repaint available spots in green background");
        // for(Cords d : solution)
        // {
        //     System.out.println("("+d.getX()+","+d.getY()+")");
        //     cells.get(d.getRef()).setBackground(Color.green);
        // }

        // // if(!Explored.isEmpty())
        // // for(Cords d : Explored)
        // // {
        // //     System.out.println("("+d.getX()+","+d.getY()+")");
        // //     cells.get(d.getRef()).setBackground(Color.orange);
        // // }
        
        // /*display all the solution  */
        // for (Cords d : solution){
        //     System.out.println("("+d.getX()+","+d.getY()+")reference"+d.getRef());
        // }

        // /*New Solution becomes the new State  */
        // state = solution.get(solution.size()-1);
        // System.out.println("The New State Cords :: ("+state.getX()+","+state.getY()+")reference"+state.getRef());
        
        // /* if solution contain goal otherwise search for the optimal solution*/

        // if(reachedGoal(solution)){
        //     System.out.println("optimal solution WAs Founded ");
        //     break;
        // }else
        //     state = frontier.get(0);
        
        }
        /****************************************************************
         * */

        
        
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