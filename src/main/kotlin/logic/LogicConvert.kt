package logic

fun Logic.toCnf(): Logic {
    var r: Logic? = null
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

fun Logic.toCnfStep(): Logic = when (this) {
    is Logic.And -> copy(f1.toCnfStep(), f2.toCnfStep())
    is Logic.Group -> copy(f.toCnfStep())
    is Logic.Implies -> Logic.Or(Logic.Not(f1.toCnfStep()), f2.toCnfStep())
    is Logic.Not -> when {
        doubleNegative -> (f as Logic.Not).f
        negativeGroupedOr -> {
            f as Logic.Group
            val or = f.f as Logic.Or
            when {
                (or.f1 is Logic.Var || or.f1 is Logic.Not) && (or.f2 is Logic.Var || or.f2 is Logic.Not) -> Logic.And(Logic.Not(or.f1.toCnfStep()), Logic.Not(or.f2.toCnfStep()))
                else -> Logic.Group(Logic.And(Logic.Not(or.f1.toCnfStep()), Logic.Not(or.f2.toCnfStep())))
            }
        }

        negativeGroupedAnd -> {
            f as Logic.Group
            val and = f.f as Logic.And
            Logic.Or(Logic.Not(and.f1.toCnfStep()), Logic.Not(and.f2.toCnfStep()))
        }

        else -> copy(f.toCnfStep())
    }

    is Logic.Or -> when {
        orOverAnd -> {
            f2 as Logic.Group
            val and = f2.f as Logic.And
            Logic.And(Logic.Group(Logic.Or(f1, and.f1)), Logic.Group(Logic.Or(f1, and.f2)))
        }

        else -> copy(f1.toCnfStep(), f2.toCnfStep())
    }

    is Logic.Var -> this
}

val Logic.orOverAnd: Boolean
    get() = this is Logic.Or && f2 is Logic.Group && f2.f is Logic.And
val Logic.negativeGroupedAnd: Boolean
    get() = this is Logic.Not && f is Logic.Group && f.f is Logic.And
val Logic.negativeGroupedOr: Boolean
    get() = this is Logic.Not && f is Logic.Group && f.f is Logic.Or

val Logic.doubleNegative: Boolean
    get() = this is Logic.Not && this.f is Logic.Not