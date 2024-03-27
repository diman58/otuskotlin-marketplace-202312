import kotlinx.datetime.Instant
import models.*

data class HubContext(
    var command: HubCommand = HubCommand.NONE,
    var state: HubState = HubState.NONE,
    val errors: MutableSet<HubError> = mutableSetOf(),

    var workMode: HubWorkMode = HubWorkMode.PROD,
    var stubCase: HubStubs = HubStubs.NONE,

    var requestId: HubRequestId = HubRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var offerRequest: HubOffer = HubOffer(),
    var searchFilter: HubOfferFilter = HubOfferFilter(),

    var offerResponse: HubOffer = HubOffer(),
    var offersResponse: MutableSet<HubOffer> = mutableSetOf()
)
