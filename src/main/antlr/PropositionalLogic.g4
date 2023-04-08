grammar PropositionalLogic;


proposition
   : expression // THEREFORE expression
   ;

expression
   : relExpression ((AND | OR) relExpression)*
   ;

relExpression
   : atom
   | equiv
   | implies
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
   : '->' | '=⇒' | '=>'
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