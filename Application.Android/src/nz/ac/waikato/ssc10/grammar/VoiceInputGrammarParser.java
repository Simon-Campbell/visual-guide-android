// Generated from VoiceInputGrammar.g4 by ANTLR 4.0
package nz.ac.waikato.ssc10.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VoiceInputGrammarParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		QUOTE=1, QUOTED_WORD=2, QMARK=3, LCURLY=4, RCURLY=5, COMMA=6, HASH=7, 
		EQ=8, USR_VAR=9, SYS_VAR=10, VAR=11, WORD=12, TAB=13, NL=14, WS=15, KW=16, 
		SAY=17, SET=18, NAV=19;
	public static final String[] tokenNames = {
		"<INVALID>", "'\"'", "QUOTED_WORD", "'?'", "'{'", "'}'", "','", "'#'", 
		"'='", "USR_VAR", "SYS_VAR", "VAR", "WORD", "'\t'", "NL", "WS", "KW", 
		"SAY", "SET", "NAV"
	};
	public static final int
		RULE_program = 0, RULE_keyword_stmt = 1, RULE_stmt = 2, RULE_say_stmt = 3, 
		RULE_set_stmt = 4, RULE_nav_stmt = 5, RULE_comment = 6;
	public static final String[] ruleNames = {
		"program", "keyword_stmt", "stmt", "say_stmt", "set_stmt", "nav_stmt", 
		"comment"
	};

	@Override
	public String getGrammarFileName() { return "VoiceInputGrammar.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }


	    int count = 0;

	public VoiceInputGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public Keyword_stmtContext keyword_stmt(int i) {
			return getRuleContext(Keyword_stmtContext.class,i);
		}
		public List<Keyword_stmtContext> keyword_stmt() {
			return getRuleContexts(Keyword_stmtContext.class);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==KW) {
				{
				{
				setState(14); keyword_stmt();
				}
				}
				setState(19);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Keyword_stmtContext extends ParserRuleContext {
		public Keyword_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyword_stmt; }
	 
		public Keyword_stmtContext() { }
		public void copyFrom(Keyword_stmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class KeywordOperationContext extends Keyword_stmtContext {
		public TerminalNode QUOTED_WORD(int i) {
			return getToken(VoiceInputGrammarParser.QUOTED_WORD, i);
		}
		public List<TerminalNode> QUOTED_WORD() { return getTokens(VoiceInputGrammarParser.QUOTED_WORD); }
		public List<TerminalNode> LCURLY() { return getTokens(VoiceInputGrammarParser.LCURLY); }
		public TerminalNode COMMA(int i) {
			return getToken(VoiceInputGrammarParser.COMMA, i);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public TerminalNode KW() { return getToken(VoiceInputGrammarParser.KW, 0); }
		public List<TerminalNode> COMMA() { return getTokens(VoiceInputGrammarParser.COMMA); }
		public TerminalNode RCURLY(int i) {
			return getToken(VoiceInputGrammarParser.RCURLY, i);
		}
		public TerminalNode LCURLY(int i) {
			return getToken(VoiceInputGrammarParser.LCURLY, i);
		}
		public TerminalNode QMARK() { return getToken(VoiceInputGrammarParser.QMARK, 0); }
		public List<TerminalNode> RCURLY() { return getTokens(VoiceInputGrammarParser.RCURLY); }
		public KeywordOperationContext(Keyword_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterKeywordOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitKeywordOperation(this);
		}
	}

	public final Keyword_stmtContext keyword_stmt() throws RecognitionException {
		Keyword_stmtContext _localctx = new Keyword_stmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_keyword_stmt);
		int _la;
		try {
			_localctx = new KeywordOperationContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(20); match(KW);
			setState(22);
			_la = _input.LA(1);
			if (_la==QMARK) {
				{
				setState(21); match(QMARK);
				}
			}

			setState(24); match(QUOTED_WORD);
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(25); match(COMMA);
				setState(26); match(QUOTED_WORD);
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LCURLY) {
				{
				{
				setState(32); match(LCURLY);
				setState(33); stmt();
				setState(34); match(RCURLY);
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public Nav_stmtContext nav_stmt() {
			return getRuleContext(Nav_stmtContext.class,0);
		}
		public Keyword_stmtContext keyword_stmt() {
			return getRuleContext(Keyword_stmtContext.class,0);
		}
		public Set_stmtContext set_stmt() {
			return getRuleContext(Set_stmtContext.class,0);
		}
		public Say_stmtContext say_stmt() {
			return getRuleContext(Say_stmtContext.class,0);
		}
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitStmt(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			switch (_input.LA(1)) {
			case KW:
				{
				setState(41); keyword_stmt();
				}
				break;
			case SET:
				{
				setState(42); set_stmt();
				}
				break;
			case NAV:
				{
				setState(43); nav_stmt();
				}
				break;
			case SAY:
				{
				setState(44); say_stmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Say_stmtContext extends ParserRuleContext {
		public Say_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_say_stmt; }
	 
		public Say_stmtContext() { }
		public void copyFrom(Say_stmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SayContext extends Say_stmtContext {
		public TerminalNode VAR() { return getToken(VoiceInputGrammarParser.VAR, 0); }
		public TerminalNode SAY() { return getToken(VoiceInputGrammarParser.SAY, 0); }
		public TerminalNode QMARK() { return getToken(VoiceInputGrammarParser.QMARK, 0); }
		public SayContext(Say_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterSay(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitSay(this);
		}
	}

	public final Say_stmtContext say_stmt() throws RecognitionException {
		Say_stmtContext _localctx = new Say_stmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_say_stmt);
		int _la;
		try {
			_localctx = new SayContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(47); match(SAY);
			setState(49);
			_la = _input.LA(1);
			if (_la==QMARK) {
				{
				setState(48); match(QMARK);
				}
			}

			setState(51); match(VAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Set_stmtContext extends ParserRuleContext {
		public Set_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_set_stmt; }
	 
		public Set_stmtContext() { }
		public void copyFrom(Set_stmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SetContext extends Set_stmtContext {
		public TerminalNode SYS_VAR() { return getToken(VoiceInputGrammarParser.SYS_VAR, 0); }
		public TerminalNode SET() { return getToken(VoiceInputGrammarParser.SET, 0); }
		public TerminalNode EQ() { return getToken(VoiceInputGrammarParser.EQ, 0); }
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public TerminalNode QMARK() { return getToken(VoiceInputGrammarParser.QMARK, 0); }
		public TerminalNode USR_VAR() { return getToken(VoiceInputGrammarParser.USR_VAR, 0); }
		public SetContext(Set_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitSet(this);
		}
	}

	public final Set_stmtContext set_stmt() throws RecognitionException {
		Set_stmtContext _localctx = new Set_stmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_set_stmt);
		int _la;
		try {
			_localctx = new SetContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(53); match(SET);
			setState(55);
			_la = _input.LA(1);
			if (_la==QMARK) {
				{
				setState(54); match(QMARK);
				}
			}

			setState(57); match(USR_VAR);
			setState(58); match(EQ);
			setState(59); match(SYS_VAR);
			setState(60); stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Nav_stmtContext extends ParserRuleContext {
		public Nav_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nav_stmt; }
	 
		public Nav_stmtContext() { }
		public void copyFrom(Nav_stmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NavigateContext extends Nav_stmtContext {
		public TerminalNode WORD() { return getToken(VoiceInputGrammarParser.WORD, 0); }
		public List<TerminalNode> QUOTE() { return getTokens(VoiceInputGrammarParser.QUOTE); }
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public TerminalNode NAV() { return getToken(VoiceInputGrammarParser.NAV, 0); }
		public TerminalNode QMARK() { return getToken(VoiceInputGrammarParser.QMARK, 0); }
		public TerminalNode USR_VAR() { return getToken(VoiceInputGrammarParser.USR_VAR, 0); }
		public TerminalNode QUOTE(int i) {
			return getToken(VoiceInputGrammarParser.QUOTE, i);
		}
		public NavigateContext(Nav_stmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterNavigate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitNavigate(this);
		}
	}

	public final Nav_stmtContext nav_stmt() throws RecognitionException {
		Nav_stmtContext _localctx = new Nav_stmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nav_stmt);
		int _la;
		try {
			_localctx = new NavigateContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(62); match(NAV);
			setState(64);
			_la = _input.LA(1);
			if (_la==QMARK) {
				{
				setState(63); match(QMARK);
				}
			}

			setState(70);
			switch (_input.LA(1)) {
			case USR_VAR:
				{
				setState(66); match(USR_VAR);
				}
				break;
			case QUOTE:
				{
				setState(67); match(QUOTE);
				setState(68); match(WORD);
				setState(69); match(QUOTE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(72); stmt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentContext extends ParserRuleContext {
		public TerminalNode HASH() { return getToken(VoiceInputGrammarParser.HASH, 0); }
		public TerminalNode NL() { return getToken(VoiceInputGrammarParser.NL, 0); }
		public CommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).enterComment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VoiceInputGrammarListener ) ((VoiceInputGrammarListener)listener).exitComment(this);
		}
	}

	public final CommentContext comment() throws RecognitionException {
		CommentContext _localctx = new CommentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_comment);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(74); match(HASH);
			setState(78);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=1 && _alt!=-1 ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(75);
					matchWildcard();
					}
					} 
				}
				setState(80);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(81); match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3\25V\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\7"+
		"\2\22\n\2\f\2\16\2\25\13\2\3\3\3\3\5\3\31\n\3\3\3\3\3\3\3\7\3\36\n\3\f"+
		"\3\16\3!\13\3\3\3\3\3\3\3\3\3\7\3\'\n\3\f\3\16\3*\13\3\3\4\3\4\3\4\3\4"+
		"\5\4\60\n\4\3\5\3\5\5\5\64\n\5\3\5\3\5\3\6\3\6\5\6:\n\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\5\7C\n\7\3\7\3\7\3\7\3\7\5\7I\n\7\3\7\3\7\3\b\3\b\7\bO"+
		"\n\b\f\b\16\bR\13\b\3\b\3\b\3\b\3P\t\2\4\6\b\n\f\16\2\2Z\2\23\3\2\2\2"+
		"\4\26\3\2\2\2\6/\3\2\2\2\b\61\3\2\2\2\n\67\3\2\2\2\f@\3\2\2\2\16L\3\2"+
		"\2\2\20\22\5\4\3\2\21\20\3\2\2\2\22\25\3\2\2\2\23\21\3\2\2\2\23\24\3\2"+
		"\2\2\24\3\3\2\2\2\25\23\3\2\2\2\26\30\7\22\2\2\27\31\7\5\2\2\30\27\3\2"+
		"\2\2\30\31\3\2\2\2\31\32\3\2\2\2\32\37\7\4\2\2\33\34\7\b\2\2\34\36\7\4"+
		"\2\2\35\33\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 (\3\2\2\2!\37"+
		"\3\2\2\2\"#\7\6\2\2#$\5\6\4\2$%\7\7\2\2%\'\3\2\2\2&\"\3\2\2\2\'*\3\2\2"+
		"\2(&\3\2\2\2()\3\2\2\2)\5\3\2\2\2*(\3\2\2\2+\60\5\4\3\2,\60\5\n\6\2-\60"+
		"\5\f\7\2.\60\5\b\5\2/+\3\2\2\2/,\3\2\2\2/-\3\2\2\2/.\3\2\2\2\60\7\3\2"+
		"\2\2\61\63\7\23\2\2\62\64\7\5\2\2\63\62\3\2\2\2\63\64\3\2\2\2\64\65\3"+
		"\2\2\2\65\66\7\r\2\2\66\t\3\2\2\2\679\7\24\2\28:\7\5\2\298\3\2\2\29:\3"+
		"\2\2\2:;\3\2\2\2;<\7\13\2\2<=\7\n\2\2=>\7\f\2\2>?\5\6\4\2?\13\3\2\2\2"+
		"@B\7\25\2\2AC\7\5\2\2BA\3\2\2\2BC\3\2\2\2CH\3\2\2\2DI\7\13\2\2EF\7\3\2"+
		"\2FG\7\16\2\2GI\7\3\2\2HD\3\2\2\2HE\3\2\2\2IJ\3\2\2\2JK\5\6\4\2K\r\3\2"+
		"\2\2LP\7\t\2\2MO\13\2\2\2NM\3\2\2\2OR\3\2\2\2PQ\3\2\2\2PN\3\2\2\2QS\3"+
		"\2\2\2RP\3\2\2\2ST\7\20\2\2T\17\3\2\2\2\f\23\30\37(/\639BHP";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}