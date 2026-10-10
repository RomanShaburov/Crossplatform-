package org.example.project.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.list.EntityPlaceholder
import org.example.project.ui.model.ClaimUi
import org.example.project.ui.model.ClaimValueUi
import org.example.project.ui.model.EntityDetailUi
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.*

@Composable
fun EntityDetailScreen(
    state: EntityDetailState,
    onIntent: (EntityDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state) {
        is EntityDetailState.Content -> EntityDetailContent(
            entity = state.entity,
            onEntityClick = { id -> onIntent(EntityDetailIntent.RelatedEntityClicked(id)) },
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
    onEntityClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EntityPlaceholder(label = entity.label)
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
            }
        }
        Text(
            text = entity.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

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
                    ClaimRow(claim = claim, onEntityClick = onEntityClick)
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

@Composable
private fun ClaimRow(
    claim: ClaimUi,
    onEntityClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = propertyLabel(claim.propertyId),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            claim.values.forEach { value ->
                when (value) {
                    is ClaimValueUi.Link -> SuggestionChip(
                        onClick = { onEntityClick(value.entityId) },
                        label = { Text(value.label) },
                    )
                    is ClaimValueUi.Plain -> Text(
                        text = value.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
