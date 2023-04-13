grammar Probability;


proposition
   : expression // THEREFORE expression
   ;

expression
   : relExpression
   ;

relExpression
   : atom
   | andatoms
   ;

andatoms
   : atom
   | atom OPERATOR andatoms
   ;

OPERATOR
   : AND
   | OR
   | IMPLIES
   | EQUIV
   ;

atoms
   : atom (',' atom)*
   ;

atom
   : variable
   | NOT atom
   | LPAREN expression RPAREN
   ;

equiv
   : atom EQUIV atom
   ;

implies
   : atom IMPLIES atom
   ;

variable
   : LETTER*
   ;


AND
   : '^' | '∧'
   ;


OR
   : 'v' | '∨'
   ;


NOT
   : '!' | '¬' | '-'
   ;


EQ
   : '='
   ;


IMPLIES
   : '->' | '=⇒' | '=>' | '==>'
   ;


THEREFORE
   : '|-'
   ;


EQUIV
   : '<->'
   ;


LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;


LETTER
   : ('a' .. 'z') | ('A' .. 'Z')
   ;


WS
   : [ \r\n\t] + -> channel (HIDDEN)
   ;