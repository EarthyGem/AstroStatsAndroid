package app.lilaverse.astrostatsandroid.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0016\u0010\u000b\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\rH\u0007J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\bH\u0007J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\r2\u0006\u0010\u0011\u001a\u00020\bH\u0007\u00a8\u0006\u0013"}, d2 = {"Lapp/lilaverse/astrostatsandroid/model/Converters;", "", "()V", "fromDate", "", "date", "Ljava/util/Date;", "fromHouseCusps", "", "houseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "fromStringList", "list", "", "toDate", "millis", "toHouseCusps", "json", "toStringList", "app_debug"})
public final class Converters {
    
    public Converters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    public final long fromDate(@org.jetbrains.annotations.NotNull()
    java.util.Date date) {
        return 0L;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date toDate(long millis) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromStringList(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> list) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromHouseCusps(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.HouseCusps houseCusps) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.HouseCusps toHouseCusps(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> toStringList(@org.jetbrains.annotations.NotNull()
    java.lang.String json) {
        return null;
    }
}