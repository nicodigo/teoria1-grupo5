// ============================================================================
// Lexico.flex — especificación léxica del TP Compilador (Grupo 5 - SUMAIMPAR)
//
// Procesada por JFlex (jflex-maven-plugin 1.9.1) en la fase generate-sources.
// Genera: target/generated-sources/jflex/unlu/teoi/grupo5/lexer/Lexico.java
//
// El archivo generado NO se versiona y NO se edita: se edita este .flex
// y se recompila (./mvnw verify).
//
// TODO: reemplazar las reglas de la tercera sección por la gramática del TP.
// ============================================================================

// Primera sección: copiada tal cual al tope del archivo generado.
package unlu.teoi.grupo5.lexer;

import unlu.teoi.grupo5.parser.sym;
import java_cup.runtime.Symbol;

%%

/* Opciones y declaraciones de JFlex */
%class Lexico
%public
%unicode
/* Modo CUP: el lexer implementa java_cup.runtime.Scanner, escanea con
   next_token() y devuelve java_cup.runtime.Symbol. Los tokens se crean con
   new java_cup.runtime.Symbol(sym.X, ...) usando la interfaz sym generada
   por CUP (ver src/main/cup/Sintactico.cup); el EOF se maneja solo
   (new Symbol(sym.EOF)). */
%cupsym unlu.teoi.grupo5.parser.sym
%cup
%line
%column

/* Macros (definiciones regulares) */
LETRA = [a-zA-Z] 
DIGITO = [0-9]
ESPACIO = [ \t\f\n\r\n]+
ID = {LETRA} ({LETRA}|{DIGITO}|_)*
COMENTARIO = "//*" [^*] ~"*//"

/*Constantes*/
CTE_E = {DIGITO}+
CTE_F = ({DIGITO}+ "." {DIGITO}*) | ({DIGITO}* "." {DIGITO}+)
CTE_STRING =  \"[^\"]*\"


%%

/* Reglas */
"DECLARE.SECTION"    { return new Symbol(sym.DECLARE_SECTION, yyline, yycolumn, yytext()); }
"ENDDECLARE.SECTION" { return new Symbol(sym.ENDDECLARE_SECTION, yyline, yycolumn, yytext()); }
"PROGRAM.SECTION"    { return new Symbol(sym.PROGRAM_SECTION, yyline, yycolumn, yytext()); }
"ENDPROGRAM.SECTION" { return new Symbol(sym.ENDPROGRAM_SECTION, yyline, yycolumn, yytext()); }
"IF"                 { return new Symbol(sym.IF, yyline, yycolumn, yytext()); }
"ELSE"               { return new Symbol(sym.ELSE, yyline, yycolumn, yytext()); }
"WHILE"              { return new Symbol(sym.WHILE, yyline, yycolumn, yytext()); }
"WRITE"              { return new Symbol(sym.WRITE, yyline, yycolumn, yytext()); }
"FLOAT"              { return new Symbol(sym.FLOAT, yyline, yycolumn, yytext()); }
"INT"            { return new Symbol(sym.INTEGER, yyline, yycolumn, yytext()); }
"STRING"             { return new Symbol(sym.STRING, yyline, yycolumn, yytext()); }
"SUMAIMPAR"          { return new Symbol(sym.SUMAIMPAR, yyline, yycolumn, yytext()); }


/* Operadores Lógicos y Relacionales */
"AND"                { return new Symbol(sym.AND, yyline, yycolumn, yytext()); }
"OR"                 { return new Symbol(sym.OR, yyline, yycolumn, yytext()); }
">="                 { return new Symbol(sym.MAYORIGUAL, yyline, yycolumn, yytext()); }
"<="                 { return new Symbol(sym.MENORIGUAL, yyline, yycolumn, yytext()); }
"!="                 { return new Symbol(sym.DISTINTO, yyline, yycolumn, yytext()); }
">"                  { return new Symbol(sym.MAYOR, yyline, yycolumn, yytext()); }
"<"                  { return new Symbol(sym.MENOR, yyline, yycolumn, yytext()); }

/* Operadores Aritméticos y Asignación */
"::="                { return new Symbol(sym.ASIGNACION, yyline, yycolumn, yytext()); }
"+"                  { return new Symbol(sym.SUMA, yyline, yycolumn, yytext()); }
"-"                  { return new Symbol(sym.RESTA, yyline, yycolumn, yytext()); }
"*"                  { return new Symbol(sym.MULTIPLICACION, yyline, yycolumn, yytext()); }
"/"                  { return new Symbol(sym.DIVISION, yyline, yycolumn, yytext()); }

/* Símbolos de Agrupación y Puntuación */
"("                  { return new Symbol(sym.PARA, yyline, yycolumn, yytext()); }
")"                  { return new Symbol(sym.PARC, yyline, yycolumn, yytext()); }
"{"                  { return new Symbol(sym.LLAVEA, yyline, yycolumn, yytext()); }
"}"                  { return new Symbol(sym.LLAVEC, yyline, yycolumn, yytext()); }
"["                  { return new Symbol(sym.CORCHETEA, yyline, yycolumn, yytext()); }
"]"                  { return new Symbol(sym.CORCHETEC, yyline, yycolumn, yytext()); }
";"                  { return new Symbol(sym.PUNTOYCOMA, yyline, yycolumn, yytext()); }
","                  { return new Symbol(sym.COMA, yyline, yycolumn, yytext()); }




{ID} { return new Symbol(sym.ID, yyline, yycolumn, yytext()); }
{CTE_E}              { return new Symbol(sym.CTE_E, yyline, yycolumn, yytext()); }
{CTE_F}              { return new Symbol(sym.CTE_F, yyline, yycolumn, yytext()); }
{CTE_STRING}         { return new Symbol(sym.CTE_STRING, yyline, yycolumn, yytext()); }
{COMENTARIO}	{/* No se realiza accion por lo tanto se ignoran*/}
{ESPACIO}   { /* los espacios en blanco no generan token */ }

[^]             { throw new Error("Caracter no permitido: <" + yytext() + "> en linea " + yyline); }
