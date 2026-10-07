package org.example.project.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.*

/**
 * Шапка, общая для всех экранов: «назад», заголовок, переключатели темы и языка.
 * Одна на приложение — поэтому переключатели не надо протаскивать в каждый экран.
 *
 * @param onBack null — кнопки «назад» нет (мы на первом экране).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    onBack: (() -> Unit)?,
    darkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_arrow_back),
                                contentDescription = stringResource(Res.string.action_back),
                            )
                        }
                    }
                },
                actions = {
                    // На кнопке — код текущего языка. Он сам лежит в ресурсах:
                    // «RU» в values/, «EN» в values-en/.
                    TextButton(onClick = onToggleLanguage) {
                        Text(stringResource(Res.string.language_code))
                    }
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            painter = painterResource(
                                if (darkTheme) Res.drawable.ic_light_mode else Res.drawable.ic_dark_mode,
                            ),
                            contentDescription = stringResource(Res.string.action_toggle_theme),
                        )
                    }
                },
            )
        },
    ) { insets ->
        Box(Modifier.padding(insets)) {
            content(Modifier.fillMaxSize())
        }
    }
}
