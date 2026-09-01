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

/* Macros (definiciones regulares) */
WhiteSpace = [ \t\r\n]+

%%

/* Reglas */
{WhiteSpace}   { /* los espacios en blanco no generan token */ }

[^]            { /* TODO: regla temporal del kickstart; reemplazar por las
                    reglas léxicas del TP (identificadores, constantes
                    numéricas y string, comentarios, palabras reservadas) */
                 return new java_cup.runtime.Symbol(sym.OTRO, yytext()); }
