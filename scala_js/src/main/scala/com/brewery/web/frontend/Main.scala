package com.brewery.web.frontend

import com.brewery.web.frontend.dom.PageInitializers

object Main {
    private val methods: Map[String, () => Unit] = Map[String, () => Unit](
        "/conversations" -> PageInitializers.conversations,
        "/account/login" -> PageInitializers.login
    );
    
    def main(args: Array[String]): Unit = {
        val pathname: String = org.scalajs.dom.window.location.pathname;
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