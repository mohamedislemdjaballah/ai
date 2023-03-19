import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTreeUI.TreeHomeAction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.event.*;
import java.lang.annotation.Target;
import java.nio.channels.NetworkChannel;
public class frame extends JFrame implements ActionListener{
    Color[] existingT ;  
    int steps =0;
    LinkedHashSet<Color> existing = new LinkedHashSet<>();
    /*Strings  */
    String start= "Start";
    String title="jenie";
    /* GUI ATTRIBUTES */
    /* numeric variables */
    private int width=800,height=600;
    private int col=20,row=20;
    public int chromosomms;
    public int SequenceL;
    Dimension d = new Dimension(width,height) ;
    SpinnerNumberModel number = new SpinnerNumberModel(5,5,10,1);
    JSpinner spinner1= new JSpinner(number);
    JSpinner spinner2 = new JSpinner(number);
    Random rn = new Random();
    /*Panel Layout style  */
    GridLayout grid = new GridLayout(row,col);
    BorderLayout border = new BorderLayout();

    /*Panels */
    JPanel settings = new JPanel();
    JPanel board = new JPanel();
   static JPanel target = new JPanel();
    JPanel chromosomes = new JPanel();
    JPanel finalC = new JPanel();
    
    /*Style and Colors */
    ///  small font size 
    Font sfont= new Font("Arial",Font.BOLD,24);
    // big sized font 
    Font bfont= new Font("Arial",Font.BOLD,34);
    
    // Font Colors
    Color fontColor = new Color(0,0, 0);
    Color bgColor = new Color(255,255,255,100);
    
    //borders
    Border borderSolid = BorderFactory.createLineBorder(Color.BLACK, 1);  
    
    /*JButtons  */
    JButton start_btn = new  JButton(start);
    JButton confirme = new JButton("Play");
    JButton play = new JButton("Start Animation");
    
    /*JLabels  */
    JLabel code = new JLabel();
    JLabel text1= new JLabel("Set Number of (guesses) ");
    JLabel text2 = new JLabel("Set Number of steps");
    JLabel text3 = new JLabel("Working on it");
    JLabel text4 = new JLabel("Finded the Sequence after ");
    JLabel text5 = new JLabel("Final Guesse");
    JLabel color ;
    /* handle Events Attributes */
    // code maker array
    ArrayList<Cromo> codeMaker = new ArrayList<Cromo>();
    ArrayList<JLabel> sq = new ArrayList<>();
    ArrayList<JLabel> guessed = new ArrayList<>();
    // set of colors allowed 
    Color[] colors = {
        new Color(255, 0, 0),
        new Color(0,225,0),
        Color.yellow,
        Color.blue,
        Color.GRAY,
        Color.magenta,
        Color.ORANGE,
        Color.pink, 
    }; 
    // cromosoms and final gusse arrays 
    ArrayList<Cromo> codebreaked = new ArrayList<Cromo>();
    ArrayList<ArrayList<Cromo>> cromose = new ArrayList<ArrayList<Cromo>>();
    
    ArrayList<Cromo> cromo_one;
    ArrayList<Cromo> cromo_two  ;
    
    /*making a random code afte clicking the start button  */
    ArrayList<Cromo> generateTarget(int length){
        
        for(int i=0; i < length ; i++){
            Cromo c = new Cromo(colors[new Random().nextInt(colors.length)], null,i);
            codebreaked.add(c);
            System.out.print("TARGET COLOR :::: "+c.getColor().toString()+",");
        }
        System.out.println();
        return codebreaked;
    }

    /* Making a set of guesse  */
    
    ArrayList< ArrayList<Cromo>> generateChildGuesses(int nbrOfchilds,int length){
    
        ArrayList< ArrayList<Cromo> > guesses = new ArrayList< ArrayList<Cromo> >();
        int x;
        for(int i=0; i < nbrOfchilds ; i++){
            ArrayList<Cromo> cromo = new ArrayList<Cromo>();
            int[] prev = new int[length];
            for(int j=0; j < length ; j++)
            {
                // while x is giving the sane color loop again to find another color thene the previous chrono
                x= rn.nextInt(colors.length);
                while(x == prev[j]) x= rn.nextInt(colors.length);
                
                Cromo c = new Cromo(colors[x], null, j);
                //c.setColor(colors[x]);
                cromo.add(c);
                
                prev[j] = x;
               
            }
            guesses.add(cromo);
        }
        return guesses;
    }

    /*  CALCULATE THE FITNESS OF EACH GENE IN THE CHROMOSOME */
    public ArrayList< ArrayList<Cromo>> calculateFitness(ArrayList< ArrayList<Cromo>> cromoSet ,ArrayList<Cromo> target){
        boolean pass ;
        // ArrayList<ArrayList<Cromo>> cromose = new ArrayList<ArrayList<Cromo>>();
        /*loop all the cromosoms we added to calculate the fitness of each one  */
        for(ArrayList<Cromo>  cromosom : cromoSet)
            for (Cromo current : cromosom) {
                pass=false;
                // current.setFitness(0.0);
                int order = current.getOrder();
                current.setFitness(0.0);
                 // if the color and the order are equals thene the fitness equals 1
                 if((current.getColor() == target.get(order).getColor())&& (current.getOrder() == target.get(order).getOrder()) ){
                    current.setFitness(1.0);
                    pass = true;
                }

                if(!pass)
                    for(int i = 0; i< target.size();i++)
                       // if the color exist and the order is not equal thene the we set the fitness to 0.5
                       if((current.getColor() == target.get(i).getColor())){current.setFitness(0.5);break;}}

    return cromoSet;
    
}

