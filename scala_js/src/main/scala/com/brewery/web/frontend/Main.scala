package com.brewery.web.frontend

import com.brewery.web.frontend.dom.PageInitializers
import com.brewery.web.frontend.facades.DOMPurify

object Main {
    private val methods: Map[String, () => Unit] = Map[String, () => Unit](
        "/conversations" -> PageInitializers.conversations,
        "/account/login" -> PageInitializers.login,
        "/account/register" -> PageInitializers.register,
        "/admin" -> PageInitializers.admin
    );
    
    def main(args: Array[String]): Unit = {
        val pathname: String = org.scalajs.dom.window.location.pathname;
        org.scalajs.dom.window.console.log(DOMPurify.sanitize("""<img src=x onerror=alert("XSS")><b>Valid Text</b>"""));
        io.udash.wrappers.jquery.jQ(() => {
            PageInitializers.initializeHeader();
            
            methods.get(pathname) match {
                case Some(fn) => {
                    fn();
                    org.scalajs.dom.window.console.log("Page Initialized")
                }
                case None => org.scalajs.dom.window.console.log("Route not found")
            }
        });
    }
}