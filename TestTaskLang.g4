grammar TestTaskLang;




ARGLIST
 : EXPR | EXPR ',' ARGLIST
 ;

BINEXPR
 : '(' EXPR OPERATION EXPR ')'
 ;

CONSTEXPR
 : '-' NUMBER | NUMBER
 ;

OPERATION
 : '+' | '-' | '*' | '/' | '%' | '>' | '<' | '='
 ;

IDENTIFIER
 : CHARACTER | CHARACTER IDENTIFIER
 ;

NUMBER
 : DIGIT | DIGIT NUMBER
 ;

DIGIT
 : [0-9]
 ;

CHARACTER
 : [a-zA-Z_]
 ;