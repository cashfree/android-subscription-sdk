import java.io.File
import java.io.FileInputStream
import java.util.*

object Config {

    var sandboxClientID: String = ""
    var sandboxClientSecret: String = ""
    var prodTestClientID: String = ""
    var prodTestClientSecret: String = ""

    @Throws(IllegalArgumentException::class)
    fun loadData(secretPropsFile: File) {
        val p = Properties()
        if (secretPropsFile.exists()) {
            // Read local.properties file first if it exists
            FileInputStream(secretPropsFile).use { p.load(it) }
        }

        sandboxClientID = getKey("SANDBOX_CLIENT_ID", p)
        sandboxClientSecret = getKey("SANDBOX_CLIENT_SECRET", p)
        prodTestClientID = getKey("PROD_TEST_CLIENT_ID", p)
        prodTestClientSecret = getKey("PROD_TEST_CLIENT_SECRET", p)
    }

    private fun getKey(name: String, properties: Properties, failOnError: Boolean = false): String {
        val resource = properties.getProperty(name) ?: System.getenv(name)
        if (resource == null && failOnError) {
            throw IllegalArgumentException("$name Key is not configured in local.properties or environment variables.")
        } else {
            return resource
        }
    }
}