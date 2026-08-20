package com.brewery.web.frontend.account

import com.brewery.web.frontend.HelpFunctions
import io.udash.wrappers.jquery.{JQuery, JQueryAjaxSettings, JQueryXHR, jQ}
import org.scalajs.dom.{HTMLFormElement, URL, window}

import scala.scalajs.js

object Registration {
    def register(form: HTMLFormElement): Unit = {
        jQ("#email-error").remove();
        jQ("#password-error").remove();
        
        val registerForm: JQuery = jQ(form);
        val emailElement: JQuery = registerForm.find("#email")
        val passwordElement: JQuery = registerForm.find("#password")
        
        val formData: Map[String, Any] = HelpFunctions.getFormData(registerForm);
        val email: String = formData("email").toString;
        val password: String = formData("password").toString;
        
        val EmailPattern = raw"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$$".r
        if (EmailPattern.findFirstIn(email).isEmpty) {
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
        val validatePassword: Map[String, (String) => (Boolean, String)] = Map[String, (String) => (Boolean, String)](
            "lengthCheck" -> ((pass: String) => {
                val length: Int = pass.codePointCount(0, pass.length);
                
                var message: String = "";
                val check: Boolean = length >= 8 && length <= 128;
                if (!check) {
                    message = "Password must be between 8 and 128 characters";
                }
                (check, message)
            }),
        
        )
        val messages: List[String] = validatePassword.map(entry => {
            val values: (Boolean, String) = entry._2(password)
            var message: String = "";
            if (!values._1) {
                message = values._2;
            }
            message
        }).toList
        
        if (messages.exists(_.nonEmpty)) {
            val element: JQuery = jQ("<div>")
            
            element.attr("id", "password-error");
            element.addClass("mb-2 rounded-md border border-red-300 bg-red-50 px-3 py-2 text-sm font-medium text-red-700 shadow-sm");
            element.text(messages.mkString(", "));
            
            passwordElement.removeClass(
                "border-gray-300 focus:ring-indigo-500 focus:border-indigo-500"
            ).addClass(
                "border-red-500 text-red-900 placeholder-red-300 " +
                    "ring-1 ring-red-500 " +
                    "focus:ring-2 focus:ring-red-500 focus:border-red-500 " +
                    "shadow-[0_0_10px_rgba(239,68,68,0.45)]"
            )
            
            passwordElement.before(element);
            return;
        } else {
            passwordElement.removeClass(
                "border-red-500 text-red-900 placeholder-red-300 " +
                    "ring-1 ring-red-500 focus:ring-2 focus:ring-red-500 " +
                    "focus:border-red-500 " +
                    "shadow-[0_0_10px_rgba(239,68,68,0.45)]"
            ).addClass(
                "border-gray-300 text-gray-900 placeholder-gray-500 " +
                    "focus:ring-indigo-500 focus:border-indigo-500"
            )
        }
        
        val res = jQ.ajax(js.Dynamic.literal(
            url = "/account/register",
            method = "POST",
            data = registerForm.serialize(),
            success = registerSuccess,
            error = registerFailure
        ).asInstanceOf[JQueryAjaxSettings]);
    }
    
    private def registerSuccess(data: js.Any, status: String, jqXHR: JQueryXHR): Unit = {
        val url: URL = new URL(window.location.href);
        window.location.href = url.origin + "/account/login";
    }
    
    private def registerFailure(jqXHR: JQueryXHR, textStatus: String, errorThrown: String): Unit = {
    
    }
    
}
