package no.nav.meldeplikt.meldekortservice.api

import io.ktor.client.HttpClient
import io.ktor.locations.Location
import io.ktor.routing.Routing
import no.nav.meldeplikt.meldekortservice.utils.swagger.*
import no.nav.meldeplikt.meldekortservice.utils.Error
import no.nav.meldeplikt.meldekortservice.utils.ErrorMessage
import no.nav.meldeplikt.meldekortservice.utils.MELDEKORT_PATH
import no.nav.meldeplikt.meldekortservice.utils.respondOrServiceUnavailable

fun Routing.meldekortApi(httpClient: HttpClient) {
    getMeldekortdetaljer()
    getKorrigertMeldekort()
}

private const val meldekortGroup = "Meldekort"

@Group(meldekortGroup)
@Location("$MELDEKORT_PATH/{meldekortId}")
data class GetMeldekortdetaljer(val meldekortId: Long)

fun Routing.getMeldekortdetaljer() =
    get<GetMeldekortdetaljer>(
        "Hent meldekortdetaljer".securityAndReponds(
            BearerTokenSecurity(),
            ok<String>(),
            serviceUnavailable<ErrorMessage>(),
            unAuthorized<Error>())) {
            meldekortid -> respondOrServiceUnavailable {
            "Hent meldekortdetaljer er ikke implementert, men id var: ${meldekortid.meldekortId}"
        }
    }

@Group(meldekortGroup)
@Location("$MELDEKORT_PATH/{meldekortId}/korrigering")
data class GetKorrigertMeldekort(val meldekortId: Long)

fun Routing.getKorrigertMeldekort() =
    get<GetKorrigertMeldekort>(
        "Hent korrigert meldekortid".securityAndReponds(BearerTokenSecurity(), ok<String>(),
            serviceUnavailable<ErrorMessage>(), unAuthorized<Error>())) { meldekortid ->
        respondOrServiceUnavailable {
            "Hent korrigert id er ikke implementert, men id var: ${meldekortid.meldekortId}"
        }
    }
