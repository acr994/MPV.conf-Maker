package com.acr.mpvconfmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acr.mpvconfmaker.data.mpv.AndroidSupport
import com.acr.mpvconfmaker.data.mpv.MpvOption
import com.acr.mpvconfmaker.data.mpv.MpvOptionCatalog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MpvConfMakerApp()
        }
    }
}

@Composable
private fun MpvConfMakerApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            HomeScreen()
        }
    }
}

@Composable
private fun HomeScreen() {
    var query by rememberSaveable { mutableStateOf("") }
    val selectedSupports = remember { mutableStateMapOf<AndroidSupport, Boolean>() }
    val expandedSections = remember {
        mutableStateMapOf<String, Boolean>().apply {
            optionSections.forEach { section -> put(section.title, true) }
        }
    }
    val activeSupports = selectedSupports.filterValues { it }.keys
    val filteredOptions = remember(query, selectedSupports.toMap()) {
        MpvOptionCatalog.androidRelevantOptions.filter { option ->
            val matchesQuery = query.isBlank() ||
                option.key.contains(query, ignoreCase = true) ||
                option.shortDescription.contains(query, ignoreCase = true)
            val matchesSupport = activeSupports.isEmpty() || option.androidSupport in activeSupports
            matchesQuery && matchesSupport
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "MPV.conf-Maker",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Generador visual de mpv.conf para Android",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                label = { Text("Buscar por key o descripción") },
            )
        }
        item {
            SupportFilters(
                selectedSupports = selectedSupports,
                onToggle = { support -> selectedSupports[support] = !(selectedSupports[support] ?: false) },
            )
        }
        items(optionSections, key = { it.title }) { section ->
            val sectionOptions = filteredOptions.filter { it.key in section.optionKeys }
            OptionSectionCard(
                title = section.title,
                options = sectionOptions,
                expanded = expandedSections[section.title] ?: true,
                onToggle = { expandedSections[section.title] = !(expandedSections[section.title] ?: true) },
            )
        }
    }
}

@Composable
private fun SupportFilters(
    selectedSupports: Map<AndroidSupport, Boolean>,
    onToggle: (AndroidSupport) -> Unit,
) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        filterSupports.forEach { support ->
            FilterChip(
                selected = selectedSupports[support] == true,
                onClick = { onToggle(support) },
                label = { Text(support.name) },
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
private fun OptionSectionCard(
    title: String,
    options: List<MpvOption>,
    expanded: Boolean,
    onToggle: () -> Unit,
) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                TextButton(onClick = onToggle) {
                    Text(if (expanded) "Ocultar (${options.size})" else "Mostrar (${options.size})")
                }
            }
            if (expanded) {
                if (options.isEmpty()) {
                    Text(
                        text = "Sin opciones para los filtros actuales.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        options.forEach { option -> OptionItem(option = option) }
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionItem(option: MpvOption) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = option.key, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(text = option.shortDescription, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Default: ${option.defaultValue ?: "no documentado"}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Android: ${option.androidSupport.name}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Riesgo: ${option.riskLevel.name}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

private data class UiOptionSection(
    val title: String,
    val optionKeys: Set<String>,
)

private val filterSupports = listOf(
    AndroidSupport.RecommendedAndroid,
    AndroidSupport.CompatibleAdvanced,
    AndroidSupport.NotRecommendedAndroid,
    AndroidSupport.DesktopOnly,
)

private val optionSections = listOf(
    UiOptionSection("Video Output", setOf("vo")),
    UiOptionSection("Decoding", setOf("hwdec", "deinterlace", "framedrop", "vid")),
    UiOptionSection("Scaling", setOf("scale", "dscale")),
    UiOptionSection("Interpolation", setOf("video-sync", "interpolation", "tscale")),
    UiOptionSection("Cache / Network", setOf("cache", "demuxer-max-bytes", "demuxer-max-back-bytes", "ytdl")),
    UiOptionSection("Color / HDR", setOf("deband", "deband-iterations", "deband-threshold")),
    UiOptionSection("Audio", setOf("audio-file-auto", "alang", "aid", "volume", "volume-max", "mute", "audio-delay")),
    UiOptionSection("Subtitles", setOf("sub-auto", "slang", "sid", "sub-delay", "sub-scale", "sub-font-size", "sub-color", "sub-border-color", "sub-border-size", "sub-shadow-offset", "sub-ass-override", "sub-ass-style-overrides")),
    UiOptionSection("Advanced", setOf("profile", "speed", "loop-file", "save-position-on-quit")),
)

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MpvConfMakerApp()
}
