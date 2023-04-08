package formula

val Formula.valid: Boolean
    get() = when { //a sentence α is valid if and only if it is true at every world
        this is Formula.Var -> true
        this is Formula.Not && this.f is Formula.Var -> true
        else -> {
            var valid = true
            world.iterator().forEach {
                if (!evaluate(it)) valid = false
            }
            valid
        }
    }