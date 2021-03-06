package com.mackhartley.temptracker.di

import androidx.lifecycle.ViewModel
import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import dagger.MapKey
import kotlin.reflect.KClass

//REF: https://stackoverflow.com/questions/44712248/inject-viewmodel-using-dagger-2-kotlin-viewmodel
@Suppress("DEPRECATED_JAVA_ANNOTATION")
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)