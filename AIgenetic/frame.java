import javax.swing.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.event.*;
public class frame extends JFrame implements ActionListener{
    /*Strings  */
    String start= "Start";
    /* GUI ATTRIBUTES */
    /* numeric variables */
    private int width=500,height=500;
    private int col=20,row=20;
    Dimension d = new Dimension(width,height) ;
    SpinnerNumberModel number = new SpinnerNumberModel(5,5,10,1);
    JSpinner spinner = new JSpinner(number);
    Random rn = new Random();
    /*Panel Layout style  */
    GridLayout grid = new GridLayout(row,col);
    BorderLayout border = new BorderLayout();

    /*Panels */
    JPanel Pcromo_one;
    JPanel Pcromo_two;
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

    /*JButtons  */
    JButton start_btn = new  JButton(start);
    /*JLabels  */
    JLabel code = new JLabel();
    
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
    
    // ArrayList<Cromo> cromo_two = new ArrayList<Cromo>();
    
    /*making a random code afte clicking the start button  */
    ArrayList<Cromo> generateTarget(int length){
        
        for(int i=0; i < length ; i++){
            Cromo c = new Cromo(colors[new Random().nextInt(colors.length)], null,i);
            codebreaked.add(c);
            System.out.print(c.getColor().toString()+",");
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
                // x = ThreadLocalRandom.current().nextInt(0,colors.length);
                x= rn.nextInt(colors.length);
                while(x == prev[j])
                {
                    x= rn.nextInt(colors.length);
                }
                
                Cromo c = new Cromo(colors[x], null, j);
                //c.setColor(colors[x]);
                cromo.add(c);
                
                prev[j] = x;
               
            }
            
            
            guesses.add(cromo);
            
        }
        System.out.println("Fitness of all the colors is null");
        int i=0;
        for (ArrayList<Cromo> c : guesses) {
            i++;
            for (Cromo e : c) {
                System.out.print(i+":"+e.getFitness());
            }
            System.out.println(c.size());
        }
        return guesses;
    }


    public ArrayList< ArrayList<Cromo>> calculateFitness(ArrayList< ArrayList<Cromo>> cromoSet ,ArrayList<Cromo> target){
        boolean pass ;
        // ArrayList<ArrayList<Cromo>> cromose = new ArrayList<ArrayList<Cromo>>();
        /*loop all the cromosoms we added to calculate the fitness of each one  */
        for(ArrayList<Cromo>  cromosom : cromoSet){
            

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

                if(!pass){
                    for(int i = 0; i< target.size();i++){
                       // if the color exist and the order is not equal thene the we set the fitness to 0.5
                       if((current.getColor() == target.get(i).getColor())){
                           current.setFitness(0.5);
                           break;
                       }
                       
                      }
                }
               
            }

        }
        return cromoSet;
    }

    /* VERIFYING IF THE TARGET IS EQUAL TO THE RESULTED CROMOSOM */
    
    public void lunchGame(){

        codeMaker = generateTarget(3);
        
        cromose = calculateFitness(generateChildGuesses(2, 3), codeMaker);
        for (ArrayList<Cromo> c : cromose) {
            for (Cromo e : c) {
                System.out.print(e.getFitness()+"::"+e.getColor() +",");
            }
            System.out.println(c.size());
        }
        System.out.println(cromose.size());


        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    public static void main(String argv[]){
       frame f =  new frame();
       f.lunchGame();
    }

}
