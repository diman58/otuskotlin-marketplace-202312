import kotlinx.datetime.Instant
import models.*
import stubs.HubStubs

data class HubContext(
    var command: HubCommand = HubCommand.NONE,
    var state: HubState = HubState.NONE,
    val errors: MutableSet<HubError> = mutableSetOf(),

    var workMode: HubWorkMode = HubWorkMode.PROD,
    var stubCase: HubStubs = HubStubs.NONE,

    var requestId: HubRequestId = HubRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var exchangeOfferRequest: ExchangeOffer = ExchangeOffer(),
    var searchFilter: ExchangeOfferFilter = ExchangeOfferFilter(),

    var exchangeOfferResponse: ExchangeOffer = ExchangeOffer(),
    var exchangeOffersResponse: MutableSet<ExchangeOffer> = mutableSetOf()
)
