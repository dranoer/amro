package com.amro.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.amro.app.ui.theme.AmroTheme
import com.amro.app.ui.view.component.ErrorView
import org.junit.Rule
import org.junit.Test

class MovieScreenUiTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun given_error_state_when_screen_shown_then_error_visible_and_retry_works() {
        // given
        val errorMessage = "No internet connection."
        var retryCalled = false

        // when
        rule.setContent {
            AmroTheme {
                ErrorView(
                    message = errorMessage,
                    onRetryClick = { retryCalled = true }
                )
            }
        }

        // then
        rule.onNodeWithText(errorMessage).assertIsDisplayed()
        rule.onNodeWithText("Retry").assertIsDisplayed()

        rule.onNodeWithText("Retry").performClick()
        assert(retryCalled)
    }
}