    /* VERIFYING IF THE TARGET IS EQUAL TO THE RESULTED CROMOSOM */
    public boolean equivelant(ArrayList<Cromo> result , ArrayList<Cromo> target){
        
        Double fitness = 0.0;
        
        for(Cromo r : result) 
            fitness = fitness + r.getFitness();
            System.out.println(fitness+"/"+target.size()+"=");
        if(fitness/target.size() == 1.0)
       { return true;}
        return false;
    }

    /*Merging the cromosoms */
    ArrayList<Cromo> mergeCromosoms(ArrayList<ArrayList<Cromo>> cromoSet ,int length){
        
        // int i=0;
        Double[] fitness = new Double[length];
        int current = 0;
        ArrayList<Cromo> result = new ArrayList<>();
        Double max = 0.0;
       
        /* Calculate the totale fitness of all the chromosoms */
        for(ArrayList<Cromo> c : cromoSet){
            for (Cromo e : c) {if(fitness[current] == null)fitness[current] = 0.0;fitness[current] = fitness[current]+ e.getFitness();}current++;}

        System.out.println();
        
        max = fitness[0];
        /* Getting the most bigger fitness among the set of chromosoms */
        for (int index = 0; index < cromoSet.size(); index++) {
            System.out.println(fitness[index]);
            if(fitness[index] != null) if(max<fitness[index])max = fitness[index];}
        System.out.println("max is "+max);
        // if(max == 3) System.exit(0);
        /*looping again to merge the chromosoms */
        current = 0;
        for(ArrayList<Cromo> c : cromoSet){if(fitness[current] == max)for (Cromo e : c)result.add(e);current++;}
        
        // Display the chromosom 1
        for (Cromo e : result) {System.out.println(e.getFitness());}
          
        return result;
    }

    /* NEW EQUIVALANT GENERATION  */
    ArrayList< ArrayList<Cromo>> generateButterGeneration(  ArrayList< Cromo > parent,int nbrOfchilds,int length){
    
        ArrayList< ArrayList<Cromo> > guesses = new ArrayList< ArrayList<Cromo> >();
        int x;
        int i = 0;
        
            // existing colors
            for (Cromo c : parent) {if(c.getFitness() == 0.5){existing.add(c.getColor());i++;}}
            i=0;
            for(Color c : existing)
            {
                System.out.println("existing ::"+c);
            }
            i=0;
        for( i=0; i < nbrOfchilds ; i++){

            ArrayList<Cromo> cromo = new ArrayList<Cromo>();
            int[] prev = new int[length];
            existingT = new Color[length];

            for(int j=0; j < length ; j++)
            {
                x= rn.nextInt(colors.length);
                // while x is giving the sane color loop again to find another color thene the previous chrono
               if(parent.get(j).getFitness() == 0.0)
               {
                while(x == prev[j] ) x= rn.nextInt(colors.length);
                Cromo c = new Cromo(colors[x], null, j);
                cromo.add(c);
               }else if(parent.get(j).getFitness() == 0.5)
               {
                x= rn.nextInt(colors.length);
                
                // System.out.println("random"+x);
                Cromo c = new Cromo(colors[x], null, j);
                cromo.add(c);
               }else 
               if(parent.get(j).getFitness() == 1.0)
               {
                cromo.add(parent.get(j));
               }
                //c.setColor(colors[x]);
                
                
                prev[j] = x;
               
            }
            guesses.add(cromo);
        }
        return guesses;
    }
    // public void lunchGame(){

    //     codeMaker = generateTarget(4);
       
    //     // cromose =  generateChildGuesses(2, 3);
    //     cromose =calculateFitness(generateChildGuesses(2, codeMaker.size()), codeMaker);
    //     /* Display the chromosomms generated  */
    //     for (ArrayList<Cromo> c : cromose) for (Cromo e : c) System.out.print(e.getFitness()+"::"+e.getColor() +",");
    //     int steps =0;
    //     // System.out.println(cromose.size());
    //     cromo_two =  mergeCromosoms(cromose,codeMaker.size());
    //     while(!equivelant(cromo_two, codeMaker))
    //         {
    //             cromose =calculateFitness(generateButterGeneration(cromo_two,2, codeMaker.size()), codeMaker);
    //             cromo_two = new ArrayList<>();
    //             cromo_two =  mergeCromosoms(cromose,codeMaker.size());
    //             steps ++;
    //         }
    //         for (Cromo c : codeMaker) {
    //             System.out.println("TARGET COLOR :::: "+c.getColor().toString()+",");
    //         }
            
