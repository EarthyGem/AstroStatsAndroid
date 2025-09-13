package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003J\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003J\t\u0010\t\u001a\u00020\bH\u00d6\u0001J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\bJ\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003J\u000e\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u000bJ\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0003J\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0003J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0019\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\bH\u00d6\u0001R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lapp/lilaverse/astrostatsandroid/HouseCusps;", "Landroid/os/Parcelable;", "cusps", "", "Lapp/lilaverse/astrostatsandroid/Cusp;", "(Ljava/util/List;)V", "allCusps", "degrees", "", "describeContents", "distances", "", "getCusp", "index", "getInterceptedSigns", "Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "houseForLongitude", "lon", "minutes", "signNames", "", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
@kotlinx.parcelize.Parcelize()
public final class HouseCusps implements android.os.Parcelable {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Cusp> cusps = null;
    
    public HouseCusps(@org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.Cusp> cusps) {
        super();
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
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
}