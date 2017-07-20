package at.hschroedl.pages.web.rest.util

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders

/**
 * Utility class for HTTP headers creation.
 */
class HeaderUtil {

    companion object {

        private val log = LoggerFactory.getLogger(HeaderUtil::class.java)

        val ERROR_HEADER = "X-pagesApp-error"

        val PARAMS_HEADER = "X-pagesApp-params"
        val ALERT_HEADER = "X-pagesApp-alert"


        @JvmStatic
        fun createAlert(message: String, param: String): HttpHeaders {
            val headers = HttpHeaders()
            headers.add(ALERT_HEADER, message)
            headers.add(PARAMS_HEADER, param)
            return headers
        }

        @JvmStatic
        fun createEntityCreationAlert(entityName: String, param: String): HttpHeaders {
            return createAlert("A new $entityName is created with identifier $param", param)
        }

        @JvmStatic
        fun createEntityUpdateAlert(entityName: String, param: String): HttpHeaders {
            return createAlert("A $entityName is updated with identifier $param", param)
        }

        @JvmStatic
        fun createEntityDeletionAlert(entityName: String, param: String): HttpHeaders {
            return createAlert("A $entityName is deleted with identifier $param", param)
        }

        @JvmStatic
        fun createFailureAlert(entityName: String, errorKey: String, defaultMessage: String): HttpHeaders {
            log.error("Entity processing failed, {}", defaultMessage)
            val headers = HttpHeaders()
            headers.add(ERROR_HEADER, defaultMessage)
            headers.add(PARAMS_HEADER, entityName)
            return headers
        }
    }
}
