package AI;
import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.LinkedHashSet;
import java.util.Stack;
// import java.util.Queue;
import java.util.concurrent.TimeUnit;


public class CreatABoard extends JFrame implements ActionListener{


    /* A frontier is an Array list that holds the stat<Cords> */
    ArrayList<Cords> frontier = new ArrayList<Cords>();

    /*An Explored list of Node to add the unprober nodes  */
    LinkedHashSet<Cords> Explored = new LinkedHashSet<Cords>();
    Stack<Cords> UnExplored = new Stack<Cords>();

    /*A solutions to add the probre nods to the path  */
    Stack<Cords> solution = new Stack<Cords>();

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
    JLabel battery = new JLabel("100%");
    JButton addBot = new JButton("Add A Bot");
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
    ImageIcon blank = new ImageIcon("/home/mohamed/Documents/AI/ai/AI/blank.png");
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
        addBot.setVisible(false);
        addBotAndGoal();
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
            width = (int)y.getValue();
            height = (int)x.getValue();
            x.setVisible(false);
            y.setVisible(false);
            DrawBoard.setVisible(false);
            cordinatLabel.setVisible(false);
            addBot.setVisible(true);
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
                    cell.setSize(this.getWidth()/(height), this.getHeight()/(width));
                    cells.add(cell);
                    cordinate = new Cords(i, j,0);
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
        
        space.setVisible(true);
        
        
        /*end of clicking on DrawBoard 
        
        Button Event */
    
    
    }else if(e.getSource() == StartSolving)
    {
        /* Seting startSolving Button Enable to false so we interupt any try to click
            * it again and 
            */
        StartSolving.setEnabled(false);
        battery.setVisible(true);
            // for (Cords cord : matrixCells)
            //     cord.setDistance(cord, goal);

            for (Cords cord : matrixCells)
                System.out.println("("+(cord.getX()+1)+","+(cord.getY()+1)+"):"+cord.getdistance());
            
            ;
            for(String action : actions)
                System.err.println("List of Action :"+action);

            
            probreMove(start);
            
    /*end of Start Solving  */
    }else if(e.getSource() == addBot)
    {
        
        addBot.setVisible(false);
        StartSolving.setVisible(true);
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
            /*  After seting the cords to the ending and the goal lets try calcuating the distance
            to each cell to the goal
            */
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
    }

}



boolean considerWater(Cords d, String dir){
    Color bgColor;
    Cords newState;
    Action move = new Action();
    switch(dir){
             /* in each move we make sure we are not getting over the height and width of the maze*/
             case "up" :
                    
             if(((state.getX()-1)>=0))
             {
                 
                 newState = move.Up(state,width);
                 bgColor =cells.get(newState.getRef()).getBackground();
                 
             if(( bgColor != Color.blue && bgColor != Color.red && bgColor != Color.black)  )
                 return false;
             }
         
         break;
         case "down" : 
     
         
             if(((state.getX()+1)<height))
             {
                 newState = move.Down(state,width);
                 bgColor =cells.get(newState.getRef()).getBackground();
                 
                 if(( bgColor != Color.blue && bgColor != Color.red && bgColor != Color.black)  )
                 return false;
             }
             ;
         break;
         case "left" : 
         
             if((state.getY()-1)>=0)
             {
                 newState = move.Left(state);
                 bgColor =cells.get(newState.getRef()).getBackground();
                 if(( bgColor != Color.blue && bgColor != Color.red && bgColor != Color.black)  )
                 return false;
             }
         break;

         case "right" : 
             if(((state.getY()+1)<width))
             {
                 newState = move.Right(state);
                 bgColor =cells.get(newState.getRef()).getBackground();
                 if(( bgColor != Color.blue && bgColor != Color.red && bgColor != Color.black)  )
                 return false;
             }
             
         break;
    }
    return true;
}



void addBotAndGoal(){

    width = (int)y.getValue();
    height = (int)x.getValue();

    addBot.setFont(font);
    cordPanel.add(addBot);
    addBot.addActionListener(this);
    StartSolving.setVisible(false);
    solve();
}


    /* Solving method */
void solve()
{
    // width and height are just meant to store the matrix demonsion
    width = (int)y.getValue();
    height = (int)x.getValue();
    
    /*Add Start solving to the event if all is clear and values are set */
    StartSolving.setFont(font);
    battery.setFont(font);
    battery.setVisible(false);
    StartSolving.addActionListener(this);
    cordPanel.add(StartSolving);
    cordPanel.add(battery);
}


boolean nodeExploreded(Cords state,LinkedHashSet<Cords> Explored)
{
    for (Cords e : Explored) {
    if((state.getX() == e.getX())&&(state.getY() == e.getY()))
    return true;
    
}
    return false;
}



