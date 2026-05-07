package com.brewery.web.frontend.admin

import com.brewery.web.frontend.HelpFunctions
import io.udash.wrappers.jquery.{EventName, JQuery, JQueryAjaxSettings, JQueryEvent, JQueryXHR, jQ}
import org.scalajs.dom.Element

import scala.scalajs.js

object Admin {
    
    def init(): Unit = {
        jQ(".verify-user").on(EventName.click, (element: Element, jQueryEvent: JQueryEvent) => {
            val $this: JQuery = jQ(element);
            
            val formData = HelpFunctions.getFormData( $this.parent().find("form") );
            
            jQ.ajax(js.Dynamic.literal(
                url = "/admin/user/verify",
                method = "POST",
                data = js.Dynamic.literal(
                    userId = formData("userId").toString
                ),
                success = (data: js.Any, textStatus: String, jqXHR: JQueryXHR) => {
                    val response: js.Dynamic = data.asInstanceOf[js.Dynamic];
    
                    if(response.success.asInstanceOf[Boolean]) {
                        $this.parent().parent().remove();
                    }
                }
            ).asInstanceOf[JQueryAjaxSettings])
        });
    }

}
