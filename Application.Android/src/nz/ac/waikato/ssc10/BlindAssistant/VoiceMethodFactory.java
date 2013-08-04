package nz.ac.waikato.ssc10.BlindAssistant;

import android.util.Log;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarLexer;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarListener;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarParser;
import nz.ac.waikato.ssc10.text.PlaceholderMapStringTemplate;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.javatuples.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 15/07/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class VoiceMethodFactory {
    private static final String TAG = "VoiceMethodFactory";
    private VoiceMethodMap voiceMethods = new VoiceMethodMap();

    public VoiceMethodFactory() {
        try {
            voiceMethods.put(new PlaceholderMapStringTemplate("where am I"), VoiceMethodFactory.class.getDeclaredMethod("SayLocation", BlindAssistant.class, Map.class));
        } catch (NoSuchMethodException ex) {
            Log.e(TAG, ex.getMessage());
        }

        try {
            voiceMethods.put(new PlaceholderMapStringTemplate("take me to {Destination}"), VoiceMethodFactory.class.getDeclaredMethod("SayGibberish", BlindAssistant.class, Map.class));
        } catch (NoSuchMethodException ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    public VoiceMethodFactory(InputStream is) throws IOException {
        try {
            ANTLRInputStream inputStream = new ANTLRInputStream(is);

            VoiceInputGrammarLexer lexer = new VoiceInputGrammarLexer(inputStream);
            CommonTokenStream cts = new CommonTokenStream(lexer);

            VoiceInputGrammarParser parser = new VoiceInputGrammarParser(cts);
            VoiceInputGrammarParser.ProgramContext program = parser.program();

            ParseTreeWalker.DEFAULT.walk(new VoiceInputWalker(), program);
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());

            throw ex;
        }
    }

    public Pair<Method, Map<String, String>> get(String input) {
        return voiceMethods.get(input);
    }

    public void SayLocation(BlindAssistant assistant, Map<String, String> arguments) {
        assistant.say("you are at " + assistant.getLocationName());
    }

    public void SayGibberish(BlindAssistant assistant, Map<String, String> arguments) {
        assistant.say("taking you to " + arguments.get("{Destination}"));
    }

    private class VoiceInputWalker implements VoiceInputGrammarListener {

        @Override
        public void enterSay(VoiceInputGrammarParser.SayContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitSay(VoiceInputGrammarParser.SayContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterProgram(VoiceInputGrammarParser.ProgramContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitProgram(VoiceInputGrammarParser.ProgramContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterNavigate(VoiceInputGrammarParser.NavigateContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitNavigate(VoiceInputGrammarParser.NavigateContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterStmt(VoiceInputGrammarParser.StmtContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitStmt(VoiceInputGrammarParser.StmtContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterKeywordOperation(VoiceInputGrammarParser.KeywordOperationContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitKeywordOperation(VoiceInputGrammarParser.KeywordOperationContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterSet(VoiceInputGrammarParser.SetContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitSet(VoiceInputGrammarParser.SetContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterComment(VoiceInputGrammarParser.CommentContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitComment(VoiceInputGrammarParser.CommentContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void visitTerminal(TerminalNode terminalNode) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void visitErrorNode(ErrorNode errorNode) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterEveryRule(ParserRuleContext parserRuleContext) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitEveryRule(ParserRuleContext parserRuleContext) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
