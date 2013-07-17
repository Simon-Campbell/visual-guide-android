// Generated from VoiceInputGrammar.g4 by ANTLR 4.0
package nz.ac.waikato.ssc10.grammar;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VoiceInputGrammarParser extends Parser {
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, QUOTE = 2, QMARK = 3, LCURLY = 4, RCURLY = 5, HASH = 6, EQ = 7, USR_VAR = 8,
            SYS_VAR = 9, WORD = 10, TAB = 11, NL = 12, WS = 13, SET = 14, NAV = 15;
    public static final String[] tokenNames = {
            "<INVALID>", "'kw'", "'\"'", "'?'", "'{'", "'}'", "'#'", "'='", "USR_VAR",
            "SYS_VAR", "WORD", "'\t'", "NL", "WS", "SET", "NAV"
    };
    public static final int
            RULE_program = 0, RULE_comment = 1, RULE_keyword_stmt = 2, RULE_set_stmt = 3,
            RULE_nav_stmt = 4, RULE_post_keyword_stmt = 5;
    public static final String[] ruleNames = {
            "program", "comment", "keyword_stmt", "set_stmt", "nav_stmt", "post_keyword_stmt"
    };

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
    public ATN getATN() {
        return _ATN;
    }


    int count = 0;

    public VoiceInputGrammarParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ProgramContext extends ParserRuleContext {
        public Keyword_stmtContext keyword_stmt() {
            return getRuleContext(Keyword_stmtContext.class, 0);
        }

        public ProgramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_program;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).enterProgram(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).exitProgram(this);
        }
    }

    public final ProgramContext program() throws RecognitionException {
        ProgramContext _localctx = new ProgramContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_program);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(12);
                keyword_stmt();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class CommentContext extends ParserRuleContext {
        public TerminalNode HASH() {
            return getToken(VoiceInputGrammarParser.HASH, 0);
        }

        public TerminalNode NL() {
            return getToken(VoiceInputGrammarParser.NL, 0);
        }

        public CommentContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comment;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).enterComment(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).exitComment(this);
        }
    }

    public final CommentContext comment() throws RecognitionException {
        CommentContext _localctx = new CommentContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_comment);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(14);
                match(HASH);
                setState(18);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 0, _ctx);
                while (_alt != 1 && _alt != -1) {
                    if (_alt == 1 + 1) {
                        {
                            {
                                setState(15);
                                matchWildcard();
                            }
                        }
                    }
                    setState(20);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 0, _ctx);
                }
                setState(21);
                match(NL);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Keyword_stmtContext extends ParserRuleContext {
        public Keyword_stmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_keyword_stmt;
        }

        public Keyword_stmtContext() {
        }

        public void copyFrom(Keyword_stmtContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class KeywordContext extends Keyword_stmtContext {
        public TerminalNode WORD() {
            return getToken(VoiceInputGrammarParser.WORD, 0);
        }

        public List<TerminalNode> QUOTE() {
            return getTokens(VoiceInputGrammarParser.QUOTE);
        }

        public TerminalNode QMARK() {
            return getToken(VoiceInputGrammarParser.QMARK, 0);
        }

        public TerminalNode QUOTE(int i) {
            return getToken(VoiceInputGrammarParser.QUOTE, i);
        }

        public KeywordContext(Keyword_stmtContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).enterKeyword(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).exitKeyword(this);
        }
    }

    public final Keyword_stmtContext keyword_stmt() throws RecognitionException {
        Keyword_stmtContext _localctx = new Keyword_stmtContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_keyword_stmt);
        int _la;
        try {
            _localctx = new KeywordContext(_localctx);
            enterOuterAlt(_localctx, 1);
            {
                setState(23);
                match(1);
                setState(25);
                _la = _input.LA(1);
                if (_la == QMARK) {
                    {
                        setState(24);
                        match(QMARK);
                    }
                }

                setState(27);
                match(QUOTE);
                setState(28);
                match(WORD);
                setState(29);
                match(QUOTE);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Set_stmtContext extends ParserRuleContext {
        public Set_stmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_set_stmt;
        }

        public Set_stmtContext() {
        }

        public void copyFrom(Set_stmtContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class SetContext extends Set_stmtContext {
        public Post_keyword_stmtContext post_keyword_stmt() {
            return getRuleContext(Post_keyword_stmtContext.class, 0);
        }

        public TerminalNode SYS_VAR() {
            return getToken(VoiceInputGrammarParser.SYS_VAR, 0);
        }

        public TerminalNode SET() {
            return getToken(VoiceInputGrammarParser.SET, 0);
        }

        public TerminalNode EQ() {
            return getToken(VoiceInputGrammarParser.EQ, 0);
        }

        public TerminalNode QMARK() {
            return getToken(VoiceInputGrammarParser.QMARK, 0);
        }

        public TerminalNode USR_VAR() {
            return getToken(VoiceInputGrammarParser.USR_VAR, 0);
        }

        public SetContext(Set_stmtContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).enterSet(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).exitSet(this);
        }
    }

    public final Set_stmtContext set_stmt() throws RecognitionException {
        Set_stmtContext _localctx = new Set_stmtContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_set_stmt);
        int _la;
        try {
            _localctx = new SetContext(_localctx);
            enterOuterAlt(_localctx, 1);
            {
                setState(31);
                match(SET);
                setState(33);
                _la = _input.LA(1);
                if (_la == QMARK) {
                    {
                        setState(32);
                        match(QMARK);
                    }
                }

                setState(35);
                match(USR_VAR);
                setState(36);
                match(EQ);
                setState(37);
                match(SYS_VAR);
                setState(38);
                post_keyword_stmt();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Nav_stmtContext extends ParserRuleContext {
        public Nav_stmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nav_stmt;
        }

        public Nav_stmtContext() {
        }

        public void copyFrom(Nav_stmtContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class NavigateContext extends Nav_stmtContext {
        public TerminalNode WORD() {
            return getToken(VoiceInputGrammarParser.WORD, 0);
        }

        public Post_keyword_stmtContext post_keyword_stmt() {
            return getRuleContext(Post_keyword_stmtContext.class, 0);
        }

        public List<TerminalNode> QUOTE() {
            return getTokens(VoiceInputGrammarParser.QUOTE);
        }

        public TerminalNode NAV() {
            return getToken(VoiceInputGrammarParser.NAV, 0);
        }

        public TerminalNode QMARK() {
            return getToken(VoiceInputGrammarParser.QMARK, 0);
        }

        public TerminalNode USR_VAR() {
            return getToken(VoiceInputGrammarParser.USR_VAR, 0);
        }

        public TerminalNode QUOTE(int i) {
            return getToken(VoiceInputGrammarParser.QUOTE, i);
        }

        public NavigateContext(Nav_stmtContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).enterNavigate(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).exitNavigate(this);
        }
    }

    public final Nav_stmtContext nav_stmt() throws RecognitionException {
        Nav_stmtContext _localctx = new Nav_stmtContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_nav_stmt);
        int _la;
        try {
            _localctx = new NavigateContext(_localctx);
            enterOuterAlt(_localctx, 1);
            {
                setState(40);
                match(NAV);
                setState(42);
                _la = _input.LA(1);
                if (_la == QMARK) {
                    {
                        setState(41);
                        match(QMARK);
                    }
                }

                setState(48);
                switch (_input.LA(1)) {
                    case USR_VAR: {
                        setState(44);
                        match(USR_VAR);
                    }
                    break;
                    case QUOTE: {
                        setState(45);
                        match(QUOTE);
                        setState(46);
                        match(WORD);
                        setState(47);
                        match(QUOTE);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(50);
                post_keyword_stmt();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class Post_keyword_stmtContext extends ParserRuleContext {
        public Post_keyword_stmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_post_keyword_stmt;
        }

        public Post_keyword_stmtContext() {
        }

        public void copyFrom(Post_keyword_stmtContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class EndContext extends Post_keyword_stmtContext {
        public TerminalNode EOF() {
            return getToken(VoiceInputGrammarParser.EOF, 0);
        }

        public EndContext(Post_keyword_stmtContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).enterEnd(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener) ((VoiceInputGrammarListener) listener).exitEnd(this);
        }
    }

    public static class StatementContext extends Post_keyword_stmtContext {
        public List<TerminalNode> TAB() {
            return getTokens(VoiceInputGrammarParser.TAB);
        }

        public Nav_stmtContext nav_stmt() {
            return getRuleContext(Nav_stmtContext.class, 0);
        }

        public Keyword_stmtContext keyword_stmt() {
            return getRuleContext(Keyword_stmtContext.class, 0);
        }

        public List<TerminalNode> NL() {
            return getTokens(VoiceInputGrammarParser.NL);
        }

        public Set_stmtContext set_stmt() {
            return getRuleContext(Set_stmtContext.class, 0);
        }

        public TerminalNode TAB(int i) {
            return getToken(VoiceInputGrammarParser.TAB, i);
        }

        public TerminalNode NL(int i) {
            return getToken(VoiceInputGrammarParser.NL, i);
        }

        public StatementContext(Post_keyword_stmtContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).enterStatement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof VoiceInputGrammarListener)
                ((VoiceInputGrammarListener) listener).exitStatement(this);
        }
    }

    public final Post_keyword_stmtContext post_keyword_stmt() throws RecognitionException {
        Post_keyword_stmtContext _localctx = new Post_keyword_stmtContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_post_keyword_stmt);
        int _la;
        try {
            setState(68);
            switch (_input.LA(1)) {
                case NL:
                    _localctx = new StatementContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(53);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    do {
                        {
                            {
                                setState(52);
                                match(NL);
                            }
                        }
                        setState(55);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    } while (_la == NL);
                    setState(58);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    do {
                        {
                            {
                                setState(57);
                                match(TAB);
                            }
                        }
                        setState(60);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    } while (_la == TAB);
                    setState(65);
                    switch (_input.LA(1)) {
                        case 1: {
                            setState(62);
                            keyword_stmt();
                        }
                        break;
                        case SET: {
                            setState(63);
                            set_stmt();
                        }
                        break;
                        case NAV: {
                            setState(64);
                            nav_stmt();
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                }
                break;
                case EOF:
                    _localctx = new EndContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(67);
                    match(EOF);
                }
                break;
                default:
                    throw new NoViableAltException(this);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN =
            "\2\3\21I\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\3\3" +
                    "\3\7\3\23\n\3\f\3\16\3\26\13\3\3\3\3\3\3\4\3\4\5\4\34\n\4\3\4\3\4\3\4" +
                    "\3\4\3\5\3\5\5\5$\n\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\5\6-\n\6\3\6\3\6\3\6" +
                    "\3\6\5\6\63\n\6\3\6\3\6\3\7\6\78\n\7\r\7\16\79\3\7\6\7=\n\7\r\7\16\7>" +
                    "\3\7\3\7\3\7\5\7D\n\7\3\7\5\7G\n\7\3\7\3\24\b\2\4\6\b\n\f\2\2L\2\16\3" +
                    "\2\2\2\4\20\3\2\2\2\6\31\3\2\2\2\b!\3\2\2\2\n*\3\2\2\2\fF\3\2\2\2\16\17" +
                    "\5\6\4\2\17\3\3\2\2\2\20\24\7\b\2\2\21\23\13\2\2\2\22\21\3\2\2\2\23\26" +
                    "\3\2\2\2\24\25\3\2\2\2\24\22\3\2\2\2\25\27\3\2\2\2\26\24\3\2\2\2\27\30" +
                    "\7\16\2\2\30\5\3\2\2\2\31\33\7\3\2\2\32\34\7\5\2\2\33\32\3\2\2\2\33\34" +
                    "\3\2\2\2\34\35\3\2\2\2\35\36\7\4\2\2\36\37\7\f\2\2\37 \7\4\2\2 \7\3\2" +
                    "\2\2!#\7\20\2\2\"$\7\5\2\2#\"\3\2\2\2#$\3\2\2\2$%\3\2\2\2%&\7\n\2\2&\'" +
                    "\7\t\2\2\'(\7\13\2\2()\5\f\7\2)\t\3\2\2\2*,\7\21\2\2+-\7\5\2\2,+\3\2\2" +
                    "\2,-\3\2\2\2-\62\3\2\2\2.\63\7\n\2\2/\60\7\4\2\2\60\61\7\f\2\2\61\63\7" +
                    "\4\2\2\62.\3\2\2\2\62/\3\2\2\2\63\64\3\2\2\2\64\65\5\f\7\2\65\13\3\2\2" +
                    "\2\668\7\16\2\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2" +
                    "\2;=\7\r\2\2<;\3\2\2\2=>\3\2\2\2><\3\2\2\2>?\3\2\2\2?C\3\2\2\2@D\5\6\4" +
                    "\2AD\5\b\5\2BD\5\n\6\2C@\3\2\2\2CA\3\2\2\2CB\3\2\2\2DG\3\2\2\2EG\7\1\2" +
                    "\2F\67\3\2\2\2FE\3\2\2\2G\r\3\2\2\2\13\24\33#,\629>CF";
    public static final ATN _ATN =
            ATNSimulator.deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
    }
}