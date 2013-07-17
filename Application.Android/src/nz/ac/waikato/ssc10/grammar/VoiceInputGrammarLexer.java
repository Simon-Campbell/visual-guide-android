// Generated from VoiceInputGrammar.g4 by ANTLR 4.0
package nz.ac.waikato.ssc10.grammar;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VoiceInputGrammarLexer extends Lexer {
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, QUOTE = 2, QMARK = 3, LCURLY = 4, RCURLY = 5, HASH = 6, EQ = 7, USR_VAR = 8,
            SYS_VAR = 9, WORD = 10, TAB = 11, NL = 12, WS = 13;
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    public static final String[] tokenNames = {
            "<INVALID>",
            "'kw'", "'\"'", "'?'", "'{'", "'}'", "'#'", "'='", "USR_VAR", "SYS_VAR",
            "WORD", "'\t'", "NL", "WS"
    };
    public static final String[] ruleNames = {
            "T__0", "QUOTE", "QMARK", "LCURLY", "RCURLY", "HASH", "EQ", "USR_VAR",
            "SYS_VAR", "WORD", "TAB", "NL", "WS"
    };


    java.util.Map<String, Integer> keywords = new java.util.HashMap<String, Integer>() {{
        //put("kw", VoiceInputGrammarParser.KW);
        put("set", VoiceInputGrammarParser.SET);
        put("nav", VoiceInputGrammarParser.NAV);
    }};


    public VoiceInputGrammarLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
        return "VoiceInputGrammar.g4";
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    @Override
    public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
        switch (ruleIndex) {
            case 12:
                WS_action((RuleContext) _localctx, actionIndex);
                break;
        }
    }

    private void WS_action(RuleContext _localctx, int actionIndex) {
        switch (actionIndex) {
            case 0:
                skip();
                break;
        }
    }

    public static final String _serializedATN =
            "\2\4\17S\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4" +
                    "\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\3\3\3" +
                    "\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3" +
                    "\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n?\n\n\3\13\6\13B\n\13\r" +
                    "\13\16\13C\3\f\3\f\3\r\6\rI\n\r\r\r\16\rJ\3\16\6\16N\n\16\r\16\16\16O" +
                    "\3\16\3\16\2\17\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23" +
                    "\13\1\25\f\1\27\r\1\31\16\1\33\17\2\3\2\5\4C\\c|\4\f\f\17\17\5\f\f\17" +
                    "\17\"\"V\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2" +
                    "\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
                    "\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5 \3\2\2\2\7\"\3\2\2\2" +
                    "\t$\3\2\2\2\13&\3\2\2\2\r(\3\2\2\2\17*\3\2\2\2\21,\3\2\2\2\23/\3\2\2\2" +
                    "\25A\3\2\2\2\27E\3\2\2\2\31H\3\2\2\2\33M\3\2\2\2\35\36\7m\2\2\36\37\7" +
                    "y\2\2\37\4\3\2\2\2 !\7$\2\2!\6\3\2\2\2\"#\7A\2\2#\b\3\2\2\2$%\7}\2\2%" +
                    "\n\3\2\2\2&\'\7\177\2\2\'\f\3\2\2\2()\7%\2\2)\16\3\2\2\2*+\7?\2\2+\20" +
                    "\3\2\2\2,-\7&\2\2-.\5\25\13\2.\22\3\2\2\2/>\7B\2\2\60\61\7U\2\2\61\62" +
                    "\7R\2\2\62\63\7G\2\2\63\64\7G\2\2\64\65\7E\2\2\65?\7J\2\2\66\67\7N\2\2" +
                    "\678\7Q\2\289\7E\2\29:\7C\2\2:;\7V\2\2;<\7K\2\2<=\7Q\2\2=?\7P\2\2>\60" +
                    "\3\2\2\2>\66\3\2\2\2?\24\3\2\2\2@B\t\2\2\2A@\3\2\2\2BC\3\2\2\2CA\3\2\2" +
                    "\2CD\3\2\2\2D\26\3\2\2\2EF\7\13\2\2F\30\3\2\2\2GI\t\3\2\2HG\3\2\2\2IJ" +
                    "\3\2\2\2JH\3\2\2\2JK\3\2\2\2K\32\3\2\2\2LN\t\4\2\2ML\3\2\2\2NO\3\2\2\2" +
                    "OM\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QR\b\16\2\2R\34\3\2\2\2\7\2>CJO";
    public static final ATN _ATN =
            ATNSimulator.deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    }
}