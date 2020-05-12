package nstda.hii.webservice.library.keycloak

import com.github.scribejava.apis.KeycloakApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.github.scribejava.core.model.OAuth2AccessToken
import com.github.scribejava.core.model.OAuthRequest
import com.github.scribejava.core.model.Verb
import nstda.hii.webservice.constKeycloakApiKey
import nstda.hii.webservice.constKeycloakApiSecret
import nstda.hii.webservice.constKeycloakBaseUrl
import nstda.hii.webservice.constKeycloakCallback
import nstda.hii.webservice.constKeycloakRealm
import nstda.hii.webservice.getLogger

class Login {
    private val protectedResourceUrl =
        "$constKeycloakBaseUrl/auth/realms/$constKeycloakRealm/protocol/openid-connect/userinfo"
    private val service = ServiceBuilder(constKeycloakApiKey)
        .apiSecret(constKeycloakApiSecret)
        .defaultScope("openid")
        .callback(constKeycloakCallback)
        .build(KeycloakApi.instance(constKeycloakBaseUrl, constKeycloakRealm))

    fun login(username: String, password: String): OAuth2AccessToken {
        logger.info { "=== Keyloack's OAuth Workflow ===" }

        // Obtain the Authorization URL
        logger.trace { "Fetching the Authorization URL..." }

        val authorizationUrl = service.authorizationUrl
        logger.trace { "Got the Authorization URL!" }
        logger.trace { "Now go and authorize ScribeJava here:" }
        logger.debug { authorizationUrl }
        logger.trace { "And paste the authorization code here >>" }
        val accessToken = service.getAccessTokenPasswordGrantAsync(username, password).get()

        logger.trace { "Got the Access Token!" }
        logger.debug { "(The raw response looks like this: " + accessToken.rawResponse + "')" }

        // Now let's go and ask for a protected resource!
        logger.trace { "Now we're going to access a protected resource..." }

        verifyToken(accessToken)

        logger.trace { "Thats it man! Go and build something awesome with ScribeJava! :)" }
        return accessToken!!
    }

    fun verifyToken(accessToken: OAuth2AccessToken?): Boolean {
        val request = OAuthRequest(Verb.GET, protectedResourceUrl)

        service.signRequest(accessToken, request)
        val response = service.execute(request)
        logger.trace { "Got it! Lets see what we found..." }
        logger.debug { "Verify ${response.code}" }
        logger.debug { response.body }
        return response.code == 200
    }

    companion object {
        private val logger = getLogger()
    }
}
