package app.lilaverse.astrostatsandroid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.lilaverse.astrostatsandroid.model.Chart

@Composable
fun ChartTabsScreen(chart: Chart, navController: NavHostController) {
    val tabs = listOf("Birth Chart", "Planets", "Signs", "Houses", "Aspects")
    var selectedTab by remember { mutableStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        when (selectedTab) {
            0 -> BirthChartTab(chart)
            1 -> PlanetScoresTab()
            2 -> SignScoresTab()
            3 -> HouseScoresTab()
            4 -> AspectScoresTab()
        }
    }
}