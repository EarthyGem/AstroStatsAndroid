package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0003\u001a$\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0003\u001a\u0018\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0003\u001a\u0010\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aH\u0002\u00a8\u0006\u001b"}, d2 = {"AspectCard", "", "aspect", "Lapp/lilaverse/astrostatsandroid/CelestialAspect;", "score", "", "chartCake", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "AspectDetailScreen", "chart", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "aspectKind", "Lapp/lilaverse/astrostatsandroid/Kind;", "navController", "Landroidx/navigation/NavController;", "PlanetBlock", "coordinate", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "alignEnd", "", "ScoreBlock", "label", "", "value", "ordinal", "number", "", "app_release"})
public final class AspectDetalScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AspectDetailScreen(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Kind aspectKind, @org.jetbrains.annotations.Nullable()
    androidx.navigation.NavController navController) {
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
}