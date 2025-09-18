package app.lilaverse.astrostatsandroid

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.lilaverse.astrostatsandroid.SouthNodeStorytellerService
import app.lilaverse.astrostatsandroid.SouthNodeStorytellerService.AIProvider
import app.lilaverse.astrostatsandroid.chat.longhorns
import app.lilaverse.astrostatsandroid.model.Chart

private data class ProviderOption(
    val label: String,
    val provider: AIProvider,
    val description: String,
    val apiKeyResolver: () -> String
)

private val genderOptions = listOf("Female", "Male", "Non-binary")

private val timePeriodOptions = listOf(
    "Ancient Egypt",
    "Mesopotamia",
    "Ancient Greece",
    "Ancient Rome",
    "Persian Empire",
    "Ancient China",
    "Mesoamerica",
    "Indus Valley Civilization",
    "African Kingdoms",
    "Medieval Europe",
    "Viking Age",
    "Byzantine Empire",
    "Mongol Empire",
    "Islamic Golden Age",
    "Feudal Japan",
    "Renaissance Italy",
    "Ottoman Empire",
    "Victorian England",
    "Spanish Conquest of the Americas",
    "Age of Exploration (1400s-1600s)",
    "French Revolution & Napoleonic Era",
    "Elizabethan England",
    "Industrial Revolution",
    "Antebellum South",
    "American Wild West",
    "Russian Empire",
    "British Raj (Colonial India)",
    "World War I",
    "Roaring Twenties & Prohibition",
    "World War II",
    "Pacific Islander Cultures",
    "Pre-Columbian North American Tribes",
    "Tang/Song Dynasty China",
    "Medieval Islamic Spain (Al-Andalus)",
    "Colonial America (1600s-1700s)",
    "Ancient Persia (Achaemenid/Sassanid Dynasties)",
    "Custom era"
)

private val storyStyleOptions = listOf(
    "Hopeful & Uplifting",
    "Dark & Gritty Drama",
    "Romantic & Tragic",
    "Adventure & Survival",
    "Philosophical & Reflective",
    "Children's Story"
)

private val lengthOptions = listOf(
    "Short" to "~400 words",
    "Medium" to "~750 words",
    "Long" to "~1100 words"
)

