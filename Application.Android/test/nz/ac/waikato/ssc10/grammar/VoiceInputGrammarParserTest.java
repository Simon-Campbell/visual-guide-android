package nz.ac.waikato.ssc10.grammar;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 14/07/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class VoiceInputGrammarParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParser() {
        VoiceInputGrammarLexer lexer = new VoiceInputGrammarLexer(new ANTLRInputStream("kw \"hi\""));
        CommonTokenStream cts = new CommonTokenStream(lexer);

        VoiceInputGrammarParser parser = new VoiceInputGrammarParser(cts);
        VoiceInputGrammarParser.ProgramContext program = parser.program();

        ParseTreeWalker.DEFAULT.walk(new TestTreeWalker(), program);
    }

    private class TestTreeWalker implements VoiceInputGrammarListener {
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
            System.out.println("Found a keyword with value: " + ctx.WORD().toString());
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
