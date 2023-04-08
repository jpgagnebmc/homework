package formula

fun Formula.toCnf(): Formula {
    var r: Formula? = null
    var lastHash = hashCode()
    var step = 1
    source
    while (lastHash != r.hashCode()) {
        lastHash = r.hashCode()
        r = toCnfStep().also { it.source }
        step += 1
    }
    return r!!
}

fun Formula.toCnfStep(): Formula = when (this) {
    is Formula.And -> copy(f1.toCnfStep(), f2.toCnfStep())
    is Formula.Group -> copy(f.toCnfStep())
    is Formula.Implies -> Formula.Or(Formula.Not(f1.toCnfStep()), f2.toCnfStep())
    is Formula.Not -> when {
        doubleNegative -> (f as Formula.Not).f
        negativeGroupedOr -> {
            f as Formula.Group
            val or = f.f as Formula.Or
            when {
                (or.f1 is Formula.Var || or.f1 is Formula.Not) && (or.f2 is Formula.Var || or.f2 is Formula.Not) -> Formula.And(Formula.Not(or.f1.toCnfStep()), Formula.Not(or.f2.toCnfStep()))
                else -> Formula.Group(Formula.And(Formula.Not(or.f1.toCnfStep()), Formula.Not(or.f2.toCnfStep())))
            }
        }

        negativeGroupedAnd -> {
            f as Formula.Group
            val and = f.f as Formula.And
            Formula.Or(Formula.Not(and.f1.toCnfStep()), Formula.Not(and.f2.toCnfStep()))
        }

        else -> copy(f.toCnfStep())
    }

    is Formula.Or -> when {
        orOverAnd -> {
            f2 as Formula.Group
            val and = f2.f as Formula.And
            Formula.And(Formula.Group(Formula.Or(f1, and.f1)), Formula.Group(Formula.Or(f1, and.f2)))
        }

        else -> copy(f1.toCnfStep(), f2.toCnfStep())
    }

    is Formula.Var -> this
}

val Formula.orOverAnd: Boolean
    get() = this is Formula.Or && f2 is Formula.Group && f2.f is Formula.And
val Formula.negativeGroupedAnd: Boolean
    get() = this is Formula.Not && f is Formula.Group && f.f is Formula.And
val Formula.negativeGroupedOr: Boolean
    get() = this is Formula.Not && f is Formula.Group && f.f is Formula.Or

val Formula.doubleNegative: Boolean
    get() = this is Formula.Not && this.f is Formula.Not