/* a method that returns all options */
ArrayList<Cords> allOptions(Cords state,LinkedHashSet<Cords> Explored)
{
Action move = new Action();
Cords newState;
Color bgColor;

 // width and height are just meant to store the matrix demonsion

 width = (int)x.getValue();
 height = (int)y.getValue();
 
 System.out.println("width" + width);

        for(String action : actions){
            
                switch(action){
                    /* in each move we make sure we are not getting over the height and width of the maze*/
                    case "up" :
                    
                        if(((state.getX()-1)>=0))
                        {
                            System.out.println("moving up");
                            newState = move.Up(state,width);
                            bgColor =cells.get(newState.getRef()).getBackground();
                            // System.out.println(bgColor);
                        if(cells.get(state.getRef()).getBackground() == Color.BLUE && bgColor == Color.blue){
                            System.out.println("we Cant Move");
                            break;
                        }
                        else
                        if(( bgColor != Color.red && bgColor != Color.black)  )
                        if(!Explored.isEmpty())
                            {
                                if(!nodeExploreded(newState, Explored))
                                    frontier.add(newState);
                    }else
                        frontier.add(newState);
                        }
                    ;
                    break;
                    case "down" : 
                
                    
                        if(((state.getX()+1)<height))
                        {
                            newState = move.Down(state,width);
                            bgColor =cells.get(newState.getRef()).getBackground();
                            // System.out.println("Down newState"+newState.getRef()+" bg"+bgColor+"oldState bgColor :"+cells.get(newState.getRef()).getBackground());
                            if(cells.get(state.getRef()).getBackground() == Color.BLUE && bgColor == Color.blue){
                                System.out.println("we Cant Move");
                                break;
                            }
                            else
                            if(( bgColor != Color.red && bgColor != Color.black))
                                if(!Explored.isEmpty())
                                {
                                    if(!nodeExploreded(newState, Explored))
                                        frontier.add(newState);
                        
                                }else
                                    frontier.add(newState);
                        }
                        ;
                    break;
                    case "left" : 
                    
                        if((state.getY()-1)>=0)
                        {
                            newState = move.Left(state);
                            bgColor =cells.get(newState.getRef()).getBackground();

                            System.out.println("newRed and color "+newState.getRef()+""+bgColor+"old State Red"+state.getRef());
                            if(cells.get(state.getRef()).getBackground() == Color.BLUE && bgColor == Color.blue){
                                System.out.println("we Cant Move");
                                break;
                            }
                            else
                            if( ( bgColor != Color.red && bgColor != Color.black))
                            if(!Explored.isEmpty())
                            {
                                System.out.println("walking left");
                                if(!nodeExploreded(newState, Explored))
                                    frontier.add(newState);
                    
                            }else
                                frontier.add(newState);
                        }
                    break;

                    case "right" : 
                        if(((state.getY()+1)<width))
                        {
                            newState = move.Right(state);
                            bgColor =cells.get(newState.getRef()).getBackground();
                            // System.out.println(bgColor);
                            if(cells.get(state.getRef()).getBackground() == Color.BLUE && bgColor == Color.blue){
                                System.out.println("we Cant Move");
                                break;
                            }
                            else
                            if( ( bgColor != Color.red && bgColor != Color.black))
                            if(!Explored.isEmpty())
                            {
                                System.out.println("walking right");
                                if(!nodeExploreded(newState, Explored))
                                    frontier.add(newState);
                        
                                }else
                                    frontier.add(newState);
                        }
                        
                    break;
            }

         }
         displayf(frontier);
return frontier;
}



/*Remove All the Explored Nodes from the frontier */
Stack<Cords> removeExplored(LinkedHashSet<Cords> Explored){
    for(Cords e : Explored)
        UnExplored.remove(e);
    return UnExplored;
}

/*Find the most optimal spot to reach the goal */
Cords leastCostSpot(ArrayList<Cords> frontier){
    Cords bestMove;
    if(!frontier.isEmpty())
    {
        bestMove = frontier.get(frontier.size()-1);
    }else 
    if(frontier.size()==1)
        {
            bestMove = frontier.get(0);
        }else
            bestMove = state;
    
    System.out.println("bestMove"+bestMove.getRef());
    //CALCULATE THE DISTANCE BETWEEN THE CURRENT NODE AND THE GOAL
    for (Cords cord : frontier)
        cord.setDistance(cord, goal);
        bestMove.setDistance(bestMove, goal);
        // int optimal = frontier.get(frontier.size()-1).getdistance();
        // solution.add(frontier.get(frontier.size()-1));
        /*Search for the optimal move in the frontier */
        for(Cords d : frontier)
        {          
            /*if the new cords has a less distance thene the previous node we remove
             * than it is not the optimal solution so we remove it
             */
            if(bestMove.getdistance() > d.getdistance())
            {
                bestMove = d;
            }
        }
        System.out.println("bestMove"+bestMove.getRef());
    return bestMove;
}



/*Adding explored nodes to explored list */
LinkedHashSet<Cords> addToExplored(Stack<Cords> solution){
    for(Cords e : solution)
        Explored.add(e);
    return Explored;
}
/*Adding unexplored nodes to explored list */
void addToUnExplored(ArrayList<Cords> frontier){
    for(Cords e : frontier)
        UnExplored.push(e);
    
}



/*Display Lst contents */
void display(Stack<Cords> list){
    for(Cords e : list)
        System.out.println("("+e.getX()+""+e.getY()+")"+e.getRef()+"Ref");
}

