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

fun HubContext.toTransportCreate() = ExchangeOfferCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exchangeOffer = exchangeOfferResponse.toTransport()
)

private fun ExchangeOffer.toTransport(): ExchangeOfferResponseObject = ExchangeOfferResponseObject(
    id = id.takeIf { it != ExchangeOfferId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    offerType = offerType.toTransport(),
    offeredCurrency = offeredCurrency.toTransport(),
    desiredCurrency = desiredCurrency.toTransport(),
    amount = amount.toTransport(),
    rate = rate.toTransport(),
    expectedAmountInDesired = expectedAmount.toTransport(),
    visibility = visibility.toTransport(),
    permissions = permissionsClient.toTransport(),
)

fun HubContext.toTransportRead() = ExchangeOfferReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exchangeOffer = exchangeOfferResponse.toTransport()
)

fun HubContext.toTransportUpdate() = ExchangeOfferUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exchangeOffer = exchangeOfferResponse.toTransport()
)

fun HubContext.toTransportDelete() = ExchangeOfferDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exchangeOffer = exchangeOfferResponse.toTransport()
)

fun HubContext.toTransportSearch() = ExchangeOfferSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exchangeOffer = exchangeOffersResponse.toTransport()
)

fun HubContext.toTransportOffers() = ExchangeOfferFeedResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    exchangeOffer = exchangeOffersResponse.toTransport()
)

fun Set<ExchangeOffer>.toTransport(): List<ExchangeOfferResponseObject>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ExchangeOfferCurrency.toTransport() = this.asString()
private fun  ExchangeOfferAmount.toTransport() = this.asString()
private fun  ExchangeOfferRate.toTransport() = this.asString()

private fun Set<ExchangeOfferPermissionClient>.toTransport(): Set<ExchangeOfferPermissions>? = this
    .map { it.toTransport() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun ExchangeOfferPermissionClient.toTransport() = when (this) {
    ExchangeOfferPermissionClient.READ -> ExchangeOfferPermissions.READ
    ExchangeOfferPermissionClient.UPDATE -> ExchangeOfferPermissions.UPDATE
    ExchangeOfferPermissionClient.MAKE_VISIBLE_OWNER -> ExchangeOfferPermissions.MAKE_VISIBLE_OWN
    ExchangeOfferPermissionClient.MAKE_VISIBLE_GROUP -> ExchangeOfferPermissions.MAKE_VISIBLE_GROUP
    ExchangeOfferPermissionClient.MAKE_VISIBLE_PUBLIC -> ExchangeOfferPermissions.MAKE_VISIBLE_PUBLIC
    ExchangeOfferPermissionClient.DELETE -> ExchangeOfferPermissions.DELETE
}

private fun HubVisibility.toTransport(): ExchangeOfferVisibility? = when (this) {
    HubVisibility.VISIBLE_PUBLIC -> ExchangeOfferVisibility.PUBLIC
    HubVisibility.VISIBLE_TO_GROUP -> ExchangeOfferVisibility.REGISTERED_ONLY
    HubVisibility.VISIBLE_TO_OWNER -> ExchangeOfferVisibility.OWNER_ONLY
    HubVisibility.NONE -> null
}

private fun HubDealSide.toTransport(): DealSide? = when (this) {
    HubDealSide.DEMAND -> DealSide.DEMAND
    HubDealSide.SUPPLY -> DealSide.SUPPLY
    HubDealSide.NONE -> null
}

private fun Set<HubError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun HubError.toTransport() = Error(
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