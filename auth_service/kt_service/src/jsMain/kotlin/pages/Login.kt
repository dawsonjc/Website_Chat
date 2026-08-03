package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.events.Event
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.RequestInit
import kotlin.js.JSON
import kotlin.js.json

@Page("/login")
@Composable
fun LoginPage() {
    val scope = rememberCoroutineScope()

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }

    DisposableEffect(isSubmitting) {
        val form = document.getElementById("login-form") as? HTMLFormElement

        val submitHandler: (Event) -> Unit = handler@{ event ->
            event.preventDefault()

            if (isSubmitting) {
                return@handler
            }

            val emailInput = document.getElementById("email") as? HTMLInputElement
                ?: return@handler

            val passwordInput = document.getElementById("password") as? HTMLInputElement
                ?: return@handler

            val email = emailInput.value.trim()
            val password = passwordInput.value

            scope.launch {
                isSubmitting = true
                errorMessage = null

                try {
                    val payload = json(
                        "username" to email,
                        "password" to password
                    )

                    val response = window.fetch(
                        "/account/login",
                        RequestInit(
                            method = "POST",
                            headers = json(
                                "Content-Type" to "application/json"
                            ),
                            body = JSON.stringify(payload)
                        )
                    ).await()

                    val body = response.json().await().asDynamic()

                    if (response.ok && body.success == true) {
                        window.location.href = "/"
                    } else {
                        errorMessage =
                            body.message?.toString()
                                ?: body.data?.errors?.toString()
                                        ?: "Invalid email or password."
                    }
                } catch (_: Throwable) {
                    errorMessage = "Unable to sign in. Please try again."
                } finally {
                    isSubmitting = false
                }
            }
        }

        form?.addEventListener("submit", submitHandler)

        onDispose {
            form?.removeEventListener("submit", submitHandler)
        }
    }

    Div(
        attrs = {
            classes(
                "min-h-screen",
                "bg-slate-950",
                "flex",
                "items-center",
                "justify-center",
                "px-4",
                "py-12"
            )
        }
    ) {
        Div(
            attrs = {
                classes(
                    "w-full",
                    "max-w-md",
                    "rounded-2xl",
                    "bg-white",
                    "p-8",
                    "shadow-2xl"
                )
            }
        ) {
            H1(
                attrs = {
                    classes(
                        "text-3xl",
                        "font-bold",
                        "text-center",
                        "text-slate-900"
                    )
                }
            ) {
                Text("Sign in")
            }

            P(
                attrs = {
                    classes(
                        "mt-2",
                        "mb-8",
                        "text-center",
                        "text-sm",
                        "text-slate-500"
                    )
                }
            ) {
                Text("Welcome back. Please enter your details.")
            }

            errorMessage?.let { message ->
                Div(
                    attrs = {
                        classes(
                            "mb-5",
                            "rounded-lg",
                            "border",
                            "border-red-200",
                            "bg-red-50",
                            "px-4",
                            "py-3",
                            "text-sm",
                            "text-red-700"
                        )
                    }
                ) {
                    Text(message)
                }
            }

            Form(
                attrs = {
                    attr("id", "login-form")
                    classes("space-y-5")
                }
            ) {
                Div {
                    Label(
                        forId = "email",
                        attrs = {
                            classes(
                                "mb-2",
                                "block",
                                "text-sm",
                                "font-medium",
                                "text-slate-700"
                            )
                        }
                    ) {
                        Text("Email")
                    }

                    Input(
                        type = InputType.Email,
                        attrs = {
                            attr("id", "email")
                            attr("name", "email")
                            attr("placeholder", "you@example.com")
                            attr("autocomplete", "email")
                            required()

                            if (isSubmitting) {
                                disabled()
                            }

                            classes(
                                "block",
                                "w-full",
                                "rounded-lg",
                                "border",
                                "border-slate-300",
                                "px-4",
                                "py-3",
                                "text-slate-900",
                                "outline-none",
                                "placeholder:text-slate-400",
                                "focus:border-indigo-500",
                                "focus:ring-2",
                                "focus:ring-indigo-500/20"
                            )
                        }
                    )
                }

                Div {
                    Label(
                        forId = "password",
                        attrs = {
                            classes(
                                "mb-2",
                                "block",
                                "text-sm",
                                "font-medium",
                                "text-slate-700"
                            )
                        }
                    ) {
                        Text("Password")
                    }

                    Input(
                        type = InputType.Password,
                        attrs = {
                            attr("id", "password")
                            attr("name", "password")
                            attr("placeholder", "Enter your password")
                            attr("autocomplete", "current-password")
                            required()

                            if (isSubmitting) {
                                disabled()
                            }

                            classes(
                                "block",
                                "w-full",
                                "rounded-lg",
                                "border",
                                "border-slate-300",
                                "px-4",
                                "py-3",
                                "text-slate-900",
                                "outline-none",
                                "placeholder:text-slate-400",
                                "focus:border-indigo-500",
                                "focus:ring-2",
                                "focus:ring-indigo-500/20"
                            )
                        }
                    )
                }

                Button(
                    attrs = {
                        type(ButtonType.Submit)

                        if (isSubmitting) {
                            disabled()
                            classes("opacity-70", "cursor-not-allowed")
                        }

                        classes(
                            "w-full",
                            "rounded-lg",
                            "bg-indigo-600",
                            "px-4",
                            "py-3",
                            "text-sm",
                            "font-semibold",
                            "text-white",
                            "hover:bg-indigo-500",
                            "focus:outline-none",
                            "focus:ring-2",
                            "focus:ring-indigo-500",
                            "focus:ring-offset-2"
                        )
                    }
                ) {
                    Text(if (isSubmitting) "Signing in..." else "Sign in")
                }
            }
        }
    }
}