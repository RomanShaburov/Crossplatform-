package org.example.project.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.model.ClaimUi
import org.example.project.ui.model.EntityDetailUi
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.*

/**
 * Экран детали. Как и список, получает состояние, а не ViewModel.
 * Колбэка нет: на экране нечего нажимать, «назад» — в общей шапке.
 */
@Composable
fun EntityDetailScreen(
    state: EntityDetailState,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is EntityDetailState.Content -> EntityDetailContent(
            entity = state.entity,
            modifier = modifier,
        )
        is EntityDetailState.NotFound -> Box(modifier.padding(24.dp), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(Res.string.error_not_found, state.entityId),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
private fun EntityDetailContent(
    entity: EntityDetailUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = entity.label,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = entity.id,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = entity.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (entity.aliases.isNotEmpty()) {
            Section(title = stringResource(Res.string.label_aliases)) {
                Text(
                    text = entity.aliases.joinToString(", "),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        if (entity.claims.isNotEmpty()) {
            Section(title = stringResource(Res.string.label_properties)) {
                entity.claims.forEachIndexed { index, claim ->
                    if (index > 0) HorizontalDivider()
                    ClaimRow(claim = claim)
                }
            }
        }

        entity.enwikiTitle?.let { title ->
            Section(title = stringResource(Res.string.label_wikipedia)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun Section(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        content()
    }
}

/** Строка свойства: подпись и значения через запятую. */
@Composable
private fun ClaimRow(claim: ClaimUi) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = propertyLabel(claim.propertyId),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = claim.values.joinToString(", "),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
