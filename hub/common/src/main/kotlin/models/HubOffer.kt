package models

data class HubOffer(
    var id: HubOfferId = HubOfferId.NONE,
    var title: String = "",
    var offerType: HubDealSide = HubDealSide.NONE,
    var offeredCurrency: HubOfferCurrency = HubOfferCurrency.NONE,
    var desiredCurrency: HubOfferCurrency = HubOfferCurrency.NONE,
    var amount: HubOfferAmount = HubOfferAmount.NONE,
    var rate: HubOfferRate = HubOfferRate.NONE,
    var expectedAmount: HubOfferAmount = HubOfferAmount.NONE,
    var location: HubOfferLocation = HubOfferLocation.NONE,
    var ownerId: HubOfferUserId = HubOfferUserId.NONE,
    var lock: HubOfferLock = HubOfferLock.NONE,
    var visibility: HubVisibility = HubVisibility.NONE,
    val permissionsClient: MutableSet<HubOfferPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = HubOffer()
    }
}