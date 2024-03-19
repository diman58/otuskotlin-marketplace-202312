package models

class ExchangeOffer(
    var id: ExchangeOfferId = ExchangeOfferId.NONE,
    var title: String = "",
    var offerType: HubDealSide = HubDealSide.NONE,
    var offeredCurrency: ExchangeOfferCurrency = ExchangeOfferCurrency.NONE,
    var desiredCurrency: ExchangeOfferCurrency = ExchangeOfferCurrency.NONE,
    var amount: ExchangeOfferAmount = ExchangeOfferAmount.NONE,
    var rate: ExchangeOfferRate = ExchangeOfferRate.NONE,
    var expectedAmount: ExchangeOfferAmount = ExchangeOfferAmount.NONE,
    var location: ExchangeOfferLocation = ExchangeOfferLocation.NONE,
    var ownerId: ExchangeOfferUserId = ExchangeOfferUserId.NONE,
    var lock: ExchangeOfferLock = ExchangeOfferLock.NONE,
    var visibility: HubVisibility = HubVisibility.NONE,
    val permissionsClient: MutableSet<ExchangeOfferPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = ExchangeOffer()
    }
}