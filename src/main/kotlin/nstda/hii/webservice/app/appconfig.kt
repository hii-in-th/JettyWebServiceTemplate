package nstda.hii.webservice.app

val constViduUser: String? = property("VIDU_USER") ?: "OPENVIDUAPP"

val constViduSecret: String = property("OPENVIDU_SECRET")
    ?: throw ExceptionInInitializerError("Can't search OPENVIDU_SECRET. Please specify OPENVIDU_SECRET in system env.")

// Redis configuration.
val constRedisHost: String = property("RE_HOST")
    ?: throw ExceptionInInitializerError("Can't search RE_HOST. Please specify RE_HOST in system env.")

val constRedisPort: Int = property("RE_PORT")?.toInt()
    ?: throw ExceptionInInitializerError("Can't search RE_PORT. Please specify RE_PORT in system env.")

val constRedisExpireSec: Int? = property("RE_EXPIRE_SEC")?.toInt()

// Mongo config
val constMongoUri: String? by lazy { property("MONGODB_URI") }

private fun property(keyName: String): String? {
    System.getProperty(keyName)?.let { return it.trim() }
    return System.getenv(keyName).trim()
}
