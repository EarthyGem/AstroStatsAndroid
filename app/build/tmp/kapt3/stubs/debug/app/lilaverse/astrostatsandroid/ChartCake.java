package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 -2\u00020\u0001:\u0001-B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010$\u001a\u00020%H\u00d6\u0001J\u0006\u0010&\u001a\u00020\u000bJ\u0006\u0010\'\u001a\u00020\u000bJ\u0019\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020%H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001a\u0010\u0013R\u0014\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\u001f\u0010 R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u000e\u00a8\u0006."}, d2 = {"Lapp/lilaverse/astrostatsandroid/ChartCake;", "Landroid/os/Parcelable;", "birthDate", "Ljava/util/Date;", "latitude", "", "longitude", "transitDate", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "timezone", "", "(Ljava/util/Date;DDLjava/util/Date;Lapp/lilaverse/astrostatsandroid/HouseCusps;Ljava/lang/String;)V", "getBirthDate", "()Ljava/util/Date;", "bodies", "", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "getBodies", "()Ljava/util/List;", "getHouseCusps", "()Lapp/lilaverse/astrostatsandroid/HouseCusps;", "getLatitude", "()D", "getLongitude", "natalBodies", "getNatalBodies", "natalBodies$delegate", "Lkotlin/Lazy;", "swe", "Lswisseph/SwissEph;", "getSwe$annotations", "()V", "getTimezone", "()Ljava/lang/String;", "getTransitDate", "describeContents", "", "formattedAllHouseActivationsBlockV2", "returnPlanets", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "Companion", "app_debug"})
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Coordinate> getBodies() {
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