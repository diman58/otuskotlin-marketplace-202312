import com.otus.otuskotlin.hub.api.log1.models.*
import kotlinx.datetime.Clock

fun HubContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "hub",
    offer = toMkplLog(),
    errors = errors.map { it.toLog() },
)

private fun HubContext.toMkplLog(): HubLogModel? {
    val offerNone = HubOffer()
    return HubLogModel(
        requestId = requestId.takeIf { it != HubRequestId.NONE }?.asString(),
        requestOffer = offerRequest.takeIf { it != offerNone }?.toLog(),
        responseOffer = offerResponse.takeIf { it != offerNone }?.toLog(),
        responseOffers = offersResponse.takeIf { it.isNotEmpty() }?.filter { it != offerNone }?.map { it.toLog() },
        requestFilter = searchFilter.takeIf { it != HubOfferFilter() }?.toLog(),
    ).takeIf { it != HubLogModel() }
}

private fun HubOfferFilter.toLog() = OfferFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != HubOfferUserId.NONE }?.asString(),
    dealSide = dealSide.takeIf { it != HubDealSide.NONE }?.name,
)

private fun HubError.toLog() = ErrorLogModel(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
    //level = level.name,
    exception = exception.takeIf { it != null }.toString()
)

private fun HubOffer.toLog() = OfferLog(
    offerId = id.takeIf { it != HubOfferId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    offerType = offerType.takeIf { it != HubDealSide.NONE }?.name,
    offeredCurrency = offeredCurrency.takeIf { it != HubOfferCurrency.NONE }?.asString(),
    desiredCurrency = desiredCurrency.takeIf { it != HubOfferCurrency.NONE }?.asString(),
    amount = amount.takeIf { it != HubOfferAmount.NONE }?.asString(),
    rate = rate.takeIf { it != HubOfferRate.NONE }?.asString(),
    expectedAmount = amount.takeIf { it != HubOfferAmount.NONE }?.asString(),
    location = location.takeIf { it != HubOfferLocation.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != HubOfferUserId.NONE }?.asString(),
    visibility = visibility.takeIf { it != HubVisibility.NONE }?.name,
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)
