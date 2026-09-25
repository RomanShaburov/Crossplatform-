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
import wikidataentities.shared.generated.resources.*

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
                    TextButton(onClick = onToggleTheme) {
                        Text(
                            text = if (language == "ru") {
                                if (isDark) "Темная" else "Светлая"
                            } else {
                                if (isDark) "Dark" else "Light"
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    TextButton(onClick = onToggleLanguage) {
                        Text(
                            text = stringResource(if (language == "ru") Res.string.lang_ru else Res.string.lang_en),
                            style = MaterialTheme.typography.titleMedium
                        )
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(entities) { entity ->
                EntityListItem(
                    entity = entity,
                    onClick = { onEntityClick(entity.id) }
                )
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
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entity.label,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = entity.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}
