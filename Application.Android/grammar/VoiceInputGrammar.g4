grammar VoiceInputGrammar;

tokens 
{
    KW,
    SAY,
    SET,
    NAV
}

@parser::members {
    int count = 0;
}

@lexer::members {
    java.util.Map<String, Integer> keywords = new java.util.HashMap<String, Integer>() {{
        put("kw", VoiceInputGrammarParser.KW);
        put("say", VoiceInputGrammarParser.SAY);
        put("set", VoiceInputGrammarParser.SET);
        put("nav", VoiceInputGrammarParser.NAV);
    }};                                                                
}

program:
    keyword_stmt* ;

keyword_stmt:
	KW QMARK? QUOTED_WORD (COMMA QUOTED_WORD)* (LCURLY stmt RCURLY)* # KeywordOperation;

stmt:
    (keyword_stmt | set_stmt | nav_stmt | say_stmt);

say_stmt:
    SAY QMARK? VAR

set_stmt:
	SET QMARK? USR_VAR EQ SYS_VAR post_keyword_stmt # Set;
	
nav_stmt:
	NAV QMARK? (USR_VAR | QUOTE WORD QUOTE) post_keyword_stmt # Navigate;

comment: HASH (.)*? NL;

QUOTE: '"';
QUOTED_WORD: QUOTE WORD QUOTE;

QMARK: '?';
LCURLY: '{';
RCURLY: '}';
COMMA:  ',';
HASH: '#';
EQ: '=';
USR_VAR: '$' WORD;
SYS_VAR: '@' ( 'SPEECH' | 'LOCATION' );

VAR: USR_VAR | SYS_VAR

WORD : [A-Za-z]+ ;          // match lower-case identifiers
TAB	 : '\t';
NL   : [\r\n]+;
WS   : [ \r\n]+ -> skip ; 	// skip spaces, newlines