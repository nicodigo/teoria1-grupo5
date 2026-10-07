// ============================================================================
// Lexico.flex — especificación léxica del TP Compilador (Grupo 5 - SUMAIMPAR)
//
// Procesada por JFlex (jflex-maven-plugin 1.9.1) en la fase generate-sources.
// Genera: target/generated-sources/jflex/unlu/teoi/grupo5/lexer/Lexico.java
//
// El archivo generado NO se versiona y NO se edita: se edita este .flex
// y se recompila (./mvnw verify).


// Primera sección: 
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

%{
/* Código Java embebido para realizar los controles de bits y caracteres */

    private void validarString(String texto) {
        // yytext() incluye las comillas, por eso restamos 2
        if (texto.length() - 2 > 30) {
            throw new Error("Error Lexico: El string supera los 30 caracteres en la linea " + (yyline + 1));
        }
    }

    private void validarEntero16Bits(String texto) {
        try {
            int valor = Integer.parseInt(texto);
            // El valor máximo para un entero con signo de 16 bits es 32767
            if (valor > 32767) { 
                throw new Error("Error Lexico: El entero supera el limite de 16 bits en la linea " + (yyline + 1));
            }
        } catch (NumberFormatException e) {
            // Si salta esta excepción, el número es tan grande que ni siquiera entra en un int normal
            throw new Error("Error Lexico: El entero supera el limite de 16 bits en la linea " + (yyline + 1));
        }
    }

    private void validarReal32Bits(String texto) {
        float valor = Float.parseFloat(texto);
      if (Float.isInfinite(valor)) {
        throw new Error("Error Lexico: El real supera el limite de 32 bits en la linea " + (yyline + 1));
      }
    }
%}

/* Macros (definiciones regulares) */
LETRA = [a-zA-Z] 
DIGITO = [0-9]
ESPACIO = [ \t\f\n\r\n]+
ID = {LETRA} ({LETRA}|{DIGITO}|_)*
TEXTO_COMENT = !([^]* ("//*" | "*//") [^]*)
COMENTARIO_INTERNO = "//*" {TEXTO_COMENT} "*//"
COMENTARIO = "//*" ({TEXTO_COMENT} | {COMENTARIO_INTERNO})* "*//"

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
"INT"                { return new Symbol(sym.INT, yyline, yycolumn, yytext()); }
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
{CTE_E}              { validarEntero16Bits(yytext()); return new Symbol(sym.CTE_E, yyline, yycolumn, yytext()); }
{CTE_F}              { validarReal32Bits(yytext());   return new Symbol(sym.CTE_F, yyline, yycolumn, yytext()); }
{CTE_STRING}         { validarString(yytext());       return new Symbol(sym.CTE_STRING, yyline, yycolumn, yytext()); }
{COMENTARIO}	{/* No se realiza accion por lo tanto se ignoran*/}
{ESPACIO}   { /* los espacios en blanco no generan token */ }

[^]             { throw new Error("Caracter no permitido: <" + yytext() + "> en linea " + (yyline + 1)); }
