//exp 1 RTFSC 10 fold cv varying swbv
package wordcount;

import java.io.*;

public class Wordcount 
{

    static String term;
    static String termlist[] = new String[110000];
    
    static int termcount = 0;
    static int finaltermcount = 0;
    
    static int termpresentflag;
    static int token;
    //static double sentence_polarity;
    static int visited[] = new int[1000];

    
    static int nooffiles = 12500; //no of sentences here
    //static double avg_acc = 0;
    

    public static void main(String[] args) throws Exception {
        createtermdocmatrices();
        
    }

    public static void createtermdocmatrices() throws FileNotFoundException, IOException {
        int positive_termdoccount[][] = new int[110000][12500];

        int negative_termdoccount[][] = new int[110000][12500];
       
        int j=0;

        FileReader rd = new FileReader("C:\\Users\\Tanmay\\Desktop\\BE\\Python_project_files\\positive.txt");                    
        StreamTokenizer st = new StreamTokenizer(rd);
        
        st.resetSyntax();
        st.wordChars('a', 'z');
        st.wordChars('A', 'Z');
        st.wordChars('\'', '\'');
        
        while ((token = st.nextToken()) != StreamTokenizer.TT_EOF)
        {          
            termpresentflag=0;
            switch (token)
            {                            
                case StreamTokenizer.TT_WORD:
                    term = st.sval;
                    System.out.println(j+" "+term);
                    if(termcount!=0) // incrementing the count if term already present in termlist
                        for(int i=0;i<termcount;i++)
                        {
                            if (termlist[i].equals(term))
                            {
                               
                                positive_termdoccount[i][j] = positive_termdoccount[i][j] + 1;                                                                                 
                                termpresentflag=1;           
                                break;
                            }
                        }
                    if(termpresentflag==0) // for storing term which is not present includes first term
                    {
                        termlist[termcount]=term;    
                        positive_termdoccount[termcount][j]=1;
                        termcount++;                              
                    }                                                                
                    break;
                    
                case StreamTokenizer.TT_NUMBER:
                    break;
                    
                case StreamTokenizer.TT_EOL:
                    //System.out.println("line no "+j+"\n\n");
                    j++;
                    break;
                    
                case StreamTokenizer.TT_EOF:
                    break;                             
            }                           
        }    
                
        
       for(j=0;j<termcount;j++) 
        {
            System.out.println(termlist[j]);
            for(int i=0;i<5330;i++)//positive_listOfFiles.length            
                System.out.print(+positive_termdoccount[j][i]+"  "); //termdoccount[i][j]);
            System.out.println();    
        }
               
        System.out.println(termcount+" "+j);    
             
        //System.out.println("1000 positive files done with "+termcount+" terms");        
       
       j=0;
       rd = new FileReader("C:\\Users\\Tanmay\\Desktop\\BE\\Python_project_files\\negative.txt");                    
       st = new StreamTokenizer(rd);   
       
       st.resetSyntax();
       st.wordChars('a', 'z');
       st.wordChars('A', 'Z');
       st.wordChars('\'', '\'');
       
       while ((token = st.nextToken()) != StreamTokenizer.TT_EOF)
       {          
           termpresentflag=0;
           switch (token)
           {                            
               case StreamTokenizer.TT_WORD:
                   term = st.sval;
                   
                   if(termcount!=0)
                       for(int i=0;i<termcount;i++)
                       {
                           if (termlist[i].equals(term))
                           {
  
                               negative_termdoccount[i][j] = negative_termdoccount[i][j] + 1;                                                                                 
                               
                               termpresentflag=1;                           
                               break;
                           }
                       }
                   if(termpresentflag==0)
                   {
                       termlist[termcount]=term;    
                       negative_termdoccount[termcount][j]=1;
                       
                       termcount++;                              
                   }                                                                
                   break;
                   
                   
               case StreamTokenizer.TT_NUMBER:
                   term = Double.toString(st.nval);                                                         
                   
                   if(termcount!=0)
                       for(int i=0;i<termcount;i++)                           
                       {                           
                           if (termlist[i].equals(term))                               
                           {                               
                               
                               negative_termdoccount[i][j] = negative_termdoccount[i][j] + 1;                                                              
                                                            
                               termpresentflag=1;                                                          
                               break;                               
                           }                           
                       }                   
                   if(termpresentflag==0)                       
                   {                       
                       termlist[termcount]=term;                                                   
                       //             System.out.println(termcount + " ");                       
                       negative_termdoccount[termcount][j]=1;                       
                                          
                       termcount++;                                                     
                   }                                                                                   
                   break;
               
               case StreamTokenizer.TT_EOL:
                   j++;
                   break;
                   
               case StreamTokenizer.TT_EOF:
                   break;                             
           }                           
       }
        
//System.out.println("2000 files done with total "+termcount+" terms");
       /*for(j=0;j<termcount;j++) 
       {
           System.out.println(termlist[j]);
           for(int i=0;i<5330;i++)//negative_listOfFiles.length            
               System.out.print(+negative_termdoccount[j][i]+"  "); //termdoccount[i][j]);
           System.out.println();    
       }
               */
       System.out.println(termcount+" "+j);    

        File output = new File("C:\\termlist.csv");
        Writer writer_output = new FileWriter(output);

        File output_pos = new File("C:\\pos_termdocmatrix.csv");
        Writer writer_output_pos = new FileWriter(output_pos);

        File output_neg = new File("C:\\neg_termdocmatrix.csv");
        Writer writer_output_neg = new FileWriter(output_neg);

        for (int i = 0; i < termcount; i++) {
            writer_output.write(termlist[i] + "\n");
        }
        writer_output.close();
        
        System.out.println(nooffiles);

        for (int i = 0; i < termcount; i++) {
            for ( j = 0; j < nooffiles; j++) {
                writer_output_pos.write(positive_termdoccount[i][j] + " ");
                writer_output_neg.write(negative_termdoccount[i][j] + " ");
            }
            writer_output_pos.write("\n");
            writer_output_neg.write("\n");
        }
        writer_output_pos.close();
        writer_output_neg.close();
    }

    
}


