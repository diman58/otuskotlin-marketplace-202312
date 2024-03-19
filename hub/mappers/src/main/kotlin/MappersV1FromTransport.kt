import com.otus.otuskotlin.hub.api.v1.models.*
import exception.UnknownRequestClass
import models.*
import stubs.HubStubs

fun HubContext.fromTransport(request: IRequest) = when (request) {
    is ExchangeOfferCreateRequest -> fromTransport(request)
    is ExchangeOfferReadRequest -> fromTransport(request)
    is ExchangeOfferUpdateRequest -> fromTransport(request)
    is ExchangeOfferDeleteRequest -> fromTransport(request)
    is ExchangeOfferSearchRequest -> fromTransport(request)
    is ExchangeOfferFeedRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

fun HubContext.fromTransport(request: ExchangeOfferCreateRequest) {
    command = HubCommand.CREATE
    exchangeOfferRequest = request.exchangeOffer?.toInternal() ?: ExchangeOffer()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExchangeOfferCreateObject.toInternal(): ExchangeOffer = ExchangeOffer(
    id = this.id.toExchangeOfferId(),
    title = this.title ?: "",
    offerType = this.offerType.fromTransport(),
    offeredCurrency = this.offeredCurrency.toExchangeOfferCurrency(),
    desiredCurrency = this.desiredCurrency.toExchangeOfferCurrency(),
    amount = this.amount.toExchangeOfferAmount(),
    rate = this.rate.toExchangeOfferRate(),
    expectedAmount = this.expectedAmountInDesired.toExchangeOfferAmount(),
    location = this.location.toExchangeOfferLocation(),
    visibility = this.visibility.fromTransport(),
)

fun HubContext.fromTransport(request: ExchangeOfferReadRequest) {
    command = HubCommand.READ
    exchangeOfferRequest = request.exchangeOffer.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExchangeOfferReadObject?.toInternal(): ExchangeOffer = if (this != null) {
    ExchangeOffer(id = id.toExchangeOfferId())
} else {
    ExchangeOffer()
}


fun HubContext.fromTransport(request: ExchangeOfferUpdateRequest) {
    command = HubCommand.UPDATE
    exchangeOfferRequest = request.exchangeOffer?.toInternal() ?: ExchangeOffer()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExchangeOfferUpdateObject.toInternal(): ExchangeOffer = ExchangeOffer(
    id = this.id.toExchangeOfferId(),
    title = this.title ?: "",
    offerType = this.offerType.fromTransport(),
    offeredCurrency = this.offeredCurrency.toExchangeOfferCurrency(),
    desiredCurrency = this.desiredCurrency.toExchangeOfferCurrency(),
    amount = this.amount.toExchangeOfferAmount(),
    rate = this.rate.toExchangeOfferRate(),
    expectedAmount = this.amount.toExchangeOfferAmount(),
    location = this.location.toExchangeOfferLocation(),
    lock = this.lock.toExchangeOfferLock(),
    visibility = this.visibility.fromTransport(),
)

fun HubContext.fromTransport(request: ExchangeOfferDeleteRequest) {
    command = HubCommand.DELETE
    exchangeOfferRequest = request.exchangeOffer.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExchangeOfferDeleteObject?.toInternal(): ExchangeOffer = if (this != null) {
    ExchangeOffer(
        id = id.toExchangeOfferId(),
        lock = lock.toExchangeOfferLock(),
    )
} else {
    ExchangeOffer()
}

fun HubContext.fromTransport(request: ExchangeOfferSearchRequest) {
    command = HubCommand.SEARCH
    searchFilter = request.exchangeOfferFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ExchangeOfferSearchFilter?.toInternal() = ExchangeOfferFilter(
    searchString = this?.searchString ?: ""
)

fun HubContext.fromTransport(request: ExchangeOfferFeedRequest) {
    command = HubCommand.OFFERS
    exchangeOfferRequest = request.exchangeOffer.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun String?.toExchangeOfferId() = this?.let { ExchangeOfferId(it) } ?: ExchangeOfferId.NONE
private fun String?.toExchangeOfferLock() = this?.let { ExchangeOfferLock(it) } ?: ExchangeOfferLock.NONE
private fun String?.toExchangeOfferCurrency() = this?.let { ExchangeOfferCurrency(it) } ?: ExchangeOfferCurrency.NONE
private fun String?.toExchangeOfferAmount() = this?.let { ExchangeOfferAmount(it) } ?: ExchangeOfferAmount.NONE
private fun String?.toExchangeOfferRate() = this?.let { ExchangeOfferRate(it) } ?: ExchangeOfferRate.NONE
private fun String?.toExchangeOfferLocation() = this?.let { ExchangeOfferLocation(it) } ?: ExchangeOfferLocation.NONE

private fun ExchangeOfferDebug?.transportToWorkMode(): HubWorkMode = when (this?.mode) {
    ExchangeOfferRequestDebugMode.PROD -> HubWorkMode.PROD
    ExchangeOfferRequestDebugMode.TEST -> HubWorkMode.TEST
    ExchangeOfferRequestDebugMode.STUB -> HubWorkMode.STUB
    null -> HubWorkMode.PROD
}

private fun ExchangeOfferDebug?.transportToStubCase(): HubStubs = when (this?.stub) {
    ExchangeOfferRequestDebugStubs.SUCCESS -> HubStubs.SUCCESS
    ExchangeOfferRequestDebugStubs.NOT_FOUND -> HubStubs.NOT_FOUND
    ExchangeOfferRequestDebugStubs.BAD_ID -> HubStubs.BAD_ID
    ExchangeOfferRequestDebugStubs.BAD_TITLE -> HubStubs.BAD_TITLE
    ExchangeOfferRequestDebugStubs.BAD_DESCRIPTION -> HubStubs.BAD_DESCRIPTION
    ExchangeOfferRequestDebugStubs.BAD_VISIBILITY -> HubStubs.BAD_VISIBILITY
    ExchangeOfferRequestDebugStubs.CANNOT_DELETE -> HubStubs.CANNOT_DELETE
    ExchangeOfferRequestDebugStubs.BAD_SEARCH_STRING -> HubStubs.BAD_SEARCH_STRING
    null -> HubStubs.NONE
}

private fun ExchangeOfferVisibility?.fromTransport(): HubVisibility = when (this) {
    ExchangeOfferVisibility.PUBLIC -> HubVisibility.VISIBLE_PUBLIC
    ExchangeOfferVisibility.OWNER_ONLY -> HubVisibility.VISIBLE_TO_OWNER
    ExchangeOfferVisibility.REGISTERED_ONLY -> HubVisibility.VISIBLE_TO_GROUP
    null -> HubVisibility.NONE
}

private fun DealSide?.fromTransport(): HubDealSide = when (this) {
    DealSide.DEMAND -> HubDealSide.DEMAND
    DealSide.SUPPLY -> HubDealSide.SUPPLY
    null -> HubDealSide.NONE
}
