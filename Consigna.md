Teoría  de la Computación  I - UNLu **2026** Trabajo Práctico Integrador Compilador 

## **<u>TRABAJO PRÁCTICO COMPILADOR</u>** 

#### **CONSIDERACIONES GENERALES** 

Es necesario cumplir con las siguientes consideraciones para evaluar el TP. 

1. Cada grupo deberá desarrollar el compilador teniendo en cuenta: 

   - Todos los temas comunes (Ver ANEXO TEMAS) 

   - El tema especial asignado al grupo. 

2. Se fijarán puntos de control con fechas y consignas determinadas 

#### **<u>PRIMERA ENTREGA</u>** 

**OBJETIVO** : Realizar un **analizador lexicográfico** utilizando la herramienta JFLEX. La aplicación realizada debe mostrar una interfaz gráfica que pueda utilizarse como IDE del compilador, en la cual se debe poder: 

1. Ingresar código de nuestro programa manualmente en un cuadro de texto adecuado a tal propósito, por ejemplo dentro del lenguaje Java JTextArea. 

2. Cargar un archivo con código y poder editarlo dentro del cuadro de texto. 

3. Compilar el programa ingresado (análisis léxico) y mostrar un texto aclaratorio, identificando los tokens reconocidos por el parser u errores encontrados en el análisis. Las impresiones deben ser claras. Los elementos que no generan tokens no deben generar salida. 

El material a entregar será: 

- El archivo jflex que se llamará **Lexico.flex** 

- Un archivo de pruebas generales que se  llamará **prueba.txt** incluyendo la prueba del tema asignado al grupo 

- Un archivo con la tabla de símbolos **ts.txt** 

- Código fuente del proyecto 

- Archivo JAR ejecutable 

Todo el material deberá ser subido a algún repositorio GIT (Github, Gitlab, etc.) y su enlace enviado a teoria1.unlu@gmail.com 

**Asunto: GrupoXX Fecha de entrega: 8/10/26** 

#### **<u>SEGUNDA ENTREGA</u>** 

**OBJETIVO:** Realizar un **analizador sintáctico** utilizando la herramienta JAVA CUP. La aplicación realizada debe mostrar una interfaz gráfica que pueda utilizarse como IDE del compilador, en este caso deberá permitir: 

1. Cumplir mismos puntos que en la primer entrega 

2. Compilar el programa ingresado (análisis sintáctico) y mostrar por pantalla un texto aclaratorio identificando las reglas sintácticas que va analizando el parser. Las impresiones deben ser claras. Las reglas que no realizan ninguna acción no deben generar salida. 

El material a entregar será: 

- El archivo jflex que se llamará **Lexico.flex** 

- El archivo jcup que se llamará **Sintactico.cup** 

- Un archivo de pruebas generales que se  llamará **prueba.txt** incluyendo la prueba del tema asignado al grupo 

- Un archivo con la tabla de símbolos **ts.txt** 

- Código fuente del proyecto 

- Archivo JAR ejecutable 

Todo el material deberá ser subido a algún repositorio GIT (Github, Gitlab, etc.) y su enlace enviado a teoria1.unlu@gmail.com 

**Asunto: GrupoXX** 

###### **Fecha de entrega: 23/11/26** 

# <u>ANEXO TEMAS</u> 

#### **TEMAS COMUNES** 

### **<u>WHILE</u>** 

Implementación de _While_ utilizando el formato que el grupo desee 

### **<u>DECISIONES</u>** 

Implementación de _IF_ utilizando el formato que el grupo desee 

### **<u>ASIGNACIONES</u>** 

Asignaciones simples _a ::= b_ 

### **<u>TIPO DE DATOS</u>** 

<u>Constantes numéricas</u> 

- enteras (16 bits) 

- reales (32 bits) 

El separador decimal será el punto “.” 

##### _Ejemplo:_ 

```
a ::= 99999.99
a ::= 99.
a ::= .9999
```

##### <u>Constantes string</u> 

Constantes de 30 caracteres alfanuméricos como máximo, limitada por comillas (" ") ,de la forma _"XXXX"_ 

##### _Ejemplo:_ 

```
WRITE “@sdADaSjfla%dfg”
var ::= “HOLA MUNDO”
```

**Las constantes deben ser reconocidas y validadas en el** **_analizador léxico_ , de acuerdo a su tipo. Las constantes guardan su valor en tabla de símbolos.** 

### **<u>VARIABLES</u>** 

##### <u>Variables numéricas</u> 

Estas variables reciben valores numéricos tales como constantes numéricas, variables numéricas u operaciones que arrojen un valor numérico, del lado derecho de una asignación. 

##### <u>Variables string</u> 

Estas variables pueden recibir una constante string 

##### **Las variables no guardan su valor en tabla de símbolos.** 

**Las asignaciones deben ser permitidas, solo en los casos en los que los tipos son compatibles, caso contrario deberá desplegarse un error.** 

### **<u>COMENTARIOS</u>** 

Deberán estar delimitados por " //* " y " *// " y podrán estar anidados en un solo nivel. 

_Ejemplo1:_ 

```
//*
IF (a <= 30)
b = ”correcto” //* asignación string *//
ENDIF
*//
```

_Ejemplo2_ `:` 

