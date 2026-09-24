package org.example.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.WikidataEntity
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.Res
import wikidataentities.shared.generated.resources.list_title
import wikidataentities.shared.generated.resources.label_id

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityListScreen(
    entities: List<WikidataEntity>,
    onEntityClick: (String) -> Unit,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    language: String,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.list_title)) },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Text(if (isDark) "☀️" else "🌙")
                    }
                    TextButton(onClick = onToggleLanguage) {
                        Text(language.uppercase())
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(entities) { entity ->
                EntityListItem(
                    entity = entity,
                    onClick = { onEntityClick(entity.id) }
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}

@Composable
fun EntityListItem(
    entity: WikidataEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entity.label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = entity.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${stringResource(Res.string.label_id)}: ${entity.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
