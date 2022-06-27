package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	private Symbol new_symbol(int type){
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value){
		return new Symbol(type, yyline+1, yycolumn, value);
	}
%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " { }
"\b" { }
"\t" { }
"\r\n" { }
"\f" { }

"//" { yybegin(COMMENT); }
<COMMENT> . { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

"program" { return new_symbol(sym.PROG, yytext()); }
"break" { return new_symbol(sym.BREAK, yytext()); }
"class" { return new_symbol(sym.CLASS, yytext()); }
"enum" { return new_symbol(sym.ENUM, yytext()); }
"else" { return new_symbol(sym.ELSE, yytext()); }
"const" { return new_symbol(sym.CONST, yytext()); }
"if" { return new_symbol(sym.IF, yytext()); }
"do" { return new_symbol(sym.DO, yytext()); }
"while" { return new_symbol(sym.WHILE, yytext()); }
"new" { return new_symbol(sym.NEW, yytext()); }
"print" { return new_symbol(sym.PRINT, yytext()); }
"read" { return new_symbol(sym.READ, yytext()); }
"return" { return new_symbol(sym.RETURN, yytext()); }
"void" { return new_symbol(sym.VOID, yytext()); }
"extends" { return new_symbol(sym.EXTENDS, yytext()); }
"continue" { return new_symbol(sym.CONTINUE, yytext()); }
"this" { return new_symbol(sym.THIS, yytext()); }
"super" { return new_symbol(sym.SUPER, yytext()); }
"goto" { return new_symbol(sym.GOTO, yytext()); }
"record" { return new_symbol(sym.RECORD, yytext()); }
"instanceof" { return new_symbol(sym.INSTANCEOF, yytext()); }


"++" { return new_symbol(sym.INC, yytext()); }
"--" { return new_symbol(sym.DEC, yytext()); }

"+" { return new_symbol(sym.PLUS, yytext()); }
"-" { return new_symbol(sym.MINUS, yytext()); }
"*" { return new_symbol(sym.MUL, yytext()); }
"/" { return new_symbol(sym.DIV, yytext()); }
"%" { return new_symbol(sym.MOD, yytext()); }
"==" { return new_symbol(sym.EQ, yytext()); }
"!=" { return new_symbol(sym.NE, yytext()); }
">" { return new_symbol(sym.GT, yytext()); }
">=" { return new_symbol(sym.GE, yytext()); }
"<" { return new_symbol(sym.LT, yytext()); }
"<=" { return new_symbol(sym.LE, yytext()); }
"&&" { return new_symbol(sym.AND, yytext()); }
"||" { return new_symbol(sym.OR, yytext()); }
"=" { return new_symbol(sym.EQUALS, yytext()); }
";" { return new_symbol(sym.SEMI, yytext()); }
":" { return new_symbol(sym.COLON, yytext()); }
"," { return new_symbol(sym.COMMA, yytext()); }
"." { return new_symbol(sym.DOT, yytext()); }
"(" { return new_symbol(sym.LPAREN, yytext()); }
")" { return new_symbol(sym.RPAREN, yytext()); }
"[" { return new_symbol(sym.LSQUARE, yytext()); }
"]" { return new_symbol(sym.RSQUARE, yytext()); }
"{" { return new_symbol(sym.LCURLY, yytext()); }
"}" { return new_symbol(sym.RCURLY, yytext()); }

"true" { return new_symbol(sym.BOOL, new Boolean(true)); }
"false" { return new_symbol(sym.BOOL, new Boolean(false)); }

"'"[\040-\176]"'" { return new_symbol(sym.CHAR, new Character(yytext().charAt(1))); }

[0-9]+ { return new_symbol(sym.NUMBER, new Integer(yytext())); }
[a-zA-Z][a-zA-Z0-9_]* { return new_symbol(sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska: (" + yytext() + ") na liniji " + (yyline+1) + " i koloni " + yycolumn); }