package com.brewery.web.frontend.dom

import com.brewery.web.frontend.HelpFunctions
import com.brewery.web.frontend.account.Login
import com.brewery.web.frontend.admin.Admin
import com.brewery.web.frontend.conversation.ConversationBox
import com.brewery.web.frontend.message.MessageEvent
import com.brewery.web.frontend.message.MessageEvent.buildMessageWebsocket
import io.udash.wrappers.jquery.{EventName, JQuery, JQueryAjaxSettings, JQueryCallback, JQueryEvent, JQueryXHR, jQ}
import org.scalajs.dom.{Element, HTMLFormElement, URLSearchParams, WebSocket, document, window}

import scala.annotation.static
import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.*
import scala.scalajs.js.annotation.JSExportTopLevel

object PageInitializers {
    
    def initializeHeader(): Unit = {
        // header
        jQ("#logout").on(EventName.click, (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault();
            
            jQ.ajax(js.Dynamic.literal(
                url = "/account/logout",
                method = "POST",
                success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => { 
                    window.location.href = "/";
                }
            ).asInstanceOf[JQueryAjaxSettings])
        })
        
    }
    
    def conversations(): Unit = {
        val messages: JQuery = jQ("#chat-messages");
        messages.scrollTop(messages.get(0).get.scrollHeight);
        
        val conversationId: String = (new URLSearchParams(window.location.search)).get("conversationId");
        val webSocket: WebSocket = new WebSocket(s"/communication?conversationId=${conversationId}&userId=${HelpFunctions.getCookie("User-Information").userId}");
        webSocket.onmessage = buildMessageWebsocket
        val sendMessageCallback: (Element, JQueryEvent) => scala.Any = (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault();
            
            MessageEvent.submitMessage(element.asInstanceOf[HTMLFormElement], webSocket);
        };
        jQ("#send-message").on(EventName.submit, (element: Element, jQueryEvent: JQueryEvent) => {
            sendMessageCallback(element, jQueryEvent);
        });
        
        ConversationBox.initialize();
    }
    
    def login(): Unit = {
        val loginFormValidation: (Element, JQueryEvent) => scala.Any = (element: Element, jQueryEvent: JQueryEvent) => {
            jQueryEvent.preventDefault();
            Login.loginValidation(element.asInstanceOf[HTMLFormElement]);
        };
        jQ("#login-form").on(EventName.submit, (element: Element, jQueryEvent: JQueryEvent) => {
            loginFormValidation(element, jQueryEvent);
        });
    }
    
    def register(): Unit = {
    
    }
    
    def admin(): Unit = {
        jQ.ajax(js.Dynamic.literal(
            url = "/account/roles",
            method = "POST",
            success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                val response: js.Dynamic = data.asInstanceOf[js.Dynamic];
                
                if(response.success.asInstanceOf[Boolean]) {
                    val roles: js.Array[String] = response.data.asInstanceOf[js.Array[String]];
                    
                    if(roles.contains("Admin")) {
                        Admin.init();
                    }
                }
            }
        ).asInstanceOf[JQueryAjaxSettings]);
    }
}
