package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.lilaverse.astrostatsandroid.model.Chart

@Composable
fun SynastryTab(
    primaryChart: Chart,
    primaryChartCake: ChartCake,
    allCharts: List<Chart>
) {
    val partnerOptions = remember(primaryChart.id, allCharts) {
        allCharts.filter { it.id != primaryChart.id }
    }

    var selectedPartnerId by remember(partnerOptions) {
        mutableStateOf(partnerOptions.firstOrNull()?.id)
    }
    var dropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(partnerOptions) {
        if (partnerOptions.none { it.id == selectedPartnerId }) {
            selectedPartnerId = partnerOptions.firstOrNull()?.id
        }
    }

    val selectedPartner = partnerOptions.firstOrNull { it.id == selectedPartnerId }
    val partnerCake = remember(selectedPartner) { selectedPartner?.let { ChartCake.from(it) } }

    val synastryReport = remember(primaryChart.id, selectedPartner?.id, partnerCake) {
        if (selectedPartner != null && partnerCake != null) {
            SynastryCalculator.analyze(primaryChart, primaryChartCake, selectedPartner, partnerCake)
        } else {
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Select a saved chart to compare",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (partnerOptions.isEmpty()) {
            Text(
                text = "Add another chart to explore synastry insights.",
                style = MaterialTheme.typography.bodyMedium
            )
            return@Column
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = selectedPartner?.name.orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Partner Chart") },
            trailingIcon = {
                TextButton(onClick = { dropdownExpanded = !dropdownExpanded }) {
                    Text(if (dropdownExpanded) "Close" else "Choose")
                }
            }
        )

        if (dropdownExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    partnerOptions.forEach { option ->
                        TextButton(
                            onClick = {
                                selectedPartnerId = option.id
                                dropdownExpanded = false
                            }
                        ) {
                            Text(option.name)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (synastryReport == null || selectedPartner == null || partnerCake == null) {
            Text(
                text = "Pick another chart to see synastry, composite, and biwheel highlights.",
                style = MaterialTheme.typography.bodyMedium
            )
            return@Column
        }

        SynastrySection(title = "Synastry Features") {
            Text(
                text = "Top interaspects",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            synastryReport.topAspects.forEach { result ->
                Text(
                    text = result.formatted(primaryChart.name, selectedPartner.name),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${primaryChart.name}'s planets in ${selectedPartner.name}'s houses",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            synastryReport.primaryInPartnerHouses.forEach { (house, bodies) ->
                Text("House $house: ${bodies.joinToString()}", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${selectedPartner.name}'s planets in ${primaryChart.name}'s houses",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            synastryReport.partnerInPrimaryHouses.forEach { (house, bodies) ->
                Text("House $house: ${bodies.joinToString()}", style = MaterialTheme.typography.bodyMedium)
            }
        }

        SynastrySection(title = "${selectedPartner.name}'s Natal Highlights") {
            synastryReport.partnerPlacements.forEach { line ->
                Text(line, style = MaterialTheme.typography.bodyMedium)
            }
        }

        SynastrySection(title = "Synastry Biwheel Overview") {
            synastryReport.biwheelRows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(row.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    Column(horizontalAlignment = Alignment.End) {
                        Text("${primaryChart.name}: ${row.primaryPosition}", style = MaterialTheme.typography.bodySmall)
                        Text("${selectedPartner.name}: ${row.partnerPosition}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        SynastrySection(title = "Composite Chart Snapshot") {
            Text(
                text = "Composite placements",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            synastryReport.compositeSummary.placements.forEach { placement ->
                Text(placement, style = MaterialTheme.typography.bodyMedium)
            }

            if (synastryReport.compositeSummary.topAspects.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Key composite aspects",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                synastryReport.compositeSummary.topAspects.forEach { aspect ->
                    Text(aspect.formatted(null, null), style = MaterialTheme.typography.bodyMedium)
                }
            }

            if (synastryReport.compositeSummary.houseEmphasis.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "House emphasis",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                synastryReport.compositeSummary.houseEmphasis.forEach { (house, count) ->
                    Text("House $house: $count bodies", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun SynastrySection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}