private val providerOptions = listOf(
    ProviderOption("Claude", AIProvider.CLAUDE, "Balanced insight & poetry") { longhorns.ANTHROPIC },
    ProviderOption("GPT-4", AIProvider.OPENAI, "Expansive storytelling") { longhorns.OPENAI },
    ProviderOption("Gemini", AIProvider.GEMINI, "Fast vivid imagery") { longhorns.GEMINI },
    ProviderOption("Grok", AIProvider.GROK, "Edgy mythic tension") { longhorns.GROK },
    ProviderOption("Plex", AIProvider.PERPLEXITY, "Concise cinematic detail") { longhorns.PERPLEXITY }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SouthNodeStoryScreen(chart: Chart, chartCake: ChartCake) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val mainHandler = remember { Handler(Looper.getMainLooper()) }

    var selectedGender by remember { mutableStateOf(genderOptions.first()) }
    var selectedTimePeriod by remember { mutableStateOf(timePeriodOptions.first()) }
    var customTimePeriod by remember { mutableStateOf("") }
    var selectedStyle by remember { mutableStateOf(storyStyleOptions[1]) }
    var selectedLength by remember { mutableStateOf(lengthOptions.first()) }
    var selectedProvider by remember { mutableStateOf(providerOptions.first()) }

    var generatedStory by remember { mutableStateOf<String?>(null) }
    var isGenerating by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var validationError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "South Node Storyteller",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "Channel a past-life narrative rooted in the karmic wisdom of ${chart.name}.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.padding(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DropdownSelector(
                    label = "Character gender",
                    options = genderOptions,
                    value = selectedGender,
                    onSelected = { selectedGender = it }
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Historical era", fontWeight = FontWeight.Medium)
                    DropdownSelector(
                        label = "Choose era",
                        options = timePeriodOptions,
                        value = selectedTimePeriod,
                        onSelected = { selectedTimePeriod = it }
                    )
                    if (selectedTimePeriod == "Custom era") {
                        OutlinedTextField(
                            value = customTimePeriod,
                            onValueChange = { customTimePeriod = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Describe your custom era") },
                            supportingText = { Text("Example: Mystical Alexandria circa 50 BCE") }
                        )
                    }
                }

                DropdownSelector(
                    label = "Story tone",
                    options = storyStyleOptions,
                    value = selectedStyle,
                    onSelected = { selectedStyle = it }
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Story length", fontWeight = FontWeight.Medium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        lengthOptions.forEach { option ->
                            FilterChip(
                                selected = selectedLength == option,
                                onClick = { selectedLength = option },
                                label = { Text("${option.first} (${option.second})") }
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "AI provider", fontWeight = FontWeight.Medium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        providerOptions.forEach { option ->
                            FilterChip(
                                selected = selectedProvider == option,
                                onClick = { selectedProvider = option },
                                label = { Text(option.label) }
                            )
                        }
                    }
                    Text(
                        text = selectedProvider.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(12.dp))

        validationError?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.padding(4.dp))
        }

        Button(
            onClick = {
                if (isGenerating) return@Button

                val eraText = if (selectedTimePeriod == "Custom era") customTimePeriod.trim() else selectedTimePeriod
                if (eraText.isEmpty()) {
                    validationError = "Please describe your custom era before generating a story."
                    return@Button
                }

                val apiKey = selectedProvider.apiKeyResolver().trim()
                if (apiKey.isEmpty()) {
                    validationError = "Missing API key for ${selectedProvider.label}. Add your key to longhorns.kt."
                    return@Button
                }

                validationError = null
                statusMessage = null
                isGenerating = true
                generatedStory = null

                val storyteller = SouthNodeStorytellerService(
                    chart = chart,
                    chartCake = chartCake,
                    apiKey = apiKey,
                    provider = selectedProvider.provider
                )

                storyteller.generateSouthNodeStory(
                    gender = selectedGender,
                    timePeriod = eraText,
                    style = selectedStyle,
                    length = selectedLength.first
                ) { result ->
                    mainHandler.post {
                        isGenerating = false
                        result.onSuccess { story ->
                            generatedStory = story.trim()
                            statusMessage = "Story received from ${selectedProvider.label}."
                        }.onFailure { error ->
                            generatedStory = null
                            val reason = error.message ?: "Story generation failed."
                            statusMessage = "${selectedProvider.label} error: $reason"
                            Log.e("SouthNodeStory", "Story generation failed", error)
                        }
                    }
                }
            },
            enabled = !isGenerating,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isGenerating) "Channeling your past life..." else "Generate my South Node story")
        }

        if (isGenerating) {
            Spacer(modifier = Modifier.padding(8.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        statusMessage?.let {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = it, style = MaterialTheme.typography.bodySmall)
        }

        generatedStory?.let { story ->
            Spacer(modifier = Modifier.padding(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Past life narrative",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    SelectionContainer {
                        Text(text = story, style = MaterialTheme.typography.bodyLarge, lineHeight = 20.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        TextButton(onClick = {
                            clipboardManager.setText(AnnotatedString(story))
                            Toast.makeText(context, "Story copied", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Copy")
                        }
                        TextButton(onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "South Node Story for ${chart.name}")
                                putExtra(Intent.EXTRA_TEXT, story)
                            }
                            val chooser = Intent.createChooser(intent, "Share your story")
                            context.startActivity(chooser)
                        }) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Share")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownSelector(
    label: String,
    options: List<String>,
    value: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, fontWeight = FontWeight.Medium)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                label = { Text(label) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}