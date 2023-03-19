import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.event.*;
import java.lang.annotation.Target;
import java.nio.channels.NetworkChannel;
public class frame extends JFrame implements ActionListener{
    /*Strings  */
    String start= "Start";
    String title="jenie";
    /* GUI ATTRIBUTES */
    /* numeric variables */
    private int width=800,height=600;
    private int col=20,row=20;
    Dimension d = new Dimension(width,height) ;
    SpinnerNumberModel number = new SpinnerNumberModel(5,5,10,1);
    JSpinner spinner = new JSpinner(number);
    Random rn = new Random();
    /*Panel Layout style  */
    GridLayout grid = new GridLayout(row,col);
    BorderLayout border = new BorderLayout();

    /*Panels */
    JPanel propreties = new JPanel();
    JPanel gameContainer = new JPanel();
    JPanel Ptarget;
    JPanel Pfinal;
    JLayeredPane container = new JLayeredPane();
    
    
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
    /*JLabels  */
    JLabel code = new JLabel();
    JLabel text1= new JLabel("Set Number of (guesses) ");
    JLabel text2 = new JLabel("Final Guesse");
    JLabel text3 = new JLabel("Working on it");
    JLabel text4 = new JLabel("Finded the Sequence after ");
    JLabel text5 = new JLabel("Set Number of steps");
    
    /* handle Events Attributes */
    // code maker array
    ArrayList<Cromo> codeMaker = new ArrayList<Cromo>();
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
        return true;
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
        Color[] existing = new Color[parent.size()];  
            // existing colors
            for (Cromo c : parent) {if(c.getFitness() == 0.5){existing[i] = c.getColor();i++;}}
            i=0;
        for( i=0; i < nbrOfchilds ; i++){
            ArrayList<Cromo> cromo = new ArrayList<Cromo>();
            int[] prev = new int[length];
            
            for(int j=0; j < length ; j++)
            {
                x= rn.nextInt(colors.length);
                // while x is giving the sane color loop again to find another color thene the previous chrono
               if(parent.get(j).getFitness() == 0.0)
               {
                while(x == prev[j]) x= rn.nextInt(colors.length);
                Cromo c = new Cromo(colors[x], null, j);
                cromo.add(c);
               }else if(parent.get(j).getFitness() == 0.5)
               {
                x= rn.nextInt(existing.length);
                System.out.println("random"+x);
                Cromo c = new Cromo(existing[x], null, j);
                cromo.add(c);
               }
               if(parent.get(j).getFitness() == 0.5)
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
    public void lunchGame(){

        codeMaker = generateTarget(4);
       
        // cromose =  generateChildGuesses(2, 3);
        cromose =calculateFitness(generateChildGuesses(2, codeMaker.size()), codeMaker);
        /* Display the chromosomms generated  */
        for (ArrayList<Cromo> c : cromose) for (Cromo e : c) System.out.print(e.getFitness()+"::"+e.getColor() +",");
        int steps =0;
        // System.out.println(cromose.size());
        cromo_two =  mergeCromosoms(cromose,codeMaker.size());
        while(!equivelant(cromo_two, codeMaker))
            {
                cromose =calculateFitness(generateButterGeneration(cromo_two,2, codeMaker.size()), codeMaker);
                cromo_two = new ArrayList<>();
                cromo_two =  mergeCromosoms(cromose,codeMaker.size());
                steps ++;
            }
            for (Cromo c : codeMaker) {
                System.out.println("TARGET COLOR :::: "+c.getColor().toString()+",");
            }
            
            for (Cromo c : codeMaker) {
                System.out.println("Generated COLOR :::: "+c.getColor().toString()+",");
            }
            System.out.println("code fonded after "+steps+"steps");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
    }
    public void creatFrame(){
        setTitle(title);
        setSize(d);
        setLayout(border);
        // setBackground(bgColor);
        container.setBounds(0,0,width,height);
        add(container,BorderLayout.CENTER);
        container.setLayout(null);
        int newHeight = height/2, newWidth = width/2;
       
        propreties.setBounds(0, 0, newWidth+newWidth/4, height);
        propreties.setBorder(borderSolid);
        propreties.setLayout(null);
        propreties.setBackground(new Color( 0 , 0 , 0 ,100));
       
        start_btn.setBounds(newWidth/(newHeight/20), newHeight/(newHeight/10), newWidth/4, newHeight/4);
        start_btn.setBorder(borderSolid);
        start_btn.setFont(sfont);
        start_btn.setFocusable(false);
        start_btn.addActionListener(this);
        
        text1.setBounds(EXIT_ON_CLOSE, ABORT, WIDTH, HEIGHT);
        
        
        propreties.add(start_btn,BorderLayout.CENTER);
        container.add(propreties);
       
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start_btn){
            text1.setFont(sfont);
        }
        
        
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        
    }

    public static void main(String argv[]){
       frame f =  new frame();
       f.lunchGame();
       f.creatFrame();
    }

}