    //         for (Cromo c : codeMaker) {
    //             System.out.println("Generated COLOR :::: "+c.getColor().toString()+",");
    //         }
    //         System.out.println("code fonded after "+steps+"steps");
    //     // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     // setVisible(true);
    // }
    public void creatFrame(){
        setTitle(title);
        setSize(d);
        setLayout(border);

        settings.setLayout(new GridLayout(1,7));
        GridLayout boardg = new GridLayout(3,1);
        boardg.setHgap(100);
        boardg.setVgap(100);
        board.setLayout(boardg);
        board.setBorder(borderSolid);

        add(settings,BorderLayout.NORTH);
        add(board,BorderLayout.CENTER);
        

        confirme.addActionListener(this);
        start_btn.addActionListener(this);
        play.addActionListener(this);


        text1.setVisible(false);
        spinner1.setVisible(false);
        confirme.setVisible(false);
        play.setVisible(false);


        settings.add(start_btn);
        settings.add(text1);
        settings.add(spinner1);
        settings.add(confirme);
        settings.add(play);

        target.setLayout(new GridLayout(1,(int)spinner1.getValue()));
        finalC.setLayout(new GridLayout(1,(int)spinner1.getValue()));
        // System.out.println(spinner1.getValue());
        // target.setBackground(Color.red);
        target.setVisible(false);
        finalC.setVisible(false);

        board.add(target);
        board.add(finalC);
        
        board.setOpaque(true);

        
        
        board.setOpaque(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start_btn){
            text1.setVisible(true);
            spinner1.setVisible(true);
            confirme.setVisible(true);
        }else if(e.getSource() == confirme){

            
            confirme.setVisible(false);
            play.setVisible(true);
            target.setVisible(true);
            finalC.setVisible(true);
            codeMaker = generateTarget((int)spinner1.getValue());
            for (Cromo c : codeMaker) 
        {
            color = new JLabel("Color");
            color.setForeground(c.getColor());
            color.setBackground(c.getColor());
            color.setBorder(borderSolid);
            target.add(color);
            sq.add(color);
            // System.out.println("Generated COLOR :::: "+c.getColor().toString()+",");
        }
        for (Cromo c : codeMaker) 
        {
            JLabel b = new JLabel("Target Gene");
            finalC.add(b);
            guessed.add(b);
            // System.out.println("Generated COLOR :::: "+c.getColor().toString()+",");
        }
      
            // int i=0;
            // if(!sq.isEmpty())
            // for (Cromo c : codeMaker) 
            // {
            //     sq.get(i).setForeground(c.getColor());
            //     i++;
            //     // target.add(color);
                
            //     // System.out.println("Generated COLOR :::: "+c.getColor().toString()+",");
            // }
        }else if(e.getSource() == play)
        {
                 // cromose =  generateChildGuesses(2, 3);
        cromose =calculateFitness(generateChildGuesses(2, codeMaker.size()), codeMaker);
        /* Display the chromosomms generated  */
        for (ArrayList<Cromo> c : cromose) for (Cromo r : c) System.out.print(r.getFitness()+"::"+r.getColor() +",");
        
        // System.out.println(cromose.size());
        cromo_two =  mergeCromosoms(cromose,codeMaker.size());
        
            try {
                new Thread( new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        while(!equivelant(cromo_two, codeMaker))
                        {
                            cromose =calculateFitness(generateButterGeneration(cromo_two,2, codeMaker.size()), codeMaker);
                            cromo_two = new ArrayList<>();
                            cromo_two =  mergeCromosoms(cromose,codeMaker.size());
                            paint(cromo_two);
                            // System.out.println("cromo size"+cromo_two.size());
                            steps ++;
                            try {
                                
                                Thread.sleep(1000);
                            } catch (Exception d) {
                                // TODO: handle exception
                                d.printStackTrace();
                            }
                        }
                        for (Cromo c : codeMaker) {
                            System.out.println("TARGET COLOR :::: "+c.getColor().toString()+",");
                        }
                        
                        for (Cromo c : cromo_two) {
                            System.out.println("Generated COLOR :::: "+c.getColor().toString()+",");
                            
                        }
                        System.out.println("code fonded after "+steps+"steps");
                
                    }
                }).start();
            } catch (Exception e1) {
                // TODO: handle exception
            }
            
        }
    }
public void paint(ArrayList<Cromo> c){
    Color[] cls = new Color[10];
    int i=0;
    System.out.println(guessed.size());
   
    for(Cromo e : c){
        cls[i] = e.getColor();
        System.out.println("cls ::"+cls[i]);
        i++;
    }
    i=0;
    try {
        new Thread( new Runnable() {
            @Override
            public void run() {
                
                // TODO Auto-generated method stub
                int i = 0;
                for(JLabel d : guessed)
                    {
                        d.setForeground(cls[i]);i++;
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e2) {
                            // TODO: handle exception
                            e2.printStackTrace();
                        }}
                
            }
        }).start();
        
    } catch (Exception e1) {
        // TODO: handle exception
        e1.printStackTrace();
    }
   
}
    public static void main(String argv[]){
       frame f =  new frame();
    //    f.lunchGame();
       f.creatFrame();
    }

}
