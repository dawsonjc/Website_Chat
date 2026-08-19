package com.brewery.web.frontend.account

import com.brewery.web.frontend.HelpFunctions
import com.brewery.web.frontend.user.{FormUser, User}
import io.udash.wrappers.jquery.{JQuery, JQueryAjaxSettings, JQueryXHR, jQ}
import org.scalajs.dom.{HTMLFormElement, Response, URL, window}

import java.util.UUID
import scala.scalajs.js
import scala.scalajs.js.{JSON, Promise}

object Login {
    
    def loginValidation(form: HTMLFormElement): Unit = {
        jQ("#email-error").remove();
        jQ("#password-error").remove();
        
        val loginForm: JQuery = jQ(form);
        val emailElement: JQuery = loginForm.find("#email")
        val passwordElement: JQuery = loginForm.find("#password")
        
        val formData: Map[String, Any] = HelpFunctions.getFormData( loginForm );
        val EmailPattern = raw"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$$".r
        val email: String = formData("email").toString;
        
        if(EmailPattern.findFirstIn(email).isEmpty) {
            val element: JQuery = jQ("<div>")
            
            element.attr("id", "email-error");
            element.addClass("mb-2 rounded-md border border-red-300 bg-red-50 px-3 py-2 text-sm font-medium text-red-700 shadow-sm");
            element.text("Please enter a valid email address");
            
            emailElement.removeClass(
                "border-gray-300 focus:ring-indigo-500 focus:border-indigo-500"
            ).addClass(
                "border-red-500 text-red-900 placeholder-red-300 " +
                "ring-1 ring-red-500 " +
                "focus:ring-2 focus:ring-red-500 focus:border-red-500 " +
                "shadow-[0_0_10px_rgba(239,68,68,0.45)]"
            )
            
            emailElement.before(element);
            return;
        } else {
            emailElement.removeClass(
                "border-red-500 text-red-900 placeholder-red-300 " +
                "ring-1 ring-red-500 focus:ring-2 focus:ring-red-500 " +
                "focus:border-red-500 " +
                "shadow-[0_0_10px_rgba(239,68,68,0.45)]"
            ).addClass(
                "border-gray-300 text-gray-900 placeholder-gray-500 " +
                "focus:ring-indigo-500 focus:border-indigo-500"
            )
        }
        if(formData("password").toString.length > 8) {
            // TODO: add password error
            return;
        } else {
        
        }
        
        val formUser: FormUser = new FormUser();
        formUser.username = HelpFunctions.cleanseString(formData("email").toString);
        formUser.password = formData("password").toString;
        
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
                case e: js.JavaScriptException => {}
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
