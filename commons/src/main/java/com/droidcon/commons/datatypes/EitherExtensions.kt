package com.droidcon.commons.datatypes

import arrow.core.*

suspend fun <L, RA, RB> Either<L, RA>.suspendedMap(f: suspend (RA) -> RB): Either<L, RB> =
    when (this) {
        is Either.Left -> this
        is Either.Right -> Right(f(b))
    }

suspend fun <L, R> Either<L, R>.runRight(f: suspend (R) -> Unit): Either<L, R> = suspendedMap {
    f(it)
    return@suspendedMap it
}