void displayf(ArrayList<Cords> list){
    for(Cords e : list)
        System.out.println("("+e.getX()+""+e.getY()+")"+e.getRef()+"Ref");
}



/* Veridy if we found a solution */
boolean reachedGoal(Stack<Cords> sol,Cords goal){
    for (Cords e : sol) {
        if(e.getRef() ==  goal.getRef())    
            return true;
    }
        
    return false;
}
/*****
 * Display solutions
 * @param solution
 * @throws Exception
 */ 
void displayMove(Stack<Cords> solution) throws Exception{

    new Thread(new Runnable() {
        @Override
        public void run(){
            int i=0;
            int battr=100;
            //TODO Auto-generated method stub
            try {
                for (Cords d : solution) {
                    cells.get(d.getRef()).setBackground(Color.green);
                    Icon previousIcon= cells.get(d.getRef()).getIcon();
                    Image image = robot.getImage();
                    Image Simage = image.getScaledInstance(cells.get(d.getRef()).getWidth(), cells.get(d.getRef()).getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaled = new ImageIcon(Simage);
                    cells.get(d.getRef()).setIcon(scaled);
                    
                    try {
                        TimeUnit.MILLISECONDS.sleep(900);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(d.getRef() == start.getRef())
                    {
                        image = blank.getImage();
                        image = image.getScaledInstance(cells.get(d.getRef()).getWidth(), cells.get(d.getRef()).getHeight(), Image.SCALE_SMOOTH);
                        scaled = new ImageIcon(Simage);
                        cells.get(d.getRef()).setIcon(scaled);
                        
                    }else if(d.getRef() == goal.getRef()){
                        image = robot.getImage();
                        image = image.getScaledInstance(cells.get(d.getRef()).getWidth(), cells.get(d.getRef()).getHeight(), Image.SCALE_SMOOTH);
                        scaled = new ImageIcon(Simage);
                        cells.get(solution.get(i).getRef()).setIcon(scaled);
                    }else if(d.getRef() != start.getRef())
                      cells.get(solution.get(i).getRef()).setIcon(previousIcon);
                    Thread.sleep(1000);
                    i++;
                    battr =battr-25;
                    battery.setText(Integer.toString(battr)+"%");
                    if(battr == 0){   	
                        Thread.sleep(3000);
                        battr = 100;
                        battery.setText(Integer.toString(battr)+"%");
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }).start();
    
}
 void paintExplored(LinkedHashSet<Cords> Explored){
    for (Cords e : Explored) {
        cells.get(e.getRef()).setBackground(Color.orange);
    }
    }
void probreMove(Cords start){
    state = null;
    solution.push(start);
    while(true){

        /* THIS IF AND ELSE STATEMENTS CHOSES THE BEST SPOTE WE NEED TO TAKE */
        if(state == null)
        {
            state = start;
        }else 
        /*IF NO SOLUTION WAS FOUND THAT MEANS WE HAVE NO OTHER MOVES 
        AND NO POSSIPLE CELLS TO EXPLORE */
        if(frontier.isEmpty() && UnExplored.isEmpty()){   
            System.out.println("no solution was found");
            paintExplored(Explored);
            break;
        }else 
        /*IF THE PATH WE ARE WALKING IS THE ONLY PATH WE HAVE OR THE BEST ONE WE ASSUME
         *IT IS THENE LET'S GO AND TAKE THE BEST MOVE
         */
        if((!frontier.isEmpty() && UnExplored.isEmpty())||(!frontier.isEmpty() && !UnExplored.isEmpty())){
            state = solution.lastElement();
            
            frontier.clear();
        }else 
        /*IF THE FRONTIER IS EMPTY AND WE HAVE A POSSIBLE MOVE WE DIDN'T 
         *THENE LET'S GO AND TRY IT OUT 
         */
        if(frontier.isEmpty() && !UnExplored.isEmpty())
        {

            state = UnExplored.lastElement();
            solution.pop();
            solution.pop();
            System.out.println("state from unexplored  :::: "+state.getRef());
            UnExplored.pop();
            solution.add(state);
        }
        // AT FIRST WE NEED TO HOLD THE BEST MOVES IN THE FRONTIER
        frontier = allOptions(state, Explored);
        
        // SECONDLY WE NEED TO ASSUME WHAT MIGHT BE THE BEST MOVE IN THE FRONTIER
        solution.push(leastCostSpot(frontier));
        // VERIFY IF WE REACHED THE GOAL OR WE DID NOT
        if(reachedGoal(solution,goal) == true){
            System.out.println("we reched goal after walking "+solution.size());
            display(solution);
            paintExplored(Explored);
            System.out.println("explored cells we walked are "+(Explored.size()+1));
            try {
                displayMove(solution);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            break;
        }else{
            if(!frontier.isEmpty())
            {
                state = solution.lastElement();
               
                // frontier.remove(state);
                if(!frontier.isEmpty())
                    addToUnExplored(frontier);
                
            }
            Explored = addToExplored(solution);
            /*LEST REMOVE EXPLORED CELLS */
            UnExplored = removeExplored(Explored);
        }

    }
        
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