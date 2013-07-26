// Generated from VoiceInputGrammar.g4 by ANTLR 4.0
package nz.ac.waikato.ssc10.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VoiceInputGrammarLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		QUOTE=1, QUOTED_WORD=2, QMARK=3, LCURLY=4, RCURLY=5, COMMA=6, HASH=7, 
		EQ=8, USR_VAR=9, SYS_VAR=10, VAR=11, WORD=12, TAB=13, NL=14, WS=15;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'\"'", "QUOTED_WORD", "'?'", "'{'", "'}'", "','", "'#'", "'='", "USR_VAR", 
		"SYS_VAR", "VAR", "WORD", "'\t'", "NL", "WS"
	};
	public static final String[] ruleNames = {
		"QUOTE", "QUOTED_WORD", "QMARK", "LCURLY", "RCURLY", "COMMA", "HASH", 
		"EQ", "USR_VAR", "SYS_VAR", "VAR", "WORD", "TAB", "NL", "WS"
	};


	    java.util.Map<String, Integer> keywords = new java.util.HashMap<String, Integer>() {{
	        put("kw", VoiceInputGrammarParser.KW);
	        put("say", VoiceInputGrammarParser.SAY);
	        put("set", VoiceInputGrammarParser.SET);
	        put("nav", VoiceInputGrammarParser.NAV);
	    }};                                                                


	public VoiceInputGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "VoiceInputGrammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 14: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\21^\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\5\13F\n\13\3\f\3\f\5\fJ\n\f\3\r\6\rM\n\r\r\r\16\r"+
		"N\3\16\3\16\3\17\6\17T\n\17\r\17\16\17U\3\20\6\20Y\n\20\r\20\16\20Z\3"+
		"\20\3\20\2\21\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13"+
		"\1\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\2\3\2\5\4C\\c|\4\f\f\17"+
		"\17\5\f\f\17\17\"\"b\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\3!\3\2\2\2\5#\3\2\2\2\7\'\3\2\2\2\t)\3\2\2\2\13+\3\2\2\2\r-\3\2\2\2"+
		"\17/\3\2\2\2\21\61\3\2\2\2\23\63\3\2\2\2\25\66\3\2\2\2\27I\3\2\2\2\31"+
		"L\3\2\2\2\33P\3\2\2\2\35S\3\2\2\2\37X\3\2\2\2!\"\7$\2\2\"\4\3\2\2\2#$"+
		"\5\3\2\2$%\5\31\r\2%&\5\3\2\2&\6\3\2\2\2\'(\7A\2\2(\b\3\2\2\2)*\7}\2\2"+
		"*\n\3\2\2\2+,\7\177\2\2,\f\3\2\2\2-.\7.\2\2.\16\3\2\2\2/\60\7%\2\2\60"+
		"\20\3\2\2\2\61\62\7?\2\2\62\22\3\2\2\2\63\64\7&\2\2\64\65\5\31\r\2\65"+
		"\24\3\2\2\2\66E\7B\2\2\678\7U\2\289\7R\2\29:\7G\2\2:;\7G\2\2;<\7E\2\2"+
		"<F\7J\2\2=>\7N\2\2>?\7Q\2\2?@\7E\2\2@A\7C\2\2AB\7V\2\2BC\7K\2\2CD\7Q\2"+
		"\2DF\7P\2\2E\67\3\2\2\2E=\3\2\2\2F\26\3\2\2\2GJ\5\23\n\2HJ\5\25\13\2I"+
		"G\3\2\2\2IH\3\2\2\2J\30\3\2\2\2KM\t\2\2\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2"+
		"\2NO\3\2\2\2O\32\3\2\2\2PQ\7\13\2\2Q\34\3\2\2\2RT\t\3\2\2SR\3\2\2\2TU"+
		"\3\2\2\2US\3\2\2\2UV\3\2\2\2V\36\3\2\2\2WY\t\4\2\2XW\3\2\2\2YZ\3\2\2\2"+
		"ZX\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\]\b\20\2\2] \3\2\2\2\b\2EINUZ";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}