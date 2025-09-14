package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003J\u000f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\n\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003J\t\u0010\r\u001a\u00020\fH\u00d6\u0001J\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\u000e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\fJ\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0003J\t\u0010\u0018\u001a\u00020\fH\u00d6\u0001J\u000e\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000fJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003J\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0003J\b\u0010\u001e\u001a\u00020\u001dH\u0016J\u0019\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\fH\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006$"}, d2 = {"Lapp/lilaverse/astrostatsandroid/HouseCusps;", "Landroid/os/Parcelable;", "cusps", "", "Lapp/lilaverse/astrostatsandroid/Cusp;", "(Ljava/util/List;)V", "getCusps", "()Ljava/util/List;", "allCusps", "component1", "copy", "degrees", "", "describeContents", "distances", "", "equals", "", "other", "", "getCusp", "index", "getInterceptedSigns", "Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "hashCode", "houseForLongitude", "lon", "minutes", "signNames", "", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
@kotlinx.parcelize.Parcelize()
public final class HouseCusps implements android.os.Parcelable {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Cusp> cusps = null;
    
    public HouseCusps(@org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.Cusp> cusps) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Cusp> getCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Cusp getCusp(int index) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Cusp> allCusps() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> degrees() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> minutes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Double> distances() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> signNames() {
        return null;
    }
    
    /**
     * Given a planet's longitude, returns the house it falls into.
     * This compares the planet's position with the house cusp boundaries.
     */
    public final int houseForLongitude(double lon) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Zodiac.Sign> getInterceptedSigns() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Cusp> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps copy(@org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.Cusp> cusps) {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
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
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
}