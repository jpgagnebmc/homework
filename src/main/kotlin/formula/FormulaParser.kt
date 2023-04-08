package formula

import PropositionalLogicLexer
import PropositionalLogicParser
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
//        println("and ${AND()}")
//        println("or ${OR()}")
//        println("relExpressions ${relExpression().size}")
        return when {
            relExpression().size == 1 -> relExpression().first().toExp()
            AND().isNotEmpty() -> Formula.And(relExpression().first().toExp(), relExpression().last().toExp())
            OR().isNotEmpty() -> Formula.Or(relExpression().first().toExp(), relExpression().last().toExp())
            else -> TODO(toStringTree(parser))
        }
    }

    private fun PropositionalLogicParser.RelExpressionContext.toExp(): Formula {
        return when {
            atom() != null -> atom().toExp()
            implies() != null -> implies().toExp()
            else -> TODO(this.toString())
        }
    }

    private fun PropositionalLogicParser.AtomContext.toExp(): Formula = when {
        LPAREN() != null && expression() != null -> Formula.Group(expression().toExp())
        NOT() != null -> Formula.Not(atom().toExp())
        variable() != null -> variable().toExp()
        expression() != null -> expression().toExp()
//        atom() != null -> atom().toExp()
        else -> TODO(toStringTree(parser))
    }

    private fun PropositionalLogicParser.ImpliesContext.toExp(): Formula = Formula.Implies(atom().first().toExp(), atom().last().toExp())
    private fun PropositionalLogicParser.VariableContext.toExp(): Formula = Formula.Var(LETTER().first().toString())
}