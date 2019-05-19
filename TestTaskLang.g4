grammar TestTaskLang;


program
 : fundeflist expr | expr
 ;

fundeflist
 : fundef EOL | fundef EOL fundeflist
 ;

fundef
 : identifier OPAR paramlist CPAR ASSIGN OBRACE expr CBRACE
 ;

paramlist
 : identifier | identifier COMMA identifier
 ;

expr
 : identifier | constexpr | binexpr | ifexpr | callexpr
 ;

ifexpr
 : OBRACK expr THEN expr ELSE expr CPAR
 ;

callexpr
 : identifier OPAR arglist CPAR
 ;

arglist
 : expr | expr COMMA arglist
 ;

binexpr
 : OPAR expr operation expr CPAR
 ;

constexpr
 : MINUS number | number
 ;


identifier
 : CHARACTER | CHARACTER identifier
 ;

number
 : DIGIT | DIGIT number
 ;

operation
 : PLUS | MINUS | MULT | DIV | MOD | GT | LT | ASSIGN
 ;


GT : '>';
LT : '<';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
MOD : '%';


COMMA : ',';
ASSIGN : '=';
OPAR : '(';
CPAR : ')';
OBRACE : '{';
CBRACE : '}';
OBRACK : '[';
THEN : ']?(';
ELSE : '):(';


DIGIT
 : [0-9]
 ;

CHARACTER
 : [a-zA-Z_]
 ;

EOL
 : [\n]
 ;