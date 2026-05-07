package com.brewery.web.frontend.dom

import org.scalajs.dom.WebSocket

object SocketManager {
    
    class Notification(
                      val eventId: String,
                      val bruh: String
                      
    ) {
        
    }
    
    
    var messageSocket: Option[WebSocket] = None;
    var eventSocket: Option[WebSocket] = None;
    
    
    
    def connectMessage(): Unit = {
        
    }
    
    
}
