package nz.ac.waikato.ssc10.input;

import android.util.Log;
import nz.ac.waikato.ssc10.BlindAssistant.BlindAssistant;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarLexer;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarListener;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.javatuples.Pair;

import java.io.IOException;
import java.io.InputStream;
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

    private static VoiceMethod sayUserLocation = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.sayCurrentLocation();
        }
    };

    private static VoiceMethod stopNavigating = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.navigateTo(null);
        }
    };

    private static VoiceMethod navigateUser = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.navigateTo(arguments.get("{Destination}"));
        }
    };

    private static VoiceMethod sayCompassDirection = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.sayUserCompassDirection();
        }
    };

    private static VoiceMethod sayDistanceToNext = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.sayDistanceToNextPoint();
        }
    };

    private static VoiceMethod sayDistanceToFinal = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.say("say distance to final is not implemented");
        }
    };

    private static VoiceMethod sayInstruction = new VoiceMethod() {
        @Override
        public void invoke(BlindAssistant assistant, Map<String, String> arguments) {
            assistant.sayCurrentInstruction();
        }
    };

    private VoiceMethodFactory() {

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

    public static VoiceMethodFactory createStandardFactory() {
        VoiceMethodFactory voiceMethodFactory = new VoiceMethodFactory();
        VoiceMethodMap voiceMethods = voiceMethodFactory.voiceMethods;

        //--------------------------------------------------------------------------------------------------------------
        // sayUserLocation
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("where am I", sayUserLocation);

        //--------------------------------------------------------------------------------------------------------------
        // sayCompassDirection
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("what is my compass direction", sayCompassDirection);
        voiceMethods.put("what direction am I facing", sayCompassDirection);
        voiceMethods.put("where am I facing", sayCompassDirection);
        voiceMethods.put("what way am I facing", sayCompassDirection);

        //--------------------------------------------------------------------------------------------------------------
        // sayDistanceToFinal - TODO: Needs implementing
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("how far away am I from the final destination", sayDistanceToFinal);

        //--------------------------------------------------------------------------------------------------------------
        // sayDistanceToNext
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("how far away is the next destination", sayDistanceToNext);
        voiceMethods.put("how far away is the next step", sayDistanceToNext);
        voiceMethods.put("how far away am I from the next destination", sayDistanceToNext);
        voiceMethods.put("how far away am I from the next step", sayDistanceToNext);
        voiceMethods.put("what is the distance to the next destination", sayDistanceToNext);
        voiceMethods.put("what is the distance to the next step", sayDistanceToNext);

        //--------------------------------------------------------------------------------------------------------------
        // sayCurrentInstruction
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("repeat the last instruction", sayInstruction);
        voiceMethods.put("what is the last instruction", sayInstruction);
        voiceMethods.put("what is the current instruction", sayInstruction);

        //--------------------------------------------------------------------------------------------------------------
        // navigateUser
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("take me to {Destination}", navigateUser);
        voiceMethods.put("navigate to {Destination}", navigateUser);

        //--------------------------------------------------------------------------------------------------------------
        // stopNavigating
        //--------------------------------------------------------------------------------------------------------------
        voiceMethods.put("stop navigating", stopNavigating);
        voiceMethods.put("stop navigation", stopNavigating);

        return voiceMethodFactory;
    }

    public Pair<VoiceMethod, Map<String, String>> get(String input) {
        return voiceMethods.get(input);
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
