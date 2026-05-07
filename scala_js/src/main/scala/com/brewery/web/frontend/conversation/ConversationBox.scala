package com.brewery.web.frontend.conversation

import com.brewery.web.frontend.HelpFunctions
import com.brewery.web.frontend.message.Message
import io.udash.wrappers.jquery.{EventName, JQuery, JQueryAjaxSettings, JQueryEvent, JQueryXHR, jQ}
import org.scalajs.dom
import org.scalajs.dom.{Element, Event, Response, URLSearchParams, window}

import java.util.UUID
import scala.scalajs.js
import scala.scalajs.js.{JSON, Promise, |}

object ConversationBox {
    val conversationId: String = (new URLSearchParams(window.location.search)).get("conversationId");
    def initialize(): Unit = {
        val $messages: JQuery = jQ("#chat-messages");
        $messages.on(EventName.scroll, (element: Element, jQueryEvent: JQueryEvent) => {
            if($messages.scrollTop() == 0) {
                jQ.ajax(js.Dynamic.literal(
                    url = "/message",
                    method = "GET",
                    success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                        prependMessages(data.asInstanceOf[js.Dynamic].data.asInstanceOf[js.Array[js.Dynamic]], $messages);
                    },
                    data = js.Dynamic.literal(
                        conversationId = conversationId,
                        beforeDate = $messages.first().find("input[name='message-date']").value().toString
                    )
                ).asInstanceOf[JQueryAjaxSettings])
            }
        });
        
        val $deleteMessage: JQuery = jQ(".delete-message");
        $deleteMessage.on(EventName.click, (element: Element, jQueryEvent: JQueryEvent) => {
            val $this = jQ(element);
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
        
        $messages.find(".message-date").each((element: Element, index: Int) => {
            val $this = jQ(element);
            
            val date = $this.html() + " UTC";
            $this.html(HelpFunctions.formatDate(new js.Date(date)));
        });
        
        initToolTip();
    }
    
    private def initToolTip(): Unit = {
        val $messages: JQuery = jQ("#chat-messages");
        
        val customTooltipCss: Map[String, String | Int | Double | Boolean] = Map(
            "position" -> "absolute",
            "z-index" -> 1000,
            "background" -> "#fff",
            "border" -> "1px solid #ccc",
            "box-shadow" -> "0 2px 5px rgba(0,0,0,0.2)",
            "padding" -> "10px",
            "border-radius" -> "4px"
        );
        
        val ulcss: Map[String, String | Int | Double | Boolean] = Map(
            "list-style" -> "none",
            "padding" -> 0,
            "margin" -> 0
        );
        
        val liCss: Map[String, String | Int | Double | Boolean] = Map(
            "padding" -> "5px 10px",
            "cursor" -> "pointer"
        );
        
        val liHover: Map[String, String | Int | Double | Boolean] = Map(
            "background-color" -> "#f8f9fa"
        );
        
        $messages.on(event = EventName.contextMenu, selector = ".user-message-box", callback = (element: Element, event: JQueryEvent) => {
            event.preventDefault();
            
            val $this: JQuery = jQ(element);
            
            jQ("#tool-tip").remove();
            
            val $toolTip: JQuery = jQ(
                """<div id="tool-tip">
                  | <ul>
                  |     <li data-action="reply">Reply</li>
                  |     <li data-action="add-friend">Add Friend</li>
                  | </ul>
                  |</div>""".stripMargin);
            
            val positionCss: Map[String, String | Int | Double | Boolean] = Map(
                "top" -> f"${event.pageY}px",
                "left" -> f"${event.pageX}px"
            );
            
            val toolTipCss: Map[String, String | Int | Double | Boolean] = customTooltipCss ++ positionCss;
            $toolTip.css(toolTipCss);
            
            $toolTip.find("ul").css(ulcss);
            $toolTip.find("li").css(liCss);
            $toolTip.find("li").on(event = EventName.mouseEnter, callback = (element: Element, event: JQueryEvent) => {
                val $this: JQuery = jQ(element);
                
                val newCss: Map[String, String | Int | Double | Boolean] = liCss ++ liHover;
                
                $this.css(newCss);
                
            }).on(event = EventName.mouseLeave, callback = (element: Element, event: JQueryEvent) => {
                val $this: JQuery = jQ(element);
                
                $this.removeAttr("style");
                $this.css(liCss);
            });
            
            $toolTip.find("li[data-action='add-friend']").on(EventName.click, callback = (element: Element, event: JQueryEvent) => {
                implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global
                
                val userId: String = $this.find("input[name='user-id']").value().asInstanceOf[String]
                
                val res: JQueryXHR = jQ.ajax(js.Dynamic.literal(
                    url = "/add-friend",
                    method = "POST",
                    data = js.Dynamic.literal(
                        userId = userId
                    ),
                    success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                    
                    },
                    failure = (jqXHR: js.Dynamic, textStatus: String, errorThrown: String) => {
                    
                    }
                ).asInstanceOf[JQueryAjaxSettings]);
            });
            
            jQ("body").append($toolTip);
            
            jQ(window.document).one(EventName.click, (element: Element, event: JQueryEvent) => {
                $toolTip.remove();
            });
        });
    }
    
    private def prependMessages(messages: js.Array[js.Dynamic], messageBox: JQuery): Unit = {
        for(message <- messages) {
            val m = new Message(message);
            val $html = m.toJQueryElement();
            $html.find(".delete-message").on(EventName.click, (element: Element, jQueryEvent: JQueryEvent) => {
                val $this = jQ(element);
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
            
            messageBox.prepend($html);
        }
    }
}
