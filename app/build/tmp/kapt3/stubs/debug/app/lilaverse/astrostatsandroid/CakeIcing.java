package app.lilaverse.astrostatsandroid;

/**
 * Calculates solar arc positions based on a birth date and a progressed (major) date.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\'\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010?\u001a\u00020\r2\u0006\u0010@\u001a\u00020AH\u0002R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0016R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u000fR\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u000fR\u0011\u0010\"\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0016R\u0011\u0010$\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016R\u0011\u0010&\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0013R\u0011\u0010(\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0016R\u0011\u0010*\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0016R\u000e\u0010,\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010-\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0016R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u000fR\u0011\u00101\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0016R\u0017\u00103\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010\u000fR\u0011\u00105\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0016R\u0011\u00107\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010\u0016R\u0011\u00109\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010;\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010\u0016R\u0011\u0010=\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010\u0016\u00a8\u0006B"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CakeIcing;", "", "birthDate", "Ljava/util/Date;", "majorDate", "latitude", "", "longitude", "timezone", "", "(Ljava/util/Date;Ljava/util/Date;DDLjava/lang/String;)V", "allBodies", "", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "getAllBodies", "()Ljava/util/List;", "ascendant", "Lapp/lilaverse/astrostatsandroid/Cusp;", "getAscendant", "()Lapp/lilaverse/astrostatsandroid/Cusp;", "ascendantCoordinate", "getAscendantCoordinate", "()Lapp/lilaverse/astrostatsandroid/Coordinate;", "delta", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "getHouseCusps", "()Lapp/lilaverse/astrostatsandroid/HouseCusps;", "jupiter", "getJupiter", "lunarNodes", "getLunarNodes", "majorCusps", "getMajorCusps", "mars", "getMars", "mercury", "getMercury", "midheaven", "getMidheaven", "midheavenCoordinate", "getMidheavenCoordinate", "moon", "getMoon", "natalSun", "neptune", "getNeptune", "planets", "getPlanets", "pluto", "getPluto", "rickysBodies", "getRickysBodies", "saturn", "getSaturn", "southNode", "getSouthNode", "sun", "getSun", "uranus", "getUranus", "venus", "getVenus", "arcFor", "body", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "app_debug"})
public final class CakeIcing {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date birthDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timezone = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.HouseCusps houseCusps = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate natalSun = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate sun = null;
    private final double delta = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate moon = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate mercury = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate venus = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate mars = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate jupiter = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate saturn = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate uranus = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate neptune = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate pluto = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate southNode = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Cusp ascendant = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Cusp midheaven = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate ascendantCoordinate = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Coordinate midheavenCoordinate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> planets = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> lunarNodes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> allBodies = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Cusp> majorCusps = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> rickysBodies = null;
    
    public CakeIcing(@org.jetbrains.annotations.NotNull()
    java.util.Date birthDate, @org.jetbrains.annotations.NotNull()
    java.util.Date majorDate, double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps getHouseCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getSun() {
        return null;
    }
    
    private final app.lilaverse.astrostatsandroid.Coordinate arcFor(app.lilaverse.astrostatsandroid.CelestialObject body) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getMoon() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getMercury() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getVenus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getMars() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getJupiter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getSaturn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getUranus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getNeptune() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getPluto() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getSouthNode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Cusp getAscendant() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Cusp getMidheaven() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getAscendantCoordinate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate getMidheavenCoordinate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getPlanets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getLunarNodes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getAllBodies() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Cusp> getMajorCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getRickysBodies() {
        return null;
    }
}