```
//* Así son los comentarios *//
```

**Los comentarios se ignoran de manera que no generan un componente léxico o token.** 

### **<u>SALIDA</u>** 

Las salidas se implementarán como se muestra en el siguiente ejemplo: 

_Ejemplo:_ 

```
write “ewr”    //* donde “ewr” debe ser una cte string *//
write 99.999 //* donde 99.999 debe ser cualquier cte numérica *//
writevar//* donde var debe ser cualquier variable numérica *//
```

### **<u>CONDICIONES</u>** 

Las condiciones para un constructor de ciclos o de selección, deberán ser comparaciones binarias que pueden estar ligadas por un único conector lógico (AND u OR) 

```
(expresión) < (expresión)
(expresión >= expresión) AND (expresión < expresión)
(expresión >= expresión) OR (expresión < expresión)
```

### **<u>DECLARACIONES</u>** 

Todas las variables deberán ser declaradas dentro de un bloque especial para ese fin, delimitado por las palabras reservadas DECLARE.SECTION y ENDDECLARE.SECTION, siguiendo el siguiente formato: 

```
DECLARE.SECTION
```

```
Línea_de_Declaración_de_Tipos
ENDDECLARE.SECTION
```

Cada _Línea_de_Declaración_de_Tipos_ tendrá la forma: _[Lista de Variables]  :=  [Lista de Tipos]_ 

La _Lista de Variables_ debe ser una lista de variables entre corchetes separadas por comas al igual que la Lista de Tipos. Cada variable se deberá corresponder con cada tipo en la lista de tipos según su posición. No deberán existir más variables que tipos, ni más tipos que variables. Pueden existir varias líneas de declaración de tipos. 


Ejemplos de formato: `DECLARE.SECTION` 

```
[a1, b1] := [FLOAT, INT]
[p1, p2, p3] := [FLOAT,FLOAT,INT]
ENDDECLARE.SECTION
```

### **<u>PROGRAMA</u>** 

Todas las sentencias del programa deberán ser declaradas dentro de un bloque especial para ese fin, delimitado por las palabras reservadas PROGRAM.SECTION y ENDPROGRAM.SECTION, siguiendo el siguiente formato: 

```
PROGRAM.SECTION
Lista_de_Sentencias
ENDPROGRAM.SECTION
```

**La zona de declaración de variables deberá ser previa a la sección del programa.** 

### **<u>TABLA DE SIMBOLOS</u>** 

La tabla de símbolos tiene la capacidad de guardar las variables y constantes con sus atributos. Los atributos aportan información necesaria para operar con constantes y variables. 

**Ejemplo 1er ENTREGA (sin agregar tipos de datos)** 

|NOMBRE|TOKEN|TIPO|VALOR|LONG|
|---|---|---|---|---|
|a1|ID||⎯|⎯|
|b1|ID||⎯|⎯|
|_hola|CTE_STR|⎯|hola|4|
|_mundo|CTE_STR|⎯|mundo|5|
|_30.5|CTE_F|⎯|30.5|⎯|
|_55|CTE_E|⎯|55|⎯|



###### **_Tabla de símbolos_** 

##### **Ejemplo 2da ENTREGA (agregando tipos de datos)** 

|NOMBRE|TOKEN|TIPO|VALOR|LONG|
|---|---|---|---|---|
|a1|ID|Float|⎯|⎯|
|b1|ID|Integer|⎯|⎯|
|_hola|CTE_STR|⎯|hola|4|
|_mundo|CTE_STR|⎯|mundo|5|
|_30.5|CTE_F|⎯|30.5|⎯|
|_55|CTE_E|⎯|55|⎯|



**_Tabla de símbolos_** 


#### **TEMAS ESPECIALES** 

#### **● Grupo 5 SUMAIMPAR (BABINO ABRIL NADIA-COLLAZO NAIARA AGUSTINA-HUICI NICOLAS GUILLERMO-ORTIZ MARIA AGUSTINA-TERRANOVA DANTE JUAN)** 

La sentencia permite sumar los primeros elementos impares dentro de una lista de constantes enteras positivas. La cantidad de elementos impares a sumar estará determinada por un elemento pivot. El elemento pivot deberá ser mayor o igual a uno. 

En caso de que no se encuentre un elemento impar en la lista, o de que haya menos elementos impares en la lista que los indicados por el pivot, se emitirá el mensaje “No puede realizarse la operación” La lista de constantes podría ser vacía en cuyo caso se emitirá un mensaje “La lista está vacía” 

Formato: 

##### **_`id ::= SUMAIMPAR(constante_entera; [lista de constantes])`_** 

Ejemplo: 

**_`resul::= SUMAIMPAR(4;[1,2,3,4,5])`_** //* mensaje no puede realizarse la operación y se asigna 0 la id resul *// **_`resul::= SUMAIMPAR(5;[2,2,2,4])`_** //* mensaje no puede realizarse la operación y se asigna 0 la id resul *// 

**_`resul::= SUMAIMPAR(1;[])`_** //* mensaje la lista esta vacía y se asigna 0 al id resul*// 

**_`resul::= SUMAIMPAR(3;[2,21,7,44,40,33,5])`_** //* La suma de los elementos impares es: 61 que será asignado al id resul *// 



