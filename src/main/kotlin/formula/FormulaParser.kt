package formula

import PropositionalLogicLexer
import PropositionalLogicParser
import formula.Formula.*
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

data class FormulaParser(val expression: String) {
    fun model(): Formula = ExpParserContext(parser).toExp()

    val parser: PropositionalLogicParser
        get() {
            val input = CharStreams.fromString(expression)
            val lexer = PropositionalLogicLexer(input)
            val tokens = CommonTokenStream(lexer)
            return PropositionalLogicParser(tokens)
        }
}


class ExpParserContext(val parser: PropositionalLogicParser) {
    fun toExp(): Formula = parser.proposition().expression().toExp()

    private fun PropositionalLogicParser.ExpressionContext.toExp(): Formula {
        return relExpression().toExp()
    }

    private fun PropositionalLogicParser.RelExpressionContext.toExp(): Formula = when {
        atom() != null -> atom().toExp()
        andatoms() != null -> andatoms().toExp()
////            implies() != null -> implies().toExp()
        else -> TODO(this.toString())

    }

    private fun PropositionalLogicParser.AtomContext.toExp(): Formula = when {
        LPAREN() != null && expression() != null -> Group(expression().toExp())
        NOT() != null -> Not(atom().toExp())
        variable() != null -> variable().toExp()
        expression() != null -> expression().toExp()
//        atom() != null -> atom().toExp()
        else -> TODO(toStringTree(parser))
    }

    private fun PropositionalLogicParser.ImpliesContext.toExp(): Formula = Implies(atom().first().toExp(), atom().last().toExp())
    private fun PropositionalLogicParser.VariableContext.toExp(): Formula = Var(LETTER().first().toString())
    private fun PropositionalLogicParser.AndatomsContext.toExp(): Formula = when {
        OPERATOR() != null -> when (OPERATOR().toString()) {
            "^" -> And(atom().toExp(), andatoms().toExp())
            "∧" -> And(atom().toExp(), andatoms().toExp())
            "v" -> Or(atom().toExp(), andatoms().toExp())
            "∨" -> Or(atom().toExp(), andatoms().toExp())
            "=⇒" -> Implies(atom().toExp(), andatoms().toExp())
            "==>" -> Implies(atom().toExp(), andatoms().toExp())
            "=>" -> Implies(atom().toExp(), andatoms().toExp())
            "->" -> Implies(atom().toExp(), andatoms().toExp())
//        AND() != null -> Formula.And(atom().toExp(), andatoms().toExp())
//        atom() != null -> atom().toExp()
            else -> TODO(OPERATOR().toString())
        }

        else -> atom().toExp()
    }


}


