package com.brewery.web.frontend.account

import com.brewery.web.frontend.HelpFunctions
import com.brewery.web.frontend.user.{FormUser, User}
import io.udash.wrappers.jquery.{JQueryAjaxSettings, JQueryXHR, jQ}
import org.scalajs.dom.{HTMLFormElement, Response, URL, window}

import java.util.UUID
import scala.scalajs.js
import scala.scalajs.js.{JSON, Promise}

object Login {
    
    def loginValidation(form: HTMLFormElement): Unit = {
        val formData: Map[String, Any] = HelpFunctions.getFormData( jQ(form) );
    
        val formUser: FormUser = new FormUser();
        formUser.username = HelpFunctions.cleanseString(formData("email").toString);
        formUser.password = HelpFunctions.cleanseString(formData("password").toString);
        
        val res = jQ.ajax(js.Dynamic.literal(
            url = "/account/login",
            method = "POST",
            contentType = "application/json",
            data = JSON.stringify(formUser),
            success = loginSuccess,
            error = loginFailure
        ).asInstanceOf[JQueryAjaxSettings]).asInstanceOf[Promise[Response]];
    }
    
    private def loginSuccess(data: js.Any, status: String, jqXHR: JQueryXHR) : Unit = {
        val response = data.asInstanceOf[js.Dynamic];
        if(response.success.asInstanceOf[Boolean]) {
            val userData: js.Dynamic = response.data;
            
            val user: User = new User();
            user.userId = Some(UUID.fromString(userData.userId.toString));
            user.username = userData.username.toString;
            user.roles = userData.roles.asInstanceOf[js.Array[String]];
            
            window.console.log(user)
            try {
                HelpFunctions.setCookie("User-Information", user.toJsObject, Some(1));
            } catch {
                case e: js.JavaScriptException => {
                
                }
            }
            
            val url: URL = new URL(window.location.href);
            window.location.href = url.origin;
        }
    }
    
    private def loginFailure(jqXHR: JQueryXHR, textStatus: String, errorThrown: String): Unit = {
        val xhr: js.Dynamic = jqXHR.asInstanceOf[js.Dynamic];
        val jsonResponse: js.Dynamic = xhr.responseJson;
    
        val errors: js.Dynamic = jsonResponse.data.errors;
    
    
    }
}
