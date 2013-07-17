// Generated from VoiceInputGrammar.g4 by ANTLR 4.0
package nz.ac.waikato.ssc10.grammar;

import org.antlr.v4.runtime.tree.*;

public interface VoiceInputGrammarListener extends ParseTreeListener {
    void enterEnd(VoiceInputGrammarParser.EndContext ctx);

    void exitEnd(VoiceInputGrammarParser.EndContext ctx);

    void enterKeyword(VoiceInputGrammarParser.KeywordContext ctx);

    void exitKeyword(VoiceInputGrammarParser.KeywordContext ctx);

    void enterProgram(VoiceInputGrammarParser.ProgramContext ctx);

    void exitProgram(VoiceInputGrammarParser.ProgramContext ctx);

    void enterStatement(VoiceInputGrammarParser.StatementContext ctx);

    void exitStatement(VoiceInputGrammarParser.StatementContext ctx);

    void enterNavigate(VoiceInputGrammarParser.NavigateContext ctx);

    void exitNavigate(VoiceInputGrammarParser.NavigateContext ctx);

    void enterSet(VoiceInputGrammarParser.SetContext ctx);

    void exitSet(VoiceInputGrammarParser.SetContext ctx);

    void enterComment(VoiceInputGrammarParser.CommentContext ctx);

    void exitComment(VoiceInputGrammarParser.CommentContext ctx);
}