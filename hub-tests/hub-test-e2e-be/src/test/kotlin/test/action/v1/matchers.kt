package test.action.v1

import com.otus.otuskotlin.hub.api.v1.models.*
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.and


fun haveResult(result: ResponseResult) = Matcher<IResponse> {
    MatcherResult(
        it.result == result,
        { "actual result ${it.result} but we expected $result" },
        { "result should not be $result" }
    )
}

val haveNoErrors = Matcher<IResponse> {
    MatcherResult(
        it.errors.isNullOrEmpty(),
        { "actual errors ${it.errors} but we expected no errors" },
        { "errors should not be empty" }
    )
}

fun haveError(code: String) = haveResult(ResponseResult.ERROR)
    .and(Matcher<IResponse> {
        MatcherResult(
            it.errors?.firstOrNull { e -> e.code == code } != null,
            { "actual errors ${it.errors} but we expected error with code $code" },
            { "errors should not contain $code" }
        )
    })

val haveSuccessResult = haveResult(ResponseResult.SUCCESS) and haveNoErrors

val IResponse.offer: OfferResponseObject?
    get() = when (this) {
        is OfferCreateResponse -> offer
        is OfferReadResponse -> offer
        is OfferUpdateResponse -> offer
        is OfferDeleteResponse -> offer
        //is OfferFeedResponse -> offer
        else -> throw IllegalArgumentException("Invalid response type: ${this::class}")
    }
