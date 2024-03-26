package test.action.v1

import Client
import com.otus.otuskotlin.hub.api.v1.models.OfferResponseObject
import com.otus.otuskotlin.hub.api.v1.models.OfferSearchFilter
import com.otus.otuskotlin.hub.api.v1.models.OfferSearchRequest
import com.otus.otuskotlin.hub.api.v1.models.OfferSearchResponse
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should

suspend fun Client.searchOffer(search: OfferSearchFilter): List<OfferResponseObject> = searchOffer(search) {
    it should haveSuccessResult
    it.offer ?: listOf()
}

suspend fun <T> Client.searchOffer(search: OfferSearchFilter, block: (OfferSearchResponse) -> T): T =
    withClue("searchAdV1: $search") {
        val response = sendAndReceive(
            "ad/search",
            OfferSearchRequest(
                requestType = "search",
                debug = debug,
                offerFilter = search,
            )
        ) as OfferSearchResponse

        response.asClue(block)
    }
