package nstda.hii.webservice

val constViduUser: String? by lazy { property("VIDU_USER") ?: "OPENVIDUAPP" }
val constViduSecret: String by lazy {
    property("OPENVIDU_SECRET")
        ?: throw ExceptionInInitializerError("Can't search OPENVIDU_SECRET. Please specify OPENVIDU_SECRET in system env.")
}

// Redis configuration.
val constRedisHost: String by lazy {
    property("RE_HOST")
        ?: throw ExceptionInInitializerError("Can't search RE_HOST. Please specify RE_HOST(Redis) in system env.")
}
val constRedisPort: Int by lazy {
    property("RE_PORT")?.toInt()
        ?: throw ExceptionInInitializerError("Can't search RE_PORT. Please specify RE_PORT(Redis) in system env.")
}
val constRedisExpireSec: Int? by lazy { property("RE_EXPIRE_SEC")?.toInt() }

// Mongo config
val constMongoUri: String? by lazy { property("MONGODB_URI") }

// Keycloak Open ID
val constKeycloakApiKey: String? by lazy { property("KEYCLOAK_KEY") }
val constKeycloakApiSecret: String? by lazy { property("KEYCLOAK_SECRET") }
val constKeycloakCallback: String? by lazy { property("KEYCLOAK_CALLBAK") }
val constKeycloakBaseUrl: String? by lazy { property("KEYCLOAK_BASE_URL") }
val constKeycloakRealm: String? by lazy { property("KEYCLOAK_REALM") }

private fun property(keyName: String): String? {
    System.getProperty(keyName)?.let { return it.trim() }
    return System.getenv(keyName).trim()
}
