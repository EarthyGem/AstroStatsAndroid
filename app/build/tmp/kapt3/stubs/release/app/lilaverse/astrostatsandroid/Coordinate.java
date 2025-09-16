package app.lilaverse.astrostatsandroid;

/**
 * Represents a celestial coordinate (planet or cusp) with position and motion data.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\b\u0018\u0000 72\u00020\u0001:\u00017B!\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bB!\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u000bB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0010J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\rH\u00c6\u0003J\t\u0010,\u001a\u00020\rH\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J;\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u00103\u001a\u00020\u0007J\t\u00104\u001a\u000205H\u00d6\u0001J\t\u00106\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u000e\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\r8F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u001b\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018R\u0011\u0010 \u001a\u00020!8F\u00a2\u0006\u0006\u001a\u0004\b\"\u0010#R\u0011\u0010$\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u001dR\u0011\u0010&\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\'\u0010\u001dR\u001a\u0010\u000f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0016\"\u0004\b)\u0010\u0018\u00a8\u00068"}, d2 = {"Lapp/lilaverse/astrostatsandroid/Coordinate;", "", "body", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "date", "Ljava/util/Date;", "timezone", "", "(Lapp/lilaverse/astrostatsandroid/CelestialObject;Ljava/util/Date;Ljava/lang/String;)V", "cusp", "Lapp/lilaverse/astrostatsandroid/Cusp;", "(Lapp/lilaverse/astrostatsandroid/Cusp;Ljava/util/Date;Ljava/lang/String;)V", "longitude", "", "declination", "velocity", "(Lapp/lilaverse/astrostatsandroid/CelestialObject;DDDLjava/util/Date;)V", "getBody", "()Lapp/lilaverse/astrostatsandroid/CelestialObject;", "getDate", "()Ljava/util/Date;", "getDeclination", "()D", "setDeclination", "(D)V", "degreeInSign", "getDegreeInSign", "formattedDegreeInSign", "getFormattedDegreeInSign", "()Ljava/lang/String;", "getLongitude", "setLongitude", "sign", "Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "getSign", "()Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "signGlyph", "getSignGlyph", "signName", "getSignName", "getVelocity", "setVelocity", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "formatAsSignDegree", "hashCode", "", "toString", "Companion", "app_release"})
public final class Coordinate {
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.CelestialObject body = null;
    private double longitude;
    private double declination;
    private double velocity;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date date = null;
    @org.jetbrains.annotations.NotNull()
    private static final swisseph.SwissEph swe = null;
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.Coordinate.Companion Companion = null;
    
    public Coordinate(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject body, double longitude, double declination, double velocity, @org.jetbrains.annotations.NotNull()
    java.util.Date date) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject getBody() {
        return null;
    }
    
    public final double getLongitude() {
        return 0.0;
    }
    
    public final void setLongitude(double p0) {
    }
    
    public final double getDeclination() {
        return 0.0;
    }
    
    public final void setDeclination(double p0) {
    }
    
    public final double getVelocity() {
        return 0.0;
    }
    
    public final void setVelocity(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getDate() {
        return null;
    }
    
    public Coordinate(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject body, @org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone) {
        super();
    }
    
    public Coordinate(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Cusp cusp, @org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getSign() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSignName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSignGlyph() {
        return null;
    }
    
    public final double getDegreeInSign() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFormattedDegreeInSign() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatAsSignDegree() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject component1() {
        return null;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Coordinate copy(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject body, double longitude, double declination, double velocity, @org.jetbrains.annotations.NotNull()
    java.util.Date date) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u000e\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rJ \u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u0007J0\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\rJ\u0016\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000bJ0\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\rJ\u000e\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007J\u0010\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u0011\u001a\u00020\u0007J\u0017\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\b\u001a\u00020\tH\u0002\u00a2\u0006\u0002\u0010!J\u001c\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00070#2\b\b\u0002\u0010\u0011\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lapp/lilaverse/astrostatsandroid/Coordinate$Companion;", "", "()V", "swe", "Lswisseph/SwissEph;", "calculateCoordinateData", "Lkotlin/Triple;", "", "body", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "date", "Ljava/util/Date;", "timezone", "", "calculateJulianDay", "calculatePointDeclination", "longitude", "obliquity", "latitude", "fromAscendant", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "cusp", "Lapp/lilaverse/astrostatsandroid/Cusp;", "geoLatitude", "geoLongitude", "fromCusp", "fromMidheaven", "getObliquity", "jd", "logZodiacDeclinations", "", "sweIdForBody", "", "(Lapp/lilaverse/astrostatsandroid/CelestialObject;)Ljava/lang/Integer;", "testDeclinationCalculation", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Calculate Julian Day from a Date object
         */
        public final double calculateJulianDay(@org.jetbrains.annotations.NotNull()
        java.util.Date date, @org.jetbrains.annotations.NotNull()
        java.lang.String timezone) {
            return 0.0;
        }
        
        /**
         * Get Swiss Ephemeris ID for a celestial body
         */
        private final java.lang.Integer sweIdForBody(app.lilaverse.astrostatsandroid.CelestialObject body) {
            return null;
        }
        
        /**
         * Calculate coordinate data (longitude, declination, velocity) for a celestial body
         */
        private final kotlin.Triple<java.lang.Double, java.lang.Double, java.lang.Double> calculateCoordinateData(app.lilaverse.astrostatsandroid.CelestialObject body, java.util.Date date, java.lang.String timezone) {
            return null;
        }
        
        /**
         * Create a Coordinate from a cusp
         */
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Coordinate fromCusp(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp, @org.jetbrains.annotations.NotNull()
        java.util.Date date) {
            return null;
        }
        
        /**
         * Get the obliquity of the ecliptic for a given Julian Day
         */
        public final double getObliquity(double jd) {
            return 0.0;
        }
        
        /**
         * Calculate declination for a point on the ecliptic
         * Formula: sin(δ) = sin(ε) × sin(λ)
         * Where:
         * - δ is declination
         * - ε is obliquity of the ecliptic
         * - λ is celestial longitude
         */
        public final double calculatePointDeclination(double longitude, double obliquity, double latitude) {
            return 0.0;
        }
        
        /**
         * Create a Coordinate for the Ascendant with proper declination calculation
         */
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Coordinate fromAscendant(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp, @org.jetbrains.annotations.NotNull()
        java.util.Date date, double geoLatitude, double geoLongitude, @org.jetbrains.annotations.NotNull()
        java.lang.String timezone) {
            return null;
        }
        
        /**
         * Create a Coordinate for the Midheaven with proper declination calculation
         */
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Coordinate fromMidheaven(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp, @org.jetbrains.annotations.NotNull()
        java.util.Date date, double geoLatitude, double geoLongitude, @org.jetbrains.annotations.NotNull()
        java.lang.String timezone) {
            return null;
        }
        
        /**
         * Calculate declination for a full range of longitudes (for testing)
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.Integer, java.lang.Double> testDeclinationCalculation(double obliquity) {
            return null;
        }
        
        /**
         * Log declination values for all zodiac signs (for verification)
         */
        public final void logZodiacDeclinations(double obliquity) {
        }
    }
}