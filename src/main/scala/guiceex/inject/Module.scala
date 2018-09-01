package guiceex.inject

import java.lang.annotation.Annotation

import com.google.inject.AbstractModule
import com.google.inject.binder.{AnnotatedBindingBuilder, ScopedBindingBuilder}
import javax.inject.Provider

import scala.reflect.ClassTag

/**
 * Extended abstraction for Guice, via implicit conversion to accomplish a Scala-way DSL.
 * The following approach is definitely not an ideal way.
 * In play, the binding and injection abstraction is re-implemented under JSR-330 spec.
 *
 * Ref to: https://github.com/playframework/playframework/blob/master/framework/src/play/src/main/scala/play/api/inject/Binding.scala
 */
abstract class Module extends AbstractModule {
  implicit class ExtendAnnotatedBindingBuilder[T: ClassTag](builder: AnnotatedBindingBuilder[T]) {
    def to[M <: T: ClassTag]: ExtendScopedBindingBuilder = {
      val clazz = implicitly[ClassTag[M]].runtimeClass.asInstanceOf[Class[M]]
      builder.to(clazz)
    }

    def toProvider[M <: Provider[T]: ClassTag]: ExtendScopedBindingBuilder = {
      val clazz = implicitly[ClassTag[M]].runtimeClass.asInstanceOf[Class[M]]
      builder.toProvider(clazz)
    }
  }

  implicit class ExtendScopedBindingBuilder(builder: ScopedBindingBuilder) {
    def in[M <: Annotation: ClassTag] = {
      val clazz = implicitly[ClassTag[M]].runtimeClass.asInstanceOf[Class[M]]
      builder.in(clazz) // this is a mutated operation?
    }
  }

  def bind[T: ClassTag]: ExtendAnnotatedBindingBuilder[T] = {
    val clazz = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]
    super.bind(clazz)
  }
}
