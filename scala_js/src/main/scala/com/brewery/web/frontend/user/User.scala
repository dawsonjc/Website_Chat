package com.brewery.web.frontend.user

import java.util.UUID
import java.util.HashSet
import scala.scalajs.js;

class User {
    var userId: Option[UUID] = None;
    var username: String = "";
    var blockedUsers: HashSet[UUID] = new HashSet[UUID];
    var accountStatus: String = "";
    
    var roles: js.Array[String] = new js.Array[String];
    
    def toJsObject: scalajs.js.Dynamic = {
        return scalajs.js.Dynamic.literal(
            userId = this.userId.get.toString,
            username = this.username,
            roles = this.roles
        )
    }
}
