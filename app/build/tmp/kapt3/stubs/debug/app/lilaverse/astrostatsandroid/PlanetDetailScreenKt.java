package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000V\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001a3\u0010\b\u001a\u00020\u00012\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u00a2\u0006\u0002\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0003\u001a$\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007\u001a\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0003\u001a\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b0\u001e2\u0006\u0010\u001f\u001a\u00020\u001b\u001a\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\nH\u0002\u00a8\u0006\""}, d2 = {"AspectCard", "", "aspect", "Lapp/lilaverse/astrostatsandroid/CelestialAspect;", "score", "", "chartCake", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "HouseCard", "houseNumber", "", "coordinate", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "houseScore", "(Ljava/lang/Integer;Lapp/lilaverse/astrostatsandroid/Coordinate;Ljava/lang/Double;Lapp/lilaverse/astrostatsandroid/ChartCake;)V", "PlanetBlock", "alignEnd", "", "PlanetDetailScreen", "chart", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "planet", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "navController", "Landroidx/navigation/NavController;", "ScoreBlock", "label", "", "value", "getPlanetCorrespondences", "", "name", "ordinal", "number", "app_debug"})
public final class PlanetDetailScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PlanetDetailScreen(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject planet, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HouseCard(java.lang.Integer houseNumber, app.lilaverse.astrostatsandroid.Coordinate coordinate, java.lang.Double houseScore, app.lilaverse.astrostatsandroid.ChartCake chartCake) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AspectCard(app.lilaverse.astrostatsandroid.CelestialAspect aspect, double score, app.lilaverse.astrostatsandroid.ChartCake chartCake) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlanetBlock(app.lilaverse.astrostatsandroid.Coordinate coordinate, app.lilaverse.astrostatsandroid.ChartCake chartCake, boolean alignEnd) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ScoreBlock(java.lang.String label, java.lang.String value) {
    }
    
    private static final java.lang.String ordinal(int number) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.Map<java.lang.String, java.lang.String> getPlanetCorrespondences(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
}