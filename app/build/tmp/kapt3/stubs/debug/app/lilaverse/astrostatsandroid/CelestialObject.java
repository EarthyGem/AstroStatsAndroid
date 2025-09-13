package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u0000 \u00072\u00020\u0001:\u0005\u0007\b\t\n\u000bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\f\r\u000e\u000f\u00a8\u0006\u0010"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CelestialObject;", "", "()V", "keyName", "", "getKeyName", "()Ljava/lang/String;", "Companion", "Cusp", "Planet", "SouthNode", "SpecialCusp", "Lapp/lilaverse/astrostatsandroid/CelestialObject$Cusp;", "Lapp/lilaverse/astrostatsandroid/CelestialObject$Planet;", "Lapp/lilaverse/astrostatsandroid/CelestialObject$SouthNode;", "Lapp/lilaverse/astrostatsandroid/CelestialObject$SpecialCusp;", "app_debug"})
public abstract class CelestialObject {
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.CelestialObject.Companion Companion = null;
    
    private CelestialObject() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getKeyName();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\f"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CelestialObject$Companion;", "", "()V", "ascendantFrom", "Lapp/lilaverse/astrostatsandroid/CelestialObject$SpecialCusp;", "cusp", "Lapp/lilaverse/astrostatsandroid/Cusp;", "fromString", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "name", "", "midheavenFrom", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.CelestialObject fromString(@org.jetbrains.annotations.NotNull()
        java.lang.String name) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.CelestialObject.SpecialCusp ascendantFrom(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.CelestialObject.SpecialCusp midheavenFrom(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CelestialObject$Cusp;", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "cusp", "Lapp/lilaverse/astrostatsandroid/Cusp;", "(Lapp/lilaverse/astrostatsandroid/Cusp;)V", "getCusp", "()Lapp/lilaverse/astrostatsandroid/Cusp;", "keyName", "", "getKeyName", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Cusp extends app.lilaverse.astrostatsandroid.CelestialObject {
        @org.jetbrains.annotations.NotNull()
        private final app.lilaverse.astrostatsandroid.Cusp cusp = null;
        
        public Cusp(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Cusp getCusp() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getKeyName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Cusp component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.CelestialObject.Cusp copy(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0006H\u00d6\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CelestialObject$Planet;", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "planet", "Lapp/lilaverse/astrostatsandroid/Planet;", "(Lapp/lilaverse/astrostatsandroid/Planet;)V", "keyName", "", "getKeyName", "()Ljava/lang/String;", "getPlanet", "()Lapp/lilaverse/astrostatsandroid/Planet;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Planet extends app.lilaverse.astrostatsandroid.CelestialObject {
        @org.jetbrains.annotations.NotNull()
        private final app.lilaverse.astrostatsandroid.Planet planet = null;
        
        public Planet(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Planet planet) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Planet getPlanet() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getKeyName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Planet component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.CelestialObject.Planet copy(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Planet planet) {
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
    
    /**
     * Represents the Mean South Node (☋), computed as Mean North Node + 180°
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CelestialObject$SouthNode;", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "()V", "keyName", "", "getKeyName", "()Ljava/lang/String;", "app_debug"})
    public static final class SouthNode extends app.lilaverse.astrostatsandroid.CelestialObject {
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String keyName = "South Node \u260b";
        @org.jetbrains.annotations.NotNull()
        public static final app.lilaverse.astrostatsandroid.CelestialObject.SouthNode INSTANCE = null;
        
        private SouthNode() {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getKeyName() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lapp/lilaverse/astrostatsandroid/CelestialObject$SpecialCusp;", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "name", "", "cusp", "Lapp/lilaverse/astrostatsandroid/Cusp;", "(Ljava/lang/String;Lapp/lilaverse/astrostatsandroid/Cusp;)V", "getCusp", "()Lapp/lilaverse/astrostatsandroid/Cusp;", "keyName", "getKeyName", "()Ljava/lang/String;", "getName", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class SpecialCusp extends app.lilaverse.astrostatsandroid.CelestialObject {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull()
        private final app.lilaverse.astrostatsandroid.Cusp cusp = null;
        
        public SpecialCusp(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Cusp getCusp() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getKeyName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.Cusp component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.CelestialObject.SpecialCusp copy(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.Cusp cusp) {
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
}