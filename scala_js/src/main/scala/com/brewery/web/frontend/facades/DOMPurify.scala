package com.brewery.web.frontend.facades

import org.scalajs.dom.Node
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/** Scala.js facade for the browser-global DOMPurify API.
  *
  * Load DOMPurify before the linked Scala.js application, for example:
  *
  *   <script src="https://cdn.jsdelivr.net/npm/dompurify@3/dist/purify.min.js"></script>
  */
@js.native
@JSGlobal("DOMPurify")
object DOMPurify extends js.Object {
    def sanitize(dirty: String): String = js.native
    def sanitize(dirty: String, config: DOMPurifyConfig): String = js.native
    def sanitize(dirty: Node): String = js.native
    def sanitize(dirty: Node, config: DOMPurifyConfig): String = js.native

    def setConfig(config: DOMPurifyConfig): Unit = js.native
    def clearConfig(): Unit = js.native

    def isValidAttribute(tag: String, attribute: String, value: String): Boolean = js.native

    val isSupported: Boolean = js.native
    val version: String = js.native
    val removed: js.Array[js.Any] = js.native
}

@js.native
trait DOMPurifyConfig extends js.Object {
    var ADD_ATTR: js.UndefOr[js.Array[String]] = js.native
    var ADD_DATA_URI_TAGS: js.UndefOr[js.Array[String]] = js.native
    var ADD_TAGS: js.UndefOr[js.Array[String]] = js.native
    var ADD_URI_SAFE_ATTR: js.UndefOr[js.Array[String]] = js.native

    var ALLOWED_ATTR: js.UndefOr[js.Array[String]] = js.native
    var ALLOWED_TAGS: js.UndefOr[js.Array[String]] = js.native
    var FORBID_ATTR: js.UndefOr[js.Array[String]] = js.native
    var FORBID_CONTENTS: js.UndefOr[js.Array[String]] = js.native
    var FORBID_TAGS: js.UndefOr[js.Array[String]] = js.native

    var ALLOW_ARIA_ATTR: js.UndefOr[Boolean] = js.native
    var ALLOW_DATA_ATTR: js.UndefOr[Boolean] = js.native
    var ALLOW_UNKNOWN_PROTOCOLS: js.UndefOr[Boolean] = js.native
    var FORCE_BODY: js.UndefOr[Boolean] = js.native
    var KEEP_CONTENT: js.UndefOr[Boolean] = js.native
    var SAFE_FOR_TEMPLATES: js.UndefOr[Boolean] = js.native
    var SAFE_FOR_XML: js.UndefOr[Boolean] = js.native
    var SANITIZE_DOM: js.UndefOr[Boolean] = js.native
    var SANITIZE_NAMED_PROPS: js.UndefOr[Boolean] = js.native
    var WHOLE_DOCUMENT: js.UndefOr[Boolean] = js.native

    var NAMESPACE: js.UndefOr[String] = js.native
    var PARSER_MEDIA_TYPE: js.UndefOr[String] = js.native
}

object DOMPurifyConfig {
    def apply(): DOMPurifyConfig =
        js.Dynamic.literal().asInstanceOf[DOMPurifyConfig]
}
