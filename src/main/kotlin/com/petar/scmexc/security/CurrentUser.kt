package com.marco.scmexc.security
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.*


@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE, AnnotationTarget.LOCAL_VARIABLE)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@AuthenticationPrincipal
annotation class CurrentUser
