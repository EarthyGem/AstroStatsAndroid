package app.lilaverse.astrostatsandroid.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b1\b\u0087\b\u0018\u00002\u00020\u0001B\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0005\u0012\u0006\u0010\u0017\u001a\u00020\u0005\u0012\u0006\u0010\u0018\u001a\u00020\u0005\u0012\u0006\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\u0002\u0010\u001bJ\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0013H\u00c6\u0003J\u000f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015H\u00c6\u0003J\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0005H\u00c6\u0003J\t\u0010<\u001a\u00020\u0005H\u00c6\u0003J\t\u0010=\u001a\u00020\u001aH\u00c6\u0003J\t\u0010>\u001a\u00020\u0005H\u00c6\u0003J\t\u0010?\u001a\u00020\u0007H\u00c6\u0003J\t\u0010@\u001a\u00020\u0005H\u00c6\u0003J\t\u0010A\u001a\u00020\u0005H\u00c6\u0003J\t\u0010B\u001a\u00020\u000bH\u00c6\u0003J\t\u0010C\u001a\u00020\u000bH\u00c6\u0003J\t\u0010D\u001a\u00020\u0005H\u00c6\u0003J\t\u0010E\u001a\u00020\u0005H\u00c6\u0003J\u00c5\u0001\u0010F\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00132\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00052\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u00c6\u0001J\u0013\u0010G\u001a\u00020\u00132\b\u0010H\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010I\u001a\u00020\u0003H\u00d6\u0001J\t\u0010J\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010%R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001dR\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\'R\u0011\u0010\u0017\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\u0010\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010!R\u0011\u0010\u0018\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001dR\u0011\u0010\u0016\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001dR\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001dR\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001d\u00a8\u0006K"}, d2 = {"Lapp/lilaverse/astrostatsandroid/model/Chart;", "", "id", "", "name", "", "date", "Ljava/util/Date;", "birthPlace", "locationName", "latitude", "", "longitude", "timezone", "timezoneId", "timezoneLabel", "rawOffsetMinutes", "dstOffsetMinutes", "isDstActive", "", "planetaryPositions", "", "sunSign", "moonSign", "risingSign", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "(ILjava/lang/String;Ljava/util/Date;Ljava/lang/String;Ljava/lang/String;DDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;IIZLjava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lapp/lilaverse/astrostatsandroid/HouseCusps;)V", "getBirthPlace", "()Ljava/lang/String;", "getDate", "()Ljava/util/Date;", "getDstOffsetMinutes", "()I", "getHouseCusps", "()Lapp/lilaverse/astrostatsandroid/HouseCusps;", "getId", "()Z", "getLatitude", "()D", "getLocationName", "getLongitude", "getMoonSign", "getName", "getPlanetaryPositions", "()Ljava/util/List;", "getRawOffsetMinutes", "getRisingSign", "getSunSign", "getTimezone", "getTimezoneId", "getTimezoneLabel", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_release"})
@androidx.room.Entity()
public final class Chart {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final int id = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date date = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String birthPlace = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String locationName = null;
    private final double latitude = 0.0;
    private final double longitude = 0.0;
    
    /**
     * Time zone identifier used for ephemeris calculations. This is stored as a resolved
     * GMT offset string (for example, "GMT-07:00") so that manual daylight-saving
     * corrections are preserved when charts are re-opened.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timezone = null;
    
    /**
     * Location-based IANA time zone identifier returned by the Places / Time Zone API.
     * This value is surfaced to the user so they can confirm which region their chart
     * is associated with even if the calculation zone is a manual GMT offset.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timezoneId = null;
    
    /**
     * Human-friendly daylight-saving label from the API (e.g. "Pacific Daylight Time").
     */
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String timezoneLabel = null;
    
    /**
     * Raw offset from UTC, in minutes, reported by the API.
     */
    private final int rawOffsetMinutes = 0;
    
    /**
     * Daylight-saving offset magnitude, in minutes.
     */
    private final int dstOffsetMinutes = 0;
    
    /**
     * Whether daylight-saving adjustments were enabled when the chart was saved.
     */
    private final boolean isDstActive = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> planetaryPositions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sunSign = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String moonSign = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String risingSign = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.HouseCusps houseCusps = null;
    
    public Chart(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    java.lang.String birthPlace, @org.jetbrains.annotations.NotNull()
    java.lang.String locationName, double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone, @org.jetbrains.annotations.NotNull()
    java.lang.String timezoneId, @org.jetbrains.annotations.Nullable()
    java.lang.String timezoneLabel, int rawOffsetMinutes, int dstOffsetMinutes, boolean isDstActive, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> planetaryPositions, @org.jetbrains.annotations.NotNull()
    java.lang.String sunSign, @org.jetbrains.annotations.NotNull()
    java.lang.String moonSign, @org.jetbrains.annotations.NotNull()
    java.lang.String risingSign, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.HouseCusps houseCusps) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBirthPlace() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocationName() {
        return null;
    }
    
    public final double getLatitude() {
        return 0.0;
    }
    
    public final double getLongitude() {
        return 0.0;
    }
    
    /**
     * Time zone identifier used for ephemeris calculations. This is stored as a resolved
     * GMT offset string (for example, "GMT-07:00") so that manual daylight-saving
     * corrections are preserved when charts are re-opened.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimezone() {
        return null;
    }
    
    /**
     * Location-based IANA time zone identifier returned by the Places / Time Zone API.
     * This value is surfaced to the user so they can confirm which region their chart
     * is associated with even if the calculation zone is a manual GMT offset.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimezoneId() {
        return null;
    }
    
    /**
     * Human-friendly daylight-saving label from the API (e.g. "Pacific Daylight Time").
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTimezoneLabel() {
        return null;
    }
    
    /**
     * Raw offset from UTC, in minutes, reported by the API.
     */
    public final int getRawOffsetMinutes() {
        return 0;
    }
    
    /**
     * Daylight-saving offset magnitude, in minutes.
     */
    public final int getDstOffsetMinutes() {
        return 0;
    }
    
    /**
     * Whether daylight-saving adjustments were enabled when the chart was saved.
     */
    public final boolean isDstActive() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPlanetaryPositions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSunSign() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMoonSign() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRisingSign() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps getHouseCusps() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.model.Chart copy(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.Date date, @org.jetbrains.annotations.NotNull()
    java.lang.String birthPlace, @org.jetbrains.annotations.NotNull()
    java.lang.String locationName, double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone, @org.jetbrains.annotations.NotNull()
    java.lang.String timezoneId, @org.jetbrains.annotations.Nullable()
    java.lang.String timezoneLabel, int rawOffsetMinutes, int dstOffsetMinutes, boolean isDstActive, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> planetaryPositions, @org.jetbrains.annotations.NotNull()
    java.lang.String sunSign, @org.jetbrains.annotations.NotNull()
    java.lang.String moonSign, @org.jetbrains.annotations.NotNull()
    java.lang.String risingSign, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.HouseCusps houseCusps) {
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
}