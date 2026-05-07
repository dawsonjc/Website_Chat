package com.brewery.web.frontend.user

import scala.scalajs.js

class FormUser(userObject: js.Dynamic = null) extends js.Object  {
    var username: String = ""
    var password: String = "";
    
    if(userObject != null) {
        if(userObject.username != null) {
            this.username = userObject.username.toString;
        }
        if(userObject.password != null) {
            this.password = userObject.password.toString;
        }
    }
    
}
