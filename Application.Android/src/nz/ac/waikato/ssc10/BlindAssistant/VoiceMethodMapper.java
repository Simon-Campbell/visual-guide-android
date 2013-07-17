package nz.ac.waikato.ssc10.BlindAssistant;

import android.util.Log;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarLexer;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarListener;
import nz.ac.waikato.ssc10.grammar.VoiceInputGrammarParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 15/07/13
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
class VoiceMethodMapper {
    private static final String TAG = "VoiceMethodMapper";

    private Map<String, Method> voiceMethods = new HashMap<String, Method>();

    private class PlaceholderComparator implements Comparator<String> {
        public PlaceholderComparator() {
        }

        @Override
        public int compare(String s, String s2) {
            final Pattern pattern = Pattern.compile("\\{\\d\\}");
            final String[] literalGroups = pattern.split(s);

            int lastIndex = 0;
            int literalMatches = 0;

            // For each literal group, we want to check if it exists in 's2'
            // in order.
            for (int i = 0; i < literalGroups.length; i++) {
                int idx = s2.indexOf(literalGroups[i], lastIndex);

                if (idx != -1) {
                    literalMatches++;
                } else {

                }

                lastIndex = idx;
            }

            return (literalMatches - literalGroups.length);
        }
    }

    public VoiceMethodMapper() {
        try {
            voiceMethods.put("where am I", VoiceMethodMapper.class.getDeclaredMethod("SayLocation", BlindAssistant.class));
        } catch (NoSuchMethodException ex) {
            Log.e(TAG, ex.getMessage());
        }

        try {
            voiceMethods.put("take me to {0}", VoiceMethodMapper.class.getDeclaredMethod("SayGibberish", BlindAssistant.class, String.class));
        } catch (NoSuchMethodException ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    public VoiceMethodMapper(InputStream is) throws IOException {
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

    public Method get(String input) {
        return voiceMethods.get(input);
    }

    public void SayLocation(BlindAssistant assistant) {
        assistant.say("you are at " + assistant.getLocationName());
    }

    public void SayGibberish(BlindAssistant assistant, String location) {
        assistant.say("taking you to " + location);
    }

    private class VoiceInputWalker implements VoiceInputGrammarListener {

        @Override
        public void enterEnd(VoiceInputGrammarParser.EndContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitEnd(VoiceInputGrammarParser.EndContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void enterKeyword(VoiceInputGrammarParser.KeywordContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitKeyword(VoiceInputGrammarParser.KeywordContext ctx) {
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
        public void enterStatement(VoiceInputGrammarParser.StatementContext ctx) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void exitStatement(VoiceInputGrammarParser.StatementContext ctx) {
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
