package com.brewery.web.frontend.message

import com.brewery.web.frontend.HelpFunctions
import io.udash.wrappers.jquery.{JQuery, jQ}
import org.scalajs.dom.console

import scala.scalajs.js
import scala.scalajs.js.JSON

class Message(messageJson: js.Dynamic = null) extends js.Object {
    var messageId: String = "";
    var updateDate: js.Date = null;
    var createDate: js.Date = null;
    var status: String = "";
    var conversationId: String = "";
    var fromUserId: String = "";
    var fromUsername: String = "";
    var toUserId: String = "";
    var toUsername: String = "";
    var content: String = "";
    
    if(messageJson != null) {
        if(messageJson.messageId != null) {
            messageId = messageJson.messageId.toString();
        }
        if(messageJson.createDate != null) {
            val sCreateDate: String = messageJson.createDate.toString();
            if(sCreateDate.contains("Z")) {
                createDate = new js.Date(sCreateDate);
            } else {
                createDate = new js.Date(sCreateDate + " UTC");
            }
        }
        if(messageJson.updateDate != null) {
            val sUpdateDate: String = messageJson.updateDate.toString();
            if(sUpdateDate.contains("Z")) {
                updateDate = new js.Date(sUpdateDate);
            } else {
                updateDate = new js.Date(sUpdateDate + " UTC");
            }
        }
        if(messageJson.status != null) {
            status = messageJson.status.toString();
        }
        if(messageJson.conversationId != null) {
            conversationId = messageJson.conversationId.toString();
        }
        if(messageJson.fromUserId != null) {
            fromUserId = messageJson.fromUserId.toString();
        }
        if(messageJson.fromUsername != null) {
            fromUsername = messageJson.fromUsername.toString();
        }
        if(messageJson.toUserId != null) {
            toUserId = messageJson.toUserId.toString();
        }
        if(messageJson.toUsername != null) {
            toUsername = messageJson.toUsername.toString();
        }
        if(messageJson.content != null) {
            content = messageJson.content.toString();
        }
    }
    
    def toJQueryElement(): JQuery = {
        val block: JQuery = jQ(f"""
      |            <div class="my-4 border border-transparent rounded-lg hover:border hover:border-black hover:rounded-lg hover:bg-darker-jakarta hover:transition hover:duration-75 relative">
      |                <form style="display: none">
      |                    <input type="hidden" name="user-id" value="${this.fromUserId}">
      |                    <input type="hidden" name="message-id" value="${this.messageId}">
      |                    <input type="hidden" name="message-date" value="${HelpFunctions.parseToUTCDateTime(createDate)}">
      |                </form>
      |                <div class="p-2 text-desert-storm information">
      |                    ${this.fromUsername}: ${HelpFunctions.formatDate(this.createDate)}
      |                    <!-- Trash icon in top right -->
      |
      |                </div>
      |                <div class="p-2 rounded-lg text-desert-storm break-all">${this.content}</div>
      |             </div>
           """.stripMargin)
        
        val userInformation: js.Dynamic = HelpFunctions.getCookie("User-Information");
        
        if(userInformation.userId.toString == this.fromUserId || userInformation.roles.asInstanceOf[js.Array[String]].contains("Admin")) {
            block.find(".information").append(f"""
           |     <div class="absolute top-2 right-2 cursor-pointer delete-message">
           |         <i class="fas fa-trash-alt text-desert-storm hover:text-red-500"></i>
           |     </div>
                """.stripMargin);
        }
        
        return block;
    }
    
}
