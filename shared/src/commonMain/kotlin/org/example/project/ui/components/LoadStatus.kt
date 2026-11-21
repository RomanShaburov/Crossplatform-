package org.example.project.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.repository.LoadFailure
import org.jetbrains.compose.resources.stringResource
import wikidataentities.shared.generated.resources.*

@Composable
fun LoadingStatus(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CircularProgressIndicator()
        Text(stringResource(Res.string.status_loading))
    }
}

@Composable
fun LoadError(failure: LoadFailure, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val message = when (failure) {
            LoadFailure.NETWORK -> Res.string.error_network
            LoadFailure.SERVER -> Res.string.error_server
            LoadFailure.RESPONSE -> Res.string.error_response
        }
        Text(stringResource(message), color = MaterialTheme.colorScheme.error)
        Button(onClick = onRetry) { Text(stringResource(Res.string.action_retry)) }
    }
}
