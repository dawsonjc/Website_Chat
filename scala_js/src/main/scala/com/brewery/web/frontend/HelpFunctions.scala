package com.brewery.web.frontend

import io.udash.wrappers.jquery.JQuery
import org.scalajs.dom.document

import scala.scalajs.js
import org.scalajs.dom.intl.{ DateTimeFormat, DateTimeFormatOptions }
import scala.scalajs.js.{ Date, JSON }
import scala.scalajs.js.URIUtils

object HelpFunctions {
    
    def getFormData(form: JQuery): Map[String, Any] = {
        val unindexedArray: js.Array[Any] = form.serializeArray();
        var indexedMap: Map[String, Any] = Map[String, Any]();
        
        for(i <- 0 until unindexedArray.length) {
            val obj: js.Dynamic = unindexedArray(i).asInstanceOf[js.Dynamic];
            val name: String = obj.name.toString
            val value: String = obj.value.toString
            
            if(!indexedMap.contains(name)) {
                indexedMap += (name -> value);
            } else if(indexedMap.contains(name) && !indexedMap(name).isInstanceOf[Seq[?]]) {
                indexedMap += (name -> Seq(indexedMap(name), value));
            } else if(indexedMap.contains(name) && indexedMap(name).isInstanceOf[Seq[?]]) {
                indexedMap += (name -> (indexedMap(name).asInstanceOf[Seq[String]].appended(value)))
            }
        }
        
        return indexedMap;
    }
    
    def cleanseString(string: String): String = {
        var retString = string.replaceAll("<", "&lt;");
        retString = retString.replaceAll(">", "&gt;");
        retString = retString.replaceAll("\"", "&#34;");
        retString = retString.replaceAll("'", "&#39;");
        
        return retString;
    }
    
    def formatDate(date: Date): String = {
        val dt: DateTimeFormat = new DateTimeFormat("en-US", js.Dynamic.literal(
            year = "numeric",
            month = "2-digit",
            day = "2-digit",
            hour = "2-digit",
            minute = "2-digit",
            hour12 = true
        ).asInstanceOf[DateTimeFormatOptions]);
        val dateFormatted: String = dt.format(date);
        
        return dateFormatted;
    }
    
    def parseToUTCDateTime(date: Date): String = {
        val options: js.Dynamic = js.Dynamic.literal(
            timeZone = "UTC",
            year = "numeric",
            month = "2-digit",
            day = "2-digit",
            hour = "2-digit",
            minute = "2-digit",
            hour12 = true
        );
        
        // Format using Intl.DateTimeFormat with UTC
        val formatter = js.Dynamic.global.Intl
            .DateTimeFormat("en-US", options)
        
        return formatter.format(date).asInstanceOf[String].replaceAll(",", "")
    }
    
    def setCookie(name: String, obj: js.Any, days: Option[Int]): Unit = {
        val jsonString = JSON.stringify(obj)
        val encodedValue: String = URIUtils.encodeURIComponent(jsonString)
        val expires: String = days match {
            case Some(d) =>
                val date = new Date()
                date.setTime(date.getTime() + (d * 24 * 60 * 60 * 1000))
                s"; expires=${date.toUTCString()}"
            case None =>
                ""
        }
        document.cookie = s"$name=$encodedValue$expires; path=/"
    }
    
    def getCookie(name: String): js.Dynamic = {
        val value: String = s"; ${document.cookie}";
        val parts: Array[String] = value.split("; " + name + "=");
        if(parts.length == 2) {
            return JSON.parse(URIUtils.decodeURIComponent(parts(1).split(";").head));
        }
        return null;
    }
}