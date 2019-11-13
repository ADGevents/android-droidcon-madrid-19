package com.droidcon.commons.ioc

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = [AnnotationTarget.CLASS, AnnotationTarget.FUNCTION])
annotation class FragmentScope