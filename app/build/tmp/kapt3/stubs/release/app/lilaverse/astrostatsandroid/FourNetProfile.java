package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b#\b\u0087\b\u0018\u00002\u00020\u0001Bu\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\u0002\u0010\u0013J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0007H\u00c6\u0003J\t\u0010(\u001a\u00020\nH\u00c6\u0003J\t\u0010)\u001a\u00020\nH\u00c6\u0003J\t\u0010*\u001a\u00020\nH\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0003J\u008f\u0001\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00c6\u0001J\u0013\u0010.\u001a\u00020\u00072\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\nH\u00d6\u0001J\t\u00101\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0015\u00a8\u00062"}, d2 = {"Lapp/lilaverse/astrostatsandroid/FourNetProfile;", "", "natalProfile", "Lapp/lilaverse/astrostatsandroid/UserChartProfile;", "transitDate", "Ljava/util/Date;", "isPast", "", "isFuture", "yearsApart", "", "monthsApart", "daysApart", "netOne", "", "", "netTwo", "netThree", "netFour", "(Lapp/lilaverse/astrostatsandroid/UserChartProfile;Ljava/util/Date;ZZIIILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getDaysApart", "()I", "()Z", "getMonthsApart", "getNatalProfile", "()Lapp/lilaverse/astrostatsandroid/UserChartProfile;", "getNetFour", "()Ljava/util/List;", "getNetOne", "getNetThree", "getNetTwo", "getTransitDate", "()Ljava/util/Date;", "getYearsApart", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_release"})
public final class FourNetProfile {
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.UserChartProfile natalProfile = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date transitDate = null;
    private final boolean isPast = false;
    private final boolean isFuture = false;
    private final int yearsApart = 0;
    private final int monthsApart = 0;
    private final int daysApart = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> netOne = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> netTwo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> netThree = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> netFour = null;
    
    public FourNetProfile(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.UserChartProfile natalProfile, @org.jetbrains.annotations.NotNull()
    java.util.Date transitDate, boolean isPast, boolean isFuture, int yearsApart, int monthsApart, int daysApart, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netOne, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netTwo, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netThree, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netFour) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.UserChartProfile getNatalProfile() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getTransitDate() {
        return null;
    }
    
    public final boolean isPast() {
        return false;
    }
    
    public final boolean isFuture() {
        return false;
    }
    
    public final int getYearsApart() {
        return 0;
    }
    
    public final int getMonthsApart() {
        return 0;
    }
    
    public final int getDaysApart() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNetOne() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNetTwo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNetThree() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNetFour() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.UserChartProfile component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.FourNetProfile copy(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.UserChartProfile natalProfile, @org.jetbrains.annotations.NotNull()
    java.util.Date transitDate, boolean isPast, boolean isFuture, int yearsApart, int monthsApart, int daysApart, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netOne, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netTwo, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netThree, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> netFour) {
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