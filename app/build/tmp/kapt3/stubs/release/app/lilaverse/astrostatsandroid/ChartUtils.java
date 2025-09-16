package app.lilaverse.astrostatsandroid;

/**
 * Utility class for chart-related functions and zodiac sign data
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J0\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u00042\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u00042\b\b\u0002\u0010\u000e\u001a\u00020\fJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\f0\u0004J\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u0004J\u001c\u0010\u0019\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\f0\u0004J\u000e\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eJ\u001a\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00150 2\u0006\u0010!\u001a\u00020\fJ\u000e\u0010\"\u001a\u00020\u00052\u0006\u0010!\u001a\u00020\fJ\"\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u0004J\u000e\u0010\'\u001a\u00020\f2\u0006\u0010(\u001a\u00020\fR\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\u00a8\u0006)"}, d2 = {"Lapp/lilaverse/astrostatsandroid/ChartUtils;", "", "()V", "planetGlyphs", "", "", "getPlanetGlyphs", "()Ljava/util/Map;", "zodiacGlyphs", "getZodiacGlyphs", "adjustPlanetPositions", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "", "positions", "minDistance", "calculateHouseSizes", "", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "findStrongestHouse", "houseScores", "", "findStrongestPlanet", "powerScores", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "findStrongestSign", "signScores", "Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "formatChartDate", "date", "Ljava/util/Date;", "formatDegreeMinute", "Lkotlin/Pair;", "longitude", "formatLongitude", "logChartData", "", "chartCake", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "normalizeAngleDifference", "diff", "app_release"})
public final class ChartUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> zodiacGlyphs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> planetGlyphs = null;
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.ChartUtils INSTANCE = null;
    
    private ChartUtils() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getZodiacGlyphs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getPlanetGlyphs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatChartDate(@org.jetbrains.annotations.NotNull()
    java.util.Date date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Integer, java.lang.Integer> formatDegreeMinute(double longitude) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatLongitude(double longitude) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Double> calculateHouseSizes(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.HouseCusps houseCusps) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> adjustPlanetPositions(@org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> positions, double minDistance) {
        return null;
    }
    
    public final double normalizeAngleDifference(double diff) {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String findStrongestPlanet(@org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.Coordinate, java.lang.Double> powerScores) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String findStrongestSign(@org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Double> signScores) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String findStrongestHouse(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Double> houseScores) {
        return null;
    }
    
    public final void logChartData(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.ChartCake chartCake, @org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.Coordinate, java.lang.Double> powerScores) {
    }
}