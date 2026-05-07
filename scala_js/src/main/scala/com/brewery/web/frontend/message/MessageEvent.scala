package com.brewery.web.frontend.message

import com.brewery.web.frontend.HelpFunctions
import com.brewery.web.frontend.conversation.ConversationBox.conversationId
import io.udash.wrappers.jquery._
import org.scalajs.dom.{Element, HTMLFormElement, MessageEvent, URLSearchParams, WebSocket, console, document, window}

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation._

object MessageEvent {
    
    def submitMessage(form: HTMLFormElement, webSocket: WebSocket): Unit = {
        val formData: Map[String, Any] = HelpFunctions.getFormData(jQ(form));
        
        val inputString: String = formData("message-content").toString.trim;;
        
        if(inputString == null || inputString.isBlank) {
            return;
        }
        
        val message: Message = new Message();
        
        message.conversationId = conversationId;
        if(formData("to-user-id").toString != "" || formData("to-username").toString != "") {
            message.toUserId = formData("to-user-id").toString;
            message.toUsername = formData("to-username").toString;
        }
        message.fromUserId = formData("user-id").toString;
        message.content = HelpFunctions.cleanseString(inputString);
        
        webSocket.send(JSON.stringify(message));
        
        jQ("#message-input").value("");
    }
    
    @FunctionalInterface
    def buildMessageWebsocket(messageEvent: MessageEvent): Unit = {
        val messages: JQuery = jQ("#chat-messages");
        
        val response: js.Dynamic = JSON.parse(messageEvent.data.asInstanceOf[String]);
        
        if(response.message.toString == "deleted") {
            messages.find(f"input[value='${response.data.messageId}']").parent().parent().remove();
            return;
        }
        
        val message: Message = new Message(response.data.asInstanceOf[js.Dynamic]);
        
        val $message = message.toJQueryElement();
        $message.find(".message-content").html(message.content);
        $message.find(".delete-message").on(EventName.click, (element: Element, jQueryEvent: JQueryEvent) => {
            val $this: JQuery = jQ(element);
            val formData: Map[String, Any] = HelpFunctions.getFormData($this.parent().parent().find("form"));
            jQ.ajax(js.Dynamic.literal(
                url = "/message/delete",
                method = "DELETE",
                data = js.Dynamic.literal(
                    messageId = formData("message-id").toString
                ),
                success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                    $this.parent().parent().remove();
                }
            ).asInstanceOf[JQueryAjaxSettings])
        });
        
        messages.append($message);
    
        var animation: Map[String, Any] = Map[String, Any]()
        animation += "scrollTop" -> messages.prop("scrollHeight")
        messages.animate(animation, 50)
    }
}
