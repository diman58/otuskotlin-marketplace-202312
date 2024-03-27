import com.otus.otuskotlin.hub.api.v1.models.*

fun HubContext.fromTransport(request: IRequest) = when (request) {
    is OfferCreateRequest -> fromTransport(request)
    is OfferReadRequest -> fromTransport(request)
    is OfferUpdateRequest -> fromTransport(request)
    is OfferDeleteRequest -> fromTransport(request)
    is OfferSearchRequest -> fromTransport(request)
    is OfferFeedRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

fun HubContext.fromTransport(request: OfferCreateRequest) {
    command = HubCommand.CREATE
    offerRequest = request.offer?.toInternal() ?: HubOffer()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OfferCreateObject.toInternal(): HubOffer = HubOffer(
    title = this.title ?: "",
    offerType = this.offerType.fromTransport(),
    offeredCurrency = this.offeredCurrency.toOfferCurrency(),
    desiredCurrency = this.desiredCurrency.toOfferCurrency(),
    amount = this.amount.toOfferAmount(),
    rate = this.rate.toOfferRate(),
    expectedAmount = this.expectedAmount.toOfferAmount(),
    location = this.location.toOfferLocation(),
    visibility = this.visibility.fromTransport(),
)

fun HubContext.fromTransport(request: OfferReadRequest) {
    command = HubCommand.READ
    offerRequest = request.offer.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OfferReadObject?.toInternal(): HubOffer = if (this != null) {
    HubOffer(id = offerId.toOfferId())
} else {
    HubOffer()
}


fun HubContext.fromTransport(request: OfferUpdateRequest) {
    command = HubCommand.UPDATE
    offerRequest = request.offer?.toInternal() ?: HubOffer()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OfferUpdateObject.toInternal(): HubOffer = HubOffer(
    id = this.offerId.toOfferId(),
    title = this.title ?: "",
    offerType = this.offerType.fromTransport(),
    offeredCurrency = this.offeredCurrency.toOfferCurrency(),
    desiredCurrency = this.desiredCurrency.toOfferCurrency(),
    amount = this.amount.toOfferAmount(),
    rate = this.rate.toOfferRate(),
    expectedAmount = this.expectedAmount.toOfferAmount(),
    location = this.location.toOfferLocation(),
    lock = this.lock.toOfferLock(),
    visibility = this.visibility.fromTransport(),
)

fun HubContext.fromTransport(request: OfferDeleteRequest) {
    command = HubCommand.DELETE
    offerRequest = request.offer.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OfferDeleteObject?.toInternal(): HubOffer = if (this != null) {
    HubOffer(
        id = offerId.toOfferId(),
        lock = lock.toOfferLock(),
    )
} else {
    HubOffer()
}

fun HubContext.fromTransport(request: OfferSearchRequest) {
    command = HubCommand.SEARCH
    searchFilter = request.offerFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun OfferSearchFilter?.toInternal() = HubOfferFilter(
    searchString = this?.searchString ?: ""
)

fun HubContext.fromTransport(request: OfferFeedRequest) {
    command = HubCommand.OFFERS
    offerRequest = request.offer.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun String?.toOfferId() = this?.let { HubOfferId(it) } ?: HubOfferId.NONE
private fun String?.toOfferLock() = this?.let { HubOfferLock(it) } ?: HubOfferLock.NONE
private fun String?.toOfferCurrency() = this?.let { HubOfferCurrency(it) } ?: HubOfferCurrency.NONE
private fun String?.toOfferAmount() = this?.let { HubOfferAmount(it) } ?: HubOfferAmount.NONE
private fun String?.toOfferRate() = this?.let { HubOfferRate(it) } ?: HubOfferRate.NONE
private fun String?.toOfferLocation() = this?.let { HubOfferLocation(it) } ?: HubOfferLocation.NONE

private fun OfferDebug?.transportToWorkMode(): HubWorkMode = when (this?.mode) {
    OfferRequestDebugMode.PROD -> HubWorkMode.PROD
    OfferRequestDebugMode.TEST -> HubWorkMode.TEST
    OfferRequestDebugMode.STUB -> HubWorkMode.STUB
    null -> HubWorkMode.PROD
}

private fun OfferDebug?.transportToStubCase(): HubStubs = when (this?.stub) {
    OfferRequestDebugStubs.SUCCESS -> HubStubs.SUCCESS
    OfferRequestDebugStubs.NOT_FOUND -> HubStubs.NOT_FOUND
    OfferRequestDebugStubs.BAD_ID -> HubStubs.BAD_ID
    OfferRequestDebugStubs.BAD_TITLE -> HubStubs.BAD_TITLE
    OfferRequestDebugStubs.BAD_DESCRIPTION -> HubStubs.BAD_DESCRIPTION
    OfferRequestDebugStubs.BAD_VISIBILITY -> HubStubs.BAD_VISIBILITY
    OfferRequestDebugStubs.CANNOT_DELETE -> HubStubs.CANNOT_DELETE
    OfferRequestDebugStubs.BAD_SEARCH_STRING -> HubStubs.BAD_SEARCH_STRING
    null -> HubStubs.NONE
}

private fun OfferVisibility?.fromTransport(): HubVisibility = when (this) {
    OfferVisibility.PUBLIC -> HubVisibility.VISIBLE_PUBLIC
    OfferVisibility.OWNER_ONLY -> HubVisibility.VISIBLE_TO_OWNER
    OfferVisibility.REGISTERED_ONLY -> HubVisibility.VISIBLE_TO_GROUP
    null -> HubVisibility.NONE
}

private fun OfferDealSide?.fromTransport(): HubDealSide = when (this) {
    OfferDealSide.DEMAND -> HubDealSide.DEMAND
    OfferDealSide.SUPPLY -> HubDealSide.SUPPLY
    null -> HubDealSide.NONE
}
