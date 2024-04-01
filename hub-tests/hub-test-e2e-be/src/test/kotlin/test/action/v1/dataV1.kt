package test.action.v1

import com.otus.otuskotlin.hub.api.v1.models.*

val debug = OfferDebug(mode = OfferRequestDebugMode.STUB, stub = OfferRequestDebugStubs.SUCCESS)

val someCreateOffer = OfferCreateObject(
    title = "Требуются йены",
    offerType = OfferDealSide.DEMAND,
    offeredCurrency = "йены",
    desiredCurrency = "доллары",
    amount = "100000",
    rate = "1.1",
    expectedAmount = "110000",
    location = "turkey",
    visibility = OfferVisibility.PUBLIC
)
