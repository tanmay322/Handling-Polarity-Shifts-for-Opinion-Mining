package javaapplication14;
import java.util.List;
import java.io.StringReader;


import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

class JavaApplication14 {

  
  public static void main(String[] args) throws IOException {
    String parserModel = "C:\\stanford-parser-full-2015-01-29\\stanford-parser-full-2015-01-30\\edu\\stanford\\nlp\\models\\lexparser\\englishPCFG.ser.gz";
    if (args.length > 0) {
      parserModel = args[0];
    }
    LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);

    if (args.length == 0) {
      demoAPI(lp);
    } else {
      String textFile = (args.length > 1) ? args[1] : args[0];
      //demoDP(lp, textFile);
    }
  }
      public static void demoAPI(LexicalizedParser lp) throws IOException {
        String filename = "C:\\Users\\Tanmay\\Desktop\\BE\\Python_project_files\\positive.txt";
        FileWriter writer; 
                     writer = new FileWriter("neg_list.txt");
    try{
            BufferedReader br = new BufferedReader( new FileReader(filename));
            String strLine = null;
            StringTokenizer st = null;
            int lineNumber = 0, tokenNumber = 0;

            while((filename = br.readLine()) != null) {
                lineNumber++;
                //String[] result = filename.split("\n");
                List<CoreLabel> rawWords = Sentence.toCoreLabelList(filename);
                Tree parse = lp.apply(rawWords);
                TokenizerFactory<CoreLabel> tokenizerFactory =
                    PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
                Tokenizer<CoreLabel> tok =
                    tokenizerFactory.getTokenizer(new StringReader(filename));
                List<CoreLabel> rawWords2 = tok.tokenize();
                parse = lp.apply(rawWords2);

    TreebankLanguagePack tlp = lp.treebankLanguagePack(); // PennTreebankLanguagePack for English
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();

     Object[] list = tdl.toArray();
        
        TypedDependency typedDependency;
        for (Object object : list) {
        typedDependency = (TypedDependency) object;
        
        if (typedDependency.reln().getShortName().equals("neg")) {
                //System.out.println(object);
                //System.out.println(typedDependency.gov());
                writer.write(typedDependency.gov()+"\n");
                writer.flush();
                
}
        
            }
    }  }catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

}
}

