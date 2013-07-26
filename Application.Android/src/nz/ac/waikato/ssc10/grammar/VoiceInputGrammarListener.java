// Generated from VoiceInputGrammar.g4 by ANTLR 4.0
package nz.ac.waikato.ssc10.grammar;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface VoiceInputGrammarListener extends ParseTreeListener {
	void enterSay(VoiceInputGrammarParser.SayContext ctx);
	void exitSay(VoiceInputGrammarParser.SayContext ctx);

	void enterProgram(VoiceInputGrammarParser.ProgramContext ctx);
	void exitProgram(VoiceInputGrammarParser.ProgramContext ctx);

	void enterNavigate(VoiceInputGrammarParser.NavigateContext ctx);
	void exitNavigate(VoiceInputGrammarParser.NavigateContext ctx);

	void enterStmt(VoiceInputGrammarParser.StmtContext ctx);
	void exitStmt(VoiceInputGrammarParser.StmtContext ctx);

	void enterKeywordOperation(VoiceInputGrammarParser.KeywordOperationContext ctx);
	void exitKeywordOperation(VoiceInputGrammarParser.KeywordOperationContext ctx);

	void enterSet(VoiceInputGrammarParser.SetContext ctx);
	void exitSet(VoiceInputGrammarParser.SetContext ctx);

	void enterComment(VoiceInputGrammarParser.CommentContext ctx);
	void exitComment(VoiceInputGrammarParser.CommentContext ctx);
}