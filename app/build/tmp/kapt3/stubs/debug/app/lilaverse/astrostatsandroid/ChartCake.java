package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 [2\u00020\u0001:\u0001[B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\"\u0010E\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\"2\u0006\u0010F\u001a\u00020\u0003H\u0002J\u0014\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010H\u001a\u00020IJ\u001e\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010F\u001a\u00020\u00032\u0006\u0010K\u001a\u00020\tH\u0002J\u0018\u0010L\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H\u0002J\u0018\u0010M\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H\u0002J\t\u0010N\u001a\u00020OH\u00d6\u0001J\u0006\u0010P\u001a\u00020\u000bJ\u000e\u0010Q\u001a\u00020\u000b2\u0006\u0010R\u001a\u00020\u000bJ\u000e\u0010S\u001a\u00020\t2\u0006\u0010H\u001a\u00020IJ\u0006\u0010T\u001a\u00020\u000bJ\u000e\u0010U\u001a\u00020\u000b2\u0006\u0010R\u001a\u00020\u000bJ\u0019\u0010V\u001a\u00020W2\u0006\u0010X\u001a\u00020Y2\u0006\u0010Z\u001a\u00020OH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001b\u0010\u0016\u001a\u00020\u00178FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F\u00a2\u0006\u0006\u001a\u0004\b \u0010\u0013R-\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\"8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b%\u0010\u001b\u001a\u0004\b#\u0010$R\u0011\u0010&\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u000eR\u0011\u0010(\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b)\u0010\u0015R\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F\u00a2\u0006\u0006\u001a\u0004\b+\u0010\u0013R-\u0010,\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\"8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b.\u0010\u001b\u001a\u0004\b-\u0010$R\u0011\u0010/\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u000eR\u0011\u00101\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b2\u0010\u0015R\'\u00103\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108FX\u0086\u0084\u0002\u00a2\u0006\u0012\n\u0004\b7\u0010\u001b\u0012\u0004\b4\u00105\u001a\u0004\b6\u0010\u0013R\u0014\u00108\u001a\u000209X\u0082\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b:\u00105R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0017\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F\u00a2\u0006\u0006\u001a\u0004\b>\u0010\u0013R-\u0010?\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\"8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\bA\u0010\u001b\u001a\u0004\b@\u0010$R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010\u000eR\u0011\u0010C\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\bD\u0010\u0015\u00a8\u0006\\"}, d2 = {"Lapp/lilaverse/astrostatsandroid/ChartCake;", "Landroid/os/Parcelable;", "birthDate", "Ljava/util/Date;", "latitude", "", "longitude", "transitDate", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "timezone", "", "(Ljava/util/Date;DDLjava/util/Date;Lapp/lilaverse/astrostatsandroid/HouseCusps;Ljava/lang/String;)V", "getBirthDate", "()Ljava/util/Date;", "bodies", "", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "getBodies", "()Ljava/util/List;", "getHouseCusps", "()Lapp/lilaverse/astrostatsandroid/HouseCusps;", "icing", "Lapp/lilaverse/astrostatsandroid/CakeIcing;", "getIcing", "()Lapp/lilaverse/astrostatsandroid/CakeIcing;", "icing$delegate", "Lkotlin/Lazy;", "getLatitude", "()D", "getLongitude", "majorBodies", "getMajorBodies", "majorData", "Lkotlin/Pair;", "getMajorData", "()Lkotlin/Pair;", "majorData$delegate", "majorDate", "getMajorDate", "majorHouseCusps", "getMajorHouseCusps", "minorBodies", "getMinorBodies", "minorData", "getMinorData", "minorData$delegate", "minorDate", "getMinorDate", "minorHouseCusps", "getMinorHouseCusps", "natalBodies", "getNatalBodies$annotations", "()V", "getNatalBodies", "natalBodies$delegate", "swe", "Lswisseph/SwissEph;", "getSwe$annotations", "getTimezone", "()Ljava/lang/String;", "transitBodies", "getTransitBodies", "transitDataPair", "getTransitDataPair", "transitDataPair$delegate", "getTransitDate", "transitHouseCusps", "getTransitHouseCusps", "bodiesAndCuspsForDate", "date", "bodiesFor", "section", "Lapp/lilaverse/astrostatsandroid/SectionType;", "bodiesForDate", "cusps", "computeMajorDate", "computeMinorDate", "describeContents", "", "formattedAllHouseActivationsBlockV2", "formattedNatalPlacements", "name", "houseCuspsFor", "returnPlanets", "returnPlanetsExpanded", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "app_debug"})
@kotlinx.parcelize.Parcelize()
public final class ChartCake implements android.os.Parcelable {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date birthDate = null;
    private final double latitude = 0.0;
    private final double longitude = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date transitDate = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.HouseCusps houseCusps = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timezone = null;
    @org.jetbrains.annotations.NotNull()
    private final swisseph.SwissEph swe = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy natalBodies$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date majorDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy majorData$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date minorDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy minorData$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy transitDataPair$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy icing$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.ChartCake.Companion Companion = null;
    
    public ChartCake(@org.jetbrains.annotations.NotNull()
    java.util.Date birthDate, double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.util.Date transitDate, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.HouseCusps houseCusps, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getBirthDate() {
        return null;
    }
    
    public final double getLatitude() {
        return 0.0;
    }
    
    public final double getLongitude() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getTransitDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps getHouseCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimezone() {
        return null;
    }
    
    @kotlinx.parcelize.IgnoredOnParcel()
    @java.lang.Deprecated()
    private static void getSwe$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getNatalBodies() {
        return null;
    }
    
    @kotlinx.parcelize.IgnoredOnParcel()
    @java.lang.Deprecated()
    public static void getNatalBodies$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getMajorDate() {
        return null;
    }
    
    private final kotlin.Pair<app.lilaverse.astrostatsandroid.HouseCusps, java.util.List<app.lilaverse.astrostatsandroid.Coordinate>> getMajorData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getMajorBodies() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps getMajorHouseCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getMinorDate() {
        return null;
    }
    
    private final kotlin.Pair<app.lilaverse.astrostatsandroid.HouseCusps, java.util.List<app.lilaverse.astrostatsandroid.Coordinate>> getMinorData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getMinorBodies() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps getMinorHouseCusps() {
        return null;
    }
    
    private final kotlin.Pair<app.lilaverse.astrostatsandroid.HouseCusps, java.util.List<app.lilaverse.astrostatsandroid.Coordinate>> getTransitDataPair() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getTransitBodies() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps getTransitHouseCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CakeIcing getIcing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getBodies() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> bodiesFor(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.SectionType section) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps houseCuspsFor(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.SectionType section) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String returnPlanets() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formattedAllHouseActivationsBlockV2() {
        return null;
    }
    
    private final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> bodiesForDate(java.util.Date date, app.lilaverse.astrostatsandroid.HouseCusps cusps) {
        return null;
    }
    
    private final kotlin.Pair<app.lilaverse.astrostatsandroid.HouseCusps, java.util.List<app.lilaverse.astrostatsandroid.Coordinate>> bodiesAndCuspsForDate(java.util.Date date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formattedNatalPlacements(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String returnPlanetsExpanded(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    private final java.util.Date computeMajorDate(java.util.Date birthDate, java.util.Date transitDate) {
        return null;
    }
    
    private final java.util.Date computeMinorDate(java.util.Date birthDate, java.util.Date transitDate) {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lapp/lilaverse/astrostatsandroid/ChartCake$Companion;", "", "()V", "from", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "chart", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.ChartCake from(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.model.Chart chart) {
            return null;
        }
    }
}