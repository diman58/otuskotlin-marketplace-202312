import com.otus.otuskotlin.hub.api.v1.models.*
import exceptions.UnknownHubCommand
import models.*

fun HubContext.toTransport(): IResponse = when (val cmd = command) {
    HubCommand.CREATE -> toTransportCreate()
    HubCommand.READ -> toTransportRead()
    HubCommand.UPDATE -> toTransportUpdate()
    HubCommand.DELETE -> toTransportDelete()
    HubCommand.SEARCH -> toTransportSearch()
    HubCommand.OFFERS -> toTransportOffers()
    HubCommand.NONE -> throw UnknownHubCommand(cmd)
}

fun HubContext.toTransportCreate() = OfferCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    offer = offerResponse.toTransport()
)

private fun HubOffer.toTransport(): OfferResponseObject = OfferResponseObject(
    offerId = id.takeIf { it != HubOfferId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    offerType = offerType.toTransport(),
    offeredCurrency = offeredCurrency.toTransport(),
    desiredCurrency = desiredCurrency.toTransport(),
    amount = amount.toTransport(),
    rate = rate.toTransport(),
    expectedAmount = expectedAmount.toTransport(),
    location = location.toTransport(),
    visibility = visibility.toTransport(),
    ownerId = ownerId.toTransport(),
    lock = lock.toTransport(),
    permissions = permissionsClient.toTransport(),
)

fun HubContext.toTransportRead() = OfferReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    offer = offerResponse.toTransport()
)

fun HubContext.toTransportUpdate() = OfferUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    offer = offerResponse.toTransport()
)

fun HubContext.toTransportDelete() = OfferDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    offer = offerResponse.toTransport()
)

fun HubContext.toTransportSearch() = OfferSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    offer = offersResponse.toTransport()
)

fun HubContext.toTransportOffers() = OfferFeedResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    offer = offersResponse.toTransport()
)

fun Set<HubOffer>.toTransport(): List<OfferResponseObject>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun HubOfferCurrency.toTransport() = this.asString()
private fun  HubOfferAmount.toTransport() = this.asString()
private fun  HubOfferRate.toTransport() = this.asString()
private fun  HubOfferLocation.toTransport() = this.asString()
private fun  HubOfferUserId.toTransport() = this.asString()
private fun  HubOfferLock.toTransport() = this.asString()

private fun Set<HubOfferPermissionClient>.toTransport(): Set<OfferPermissions>? = this
    .map { it.toTransport() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun HubOfferPermissionClient.toTransport() = when (this) {
    HubOfferPermissionClient.READ -> OfferPermissions.READ
    HubOfferPermissionClient.UPDATE -> OfferPermissions.UPDATE
    HubOfferPermissionClient.MAKE_VISIBLE_OWNER -> OfferPermissions.MAKE_VISIBLE_OWN
    HubOfferPermissionClient.MAKE_VISIBLE_GROUP -> OfferPermissions.MAKE_VISIBLE_GROUP
    HubOfferPermissionClient.MAKE_VISIBLE_PUBLIC -> OfferPermissions.MAKE_VISIBLE_PUBLIC
    HubOfferPermissionClient.DELETE -> OfferPermissions.DELETE
}

private fun HubVisibility.toTransport(): OfferVisibility? = when (this) {
    HubVisibility.VISIBLE_PUBLIC -> OfferVisibility.PUBLIC
    HubVisibility.VISIBLE_TO_GROUP -> OfferVisibility.REGISTERED_ONLY
    HubVisibility.VISIBLE_TO_OWNER -> OfferVisibility.OWNER_ONLY
    HubVisibility.NONE -> null
}

private fun HubDealSide.toTransport(): OfferDealSide? = when (this) {
    HubDealSide.DEMAND -> OfferDealSide.DEMAND
    HubDealSide.SUPPLY -> OfferDealSide.SUPPLY
    HubDealSide.NONE -> null
}

private fun Set<HubError>.toTransportErrors(): List<OfferError>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun HubError.toTransport() = OfferError(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun HubState.toResult(): ResponseResult? = when (this) {
    HubState.RUNNING -> ResponseResult.SUCCESS
    HubState.FAILING -> ResponseResult.ERROR
    HubState.FINISHING -> ResponseResult.SUCCESS
    HubState.NONE -> null
}