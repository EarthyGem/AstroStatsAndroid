package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a8\u0010\u0000\u001a\u00020\u00012\u001e\u0010\u0002\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u001a\u0010\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0007\u001a8\u0010\u000e\u001a\u00020\u00012\u001e\u0010\u000f\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u001a@\u0010\u0010\u001a\u00020\u00012\u001e\u0010\u0011\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00040\u00032\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u001a8\u0010\u0015\u001a\u00020\u00012\u001e\u0010\u0016\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u001a\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bH\u0002\u00a8\u0006\u001b"}, d2 = {"AspectScoresTab", "", "aspectTriples", "", "Lkotlin/Triple;", "Lapp/lilaverse/astrostatsandroid/Kind;", "", "chartId", "", "navController", "Landroidx/navigation/NavHostController;", "BirthChartTab", "chart", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "HouseScoresTab", "houseTriples", "PlanetScoresTab", "planetScores", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "SignScoresTab", "signScores", "Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "ordinal", "", "number", "app_debug"})
public final class BirthChartTabKt {
    
    @androidx.compose.runtime.Composable()
    public static final void BirthChartTab(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PlanetScoresTab(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends kotlin.Triple<? extends app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Float, java.lang.Float>> planetScores, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.HouseCusps houseCusps, int chartId, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SignScoresTab(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Triple<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Float, java.lang.Float>> signScores, int chartId, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HouseScoresTab(@org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Triple<java.lang.Integer, java.lang.Float, java.lang.Float>> houseTriples, int chartId, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
    }
    
    private static final java.lang.String ordinal(int number) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AspectScoresTab(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends kotlin.Triple<? extends app.lilaverse.astrostatsandroid.Kind, java.lang.Float, java.lang.Float>> aspectTriples, int chartId, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
    }
}