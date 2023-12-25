package com.syphyr.ourlittleones.backend.functional

import com.syphyr.ourlittleones.backend.exceptions.ApiError
import org.springframework.http.ResponseEntity

//Please note that the either class itself doesn't hold any values. Its child classes does.
sealed class Either<out L, out R> {
  // by FP conventions left would be the failure of this Either
  data class Left<out L>(val a: L) : Either<L, Nothing>()

  // by FP conventions right would be the Success of this Either
  data class Right<out R>(val b: R) : Either<Nothing, R>()

  fun <L> left(a: L) = Left(a)
  fun <R> right(b: R) = Right(b)

  fun either(fnL: (L) -> Unit, fnR: (R) -> Unit): Any =
    when (this) {
      is Left -> fnL(a)
      is Right -> fnR(b)
    }
  suspend fun eitherAsync(fnL: suspend (L) -> Any, fnR: suspend (R) -> Unit): Any =
    when (this) {
      is Left -> fnL(a)
      is Right -> fnR(b)
    }
}

infix fun <L, R, R2> Either<L, R>.map(f: (R) -> R2): Either<L, R2> = when (this) {
  is Either.Left -> this
  is Either.Right -> Either.Right(f(this.b))
}

suspend infix fun <L, R, R2> Either<L, R>.mapSuspend(f: suspend (R) -> R2): Either<L, R2> =
  when (this) {
    is Either.Left -> this
    is Either.Right -> {
        Either.Right(f(this.b))
    }
  }

infix fun <L, R, R2> Either<L, R>.flatMap(f: (R) -> Either<L, R2>): Either<L, R2> = when (this) {
  is Either.Left -> this
  is Either.Right -> f(b)
}


fun <R> Either<ApiError, R>.toResponseEntity(): ResponseEntity<*> =
        when (this) {
            is Either.Left -> ResponseEntity.status(this.a.status).body(this.a)
            is Either.Right -> ResponseEntity.ok(this.b)
        }


suspend infix fun <L, R, R2> Either<L, R>.flatMapSuspend(f: suspend (R) -> Either<L, R2>): Either<L, R2> =
  when (this) {
    is Either.Left -> this
    is Either.Right -> f(b)
  }

fun <T> T.toRight(): Either<ApiError, T> {
    return Either.Right(this)
}