package com.droidcon.test

import arrow.core.EitherOf
import arrow.core.fix
import arrow.core.identity

fun <L> EitherOf<L, *>.leftOrNull(): L? =
    fix().fold(::identity) { null }