package no.nav.meldeplikt.meldekortservice.config

import java.net.URL

data class Environment(
    val username: String = getEnvVar("FSS_SYSTEMUSER_USERNAME", "username"),
    val password: String = getEnvVar("FSS_SYSTEMUSER_PASSWORD", "password"),
    val securityAudience: String = getEnvVar("AUDIENCE", "dummyAudience"),
    val securityJwksIssuer: String = getEnvVar("JWKS_ISSUER", "dummyIssuer"),
    val securityJwksUri: URL = URL(getEnvVar("JWKS_URI", "https://dummyUrl.com"))
)

fun getEnvVar(varName: String, defaultValue: String? = null): String {
    return System.getenv(varName) ?: defaultValue
    ?: throw IllegalArgumentException("Variable $varName cannot be empty")
}