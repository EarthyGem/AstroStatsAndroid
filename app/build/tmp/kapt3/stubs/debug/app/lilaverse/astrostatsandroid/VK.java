package app.lilaverse.astrostatsandroid;

/**
 * Validation kernel that mirrors the behaviour of the iOS implementation. It
 * verifies placements, aspects and strength metrics mentioned by the language
 * model so that responses remain consistent with chart data available to the
 * Android client.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u000b\b\u00c7\u0002\u0018\u00002\u00020\u0001:\n;<=>?@ABCDB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J0\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0006H\u0002J\u0010\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J<\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u0010\u001fJ\u0010\u0010 \u001a\u00020\u00062\u0006\u0010!\u001a\u00020\u001eH\u0002J\u0010\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u0006H\u0002J\u0010\u0010$\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u000bH\u0002J\u0010\u0010%\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u001a\u0010&\u001a\u0004\u0018\u00010\u00062\u0006\u0010\'\u001a\u00020(2\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J \u0010)\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0002J\u0010\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u0006H\u0002JT\u0010.\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010/\u001a\u00020\u00142\b\u00100\u001a\u0004\u0018\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u00192\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00060\u0019H\u0002J\u0016\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ4\u00105\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u00106\u001a\u0002072\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0019H\u0002J\f\u00108\u001a\u00020\u0006*\u00020\u0006H\u0002J\f\u00109\u001a\u00020\u0006*\u00020:H\u0002\u00a8\u0006E"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK;", "", "()V", "aspectExists", "", "key", "", "context", "Lapp/lilaverse/astrostatsandroid/VK$Context;", "aspectKey", "ownerA", "Lapp/lilaverse/astrostatsandroid/VK$Owner;", "planetA", "aspect", "ownerB", "planetB", "containsIdenticalCharts", "text", "correctPlacements", "facts", "Lapp/lilaverse/astrostatsandroid/VK$NatalFacts;", "owner", "bag", "Lapp/lilaverse/astrostatsandroid/VK$Bag;", "notes", "", "hasContextBanner", "banner", "normalizeAspect", "normalizeHouse", "", "(Ljava/lang/String;)Ljava/lang/Integer;", "ordinal", "num", "ownerFromWord", "word", "ownerWord", "replaceIdenticalCharts", "sentenceContaining", "range", "Lkotlin/ranges/IntRange;", "signsCanForm", "signA", "signB", "soften", "sentence", "softenInvalidAspects", "userFacts", "partnerFacts", "warnings", "validate", "Lapp/lilaverse/astrostatsandroid/VK$Result;", "draft", "validateMetricClaims", "metrics", "Lapp/lilaverse/astrostatsandroid/VK$AstroMetrics;", "cap", "format1", "", "AllowedAspects", "AstroMetrics", "Bag", "Context", "Humility", "Kind", "NatalFacts", "Owner", "PronounAuditor", "Result", "app_debug"})
public final class VK {
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.VK INSTANCE = null;
    
    private VK() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.VK.Result validate(@org.jetbrains.annotations.NotNull()
    java.lang.String draft, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.VK.Context context) {
        return null;
    }
    
    private final boolean hasContextBanner(java.lang.String text, java.lang.String banner) {
        return false;
    }
    
    private final boolean containsIdenticalCharts(java.lang.String text) {
        return false;
    }
    
    private final java.lang.String replaceIdenticalCharts(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String correctPlacements(java.lang.String text, app.lilaverse.astrostatsandroid.VK.NatalFacts facts, app.lilaverse.astrostatsandroid.VK.Owner owner, app.lilaverse.astrostatsandroid.VK.Bag<java.lang.String> bag, java.util.List<java.lang.String> notes) {
        return null;
    }
    
    private final java.lang.String softenInvalidAspects(java.lang.String text, app.lilaverse.astrostatsandroid.VK.Context context, app.lilaverse.astrostatsandroid.VK.NatalFacts userFacts, app.lilaverse.astrostatsandroid.VK.NatalFacts partnerFacts, app.lilaverse.astrostatsandroid.VK.Bag<java.lang.String> bag, java.util.List<java.lang.String> notes, java.util.List<java.lang.String> warnings) {
        return null;
    }
    
    private final java.lang.String validateMetricClaims(java.lang.String text, app.lilaverse.astrostatsandroid.VK.AstroMetrics metrics, app.lilaverse.astrostatsandroid.VK.Bag<java.lang.String> bag, java.util.List<java.lang.String> notes) {
        return null;
    }
    
    private final java.lang.Integer normalizeHouse(java.lang.String text) {
        return null;
    }
    
    private final app.lilaverse.astrostatsandroid.VK.Owner ownerFromWord(java.lang.String word) {
        return null;
    }
    
    private final java.lang.String ownerWord(app.lilaverse.astrostatsandroid.VK.Owner owner) {
        return null;
    }
    
    private final boolean aspectExists(java.lang.String key, app.lilaverse.astrostatsandroid.VK.Context context) {
        return false;
    }
    
    private final java.lang.String aspectKey(app.lilaverse.astrostatsandroid.VK.Owner ownerA, java.lang.String planetA, java.lang.String aspect, app.lilaverse.astrostatsandroid.VK.Owner ownerB, java.lang.String planetB) {
        return null;
    }
    
    private final java.lang.String normalizeAspect(java.lang.String text) {
        return null;
    }
    
    private final java.lang.String sentenceContaining(kotlin.ranges.IntRange range, java.lang.String text) {
        return null;
    }
    
    private final java.lang.String soften(java.lang.String sentence) {
        return null;
    }
    
    private final boolean signsCanForm(java.lang.String signA, java.lang.String signB, java.lang.String aspect) {
        return false;
    }
    
    private final java.lang.String ordinal(int num) {
        return null;
    }
    
    private final java.lang.String cap(java.lang.String $this$cap) {
        return null;
    }
    
    private final java.lang.String format1(double $this$format1) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0087\b\u0018\u0000 \u00192\u00020\u0001:\u0002\u0019\u001aBE\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003JI\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0004H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u001b"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects;", "", "natal", "", "", "synastry", "transits", "progressions", "(Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)V", "getNatal", "()Ljava/util/Set;", "getProgressions", "getSynastry", "getTransits", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "Companion", "Precomputed", "app_debug"})
    public static final class AllowedAspects {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> natal = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> synastry = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> transits = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> progressions = null;
        @org.jetbrains.annotations.NotNull()
        public static final app.lilaverse.astrostatsandroid.VK.AllowedAspects.Companion Companion = null;
        
        public AllowedAspects(@org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> natal, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> synastry, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> transits, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> progressions) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getNatal() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getSynastry() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getTransits() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getProgressions() {
            return null;
        }
        
        public AllowedAspects() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.AllowedAspects copy(@org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> natal, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> synastry, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> transits, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> progressions) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\tJ\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a8\u0006\u0010"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects$Companion;", "", "()V", "from", "Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects;", "precomputed", "Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects$Precomputed;", "fromNatalCelestial", "", "", "aspects", "", "Lapp/lilaverse/astrostatsandroid/CelestialAspect;", "ownerPrefix", "fromSynastryCelestial", "fromTimingCelestial", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final app.lilaverse.astrostatsandroid.VK.AllowedAspects from(@org.jetbrains.annotations.NotNull()
            app.lilaverse.astrostatsandroid.VK.AllowedAspects.Precomputed precomputed) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> fromNatalCelestial(@org.jetbrains.annotations.NotNull()
            java.util.List<app.lilaverse.astrostatsandroid.CelestialAspect> aspects, @org.jetbrains.annotations.NotNull()
            java.lang.String ownerPrefix) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> fromSynastryCelestial(@org.jetbrains.annotations.NotNull()
            java.util.List<app.lilaverse.astrostatsandroid.CelestialAspect> aspects) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> fromTimingCelestial(@org.jetbrains.annotations.NotNull()
            java.util.List<app.lilaverse.astrostatsandroid.CelestialAspect> aspects) {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B=\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003JI\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0004H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u0019"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects$Precomputed;", "", "natal", "", "", "synastry", "transits", "progressions", "(Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;)V", "getNatal", "()Ljava/util/Set;", "getProgressions", "getSynastry", "getTransits", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
        public static final class Precomputed {
            @org.jetbrains.annotations.NotNull()
            private final java.util.Set<java.lang.String> natal = null;
            @org.jetbrains.annotations.NotNull()
            private final java.util.Set<java.lang.String> synastry = null;
            @org.jetbrains.annotations.NotNull()
            private final java.util.Set<java.lang.String> transits = null;
            @org.jetbrains.annotations.NotNull()
            private final java.util.Set<java.lang.String> progressions = null;
            
            public Precomputed(@org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> natal, @org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> synastry, @org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> transits, @org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> progressions) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> getNatal() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> getSynastry() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> getTransits() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> getProgressions() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> component3() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Set<java.lang.String> component4() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final app.lilaverse.astrostatsandroid.VK.AllowedAspects.Precomputed copy(@org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> natal, @org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> synastry, @org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> transits, @org.jetbrains.annotations.NotNull()
            java.util.Set<java.lang.String> progressions) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0082\b\u0018\u0000 \'2\u00020\u0001:\u0001\'Bs\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0002\u0010\rJ\u0015\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0003J\u0015\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0003J\u0015\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0003J\u0015\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0004H\u00c6\u0003J\u008a\u0001\u0010 \u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0004H\u00c6\u0001\u00a2\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020\bH\u00d6\u0001J\t\u0010&\u001a\u00020\u0004H\u00d6\u0001R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017\u00a8\u0006("}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$AstroMetrics;", "", "planetTotals", "", "", "", "planetAspectPower", "housePower", "", "signPower", "strongestPlanet", "strongestHouse", "strongestSign", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)V", "getHousePower", "()Ljava/util/Map;", "getPlanetAspectPower", "getPlanetTotals", "getSignPower", "getStrongestHouse", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getStrongestPlanet", "()Ljava/lang/String;", "getStrongestSign", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)Lapp/lilaverse/astrostatsandroid/VK$AstroMetrics;", "equals", "", "other", "hashCode", "toString", "Companion", "app_debug"})
    static final class AstroMetrics {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, java.lang.Double> planetTotals = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, java.lang.Double> planetAspectPower = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.Integer, java.lang.Double> housePower = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, java.lang.Double> signPower = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String strongestPlanet = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer strongestHouse = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String strongestSign = null;
        @org.jetbrains.annotations.NotNull()
        private static final java.util.Set<java.lang.String> planets = null;
        @org.jetbrains.annotations.NotNull()
        public static final app.lilaverse.astrostatsandroid.VK.AstroMetrics.Companion Companion = null;
        
        public AstroMetrics(@org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Double> planetTotals, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Double> planetAspectPower, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.Integer, java.lang.Double> housePower, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Double> signPower, @org.jetbrains.annotations.Nullable()
        java.lang.String strongestPlanet, @org.jetbrains.annotations.Nullable()
        java.lang.Integer strongestHouse, @org.jetbrains.annotations.Nullable()
        java.lang.String strongestSign) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Double> getPlanetTotals() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Double> getPlanetAspectPower() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.Integer, java.lang.Double> getHousePower() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Double> getSignPower() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getStrongestPlanet() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getStrongestHouse() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getStrongestSign() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Double> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Double> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.Integer, java.lang.Double> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Double> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.AstroMetrics copy(@org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Double> planetTotals, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Double> planetAspectPower, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.Integer, java.lang.Double> housePower, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Double> signPower, @org.jetbrains.annotations.Nullable()
        java.lang.String strongestPlanet, @org.jetbrains.annotations.Nullable()
        java.lang.Integer strongestHouse, @org.jetbrains.annotations.Nullable()
        java.lang.String strongestSign) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$AstroMetrics$Companion;", "", "()V", "planets", "", "", "from", "Lapp/lilaverse/astrostatsandroid/VK$AstroMetrics;", "chart", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final app.lilaverse.astrostatsandroid.VK.AstroMetrics from(@org.jetbrains.annotations.NotNull()
            app.lilaverse.astrostatsandroid.ChartCake chart) {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J!\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u00002\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n\u00a2\u0006\u0002\u0010\u000bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$Bag;", "T", "", "()V", "set", "", "once", "", "value", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "app_debug"})
    public static final class Bag<T extends java.lang.Object> {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<T> set = null;
        
        public Bag() {
            super();
        }
        
        public final void once(T value, @org.jetbrains.annotations.NotNull()
        kotlin.jvm.functions.Function0<kotlin.Unit> block) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J=\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020\nH\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013\u00a8\u0006!"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$Context;", "", "kind", "Lapp/lilaverse/astrostatsandroid/VK$Kind;", "primary", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "partner", "allowed", "Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects;", "contextBanner", "", "(Lapp/lilaverse/astrostatsandroid/VK$Kind;Lapp/lilaverse/astrostatsandroid/ChartCake;Lapp/lilaverse/astrostatsandroid/ChartCake;Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects;Ljava/lang/String;)V", "getAllowed", "()Lapp/lilaverse/astrostatsandroid/VK$AllowedAspects;", "getContextBanner", "()Ljava/lang/String;", "getKind", "()Lapp/lilaverse/astrostatsandroid/VK$Kind;", "getPartner", "()Lapp/lilaverse/astrostatsandroid/ChartCake;", "getPrimary", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class Context {
        @org.jetbrains.annotations.NotNull()
        private final app.lilaverse.astrostatsandroid.VK.Kind kind = null;
        @org.jetbrains.annotations.NotNull()
        private final app.lilaverse.astrostatsandroid.ChartCake primary = null;
        @org.jetbrains.annotations.Nullable()
        private final app.lilaverse.astrostatsandroid.ChartCake partner = null;
        @org.jetbrains.annotations.NotNull()
        private final app.lilaverse.astrostatsandroid.VK.AllowedAspects allowed = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String contextBanner = null;
        
        public Context(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.VK.Kind kind, @org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.ChartCake primary, @org.jetbrains.annotations.Nullable()
        app.lilaverse.astrostatsandroid.ChartCake partner, @org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.VK.AllowedAspects allowed, @org.jetbrains.annotations.NotNull()
        java.lang.String contextBanner) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.Kind getKind() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.ChartCake getPrimary() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final app.lilaverse.astrostatsandroid.ChartCake getPartner() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.AllowedAspects getAllowed() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getContextBanner() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.Kind component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.ChartCake component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final app.lilaverse.astrostatsandroid.ChartCake component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.AllowedAspects component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.Context copy(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.VK.Kind kind, @org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.ChartCake primary, @org.jetbrains.annotations.Nullable()
        app.lilaverse.astrostatsandroid.ChartCake partner, @org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.VK.AllowedAspects allowed, @org.jetbrains.annotations.NotNull()
        java.lang.String contextBanner) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$Humility;", "", "()V", "inject", "", "text", "app_debug"})
    static final class Humility {
        @org.jetbrains.annotations.NotNull()
        public static final app.lilaverse.astrostatsandroid.VK.Humility INSTANCE = null;
        
        private Humility() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String inject(@org.jetbrains.annotations.NotNull()
        java.lang.String text) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$Kind;", "", "(Ljava/lang/String;I)V", "NATAL", "SYNASTRY", "TRANSITS", "PROGRESSIONS", "app_debug"})
    public static enum Kind {
        /*public static final*/ NATAL /* = new NATAL() */,
        /*public static final*/ SYNASTRY /* = new SYNASTRY() */,
        /*public static final*/ TRANSITS /* = new TRANSITS() */,
        /*public static final*/ PROGRESSIONS /* = new PROGRESSIONS() */;
        
        Kind() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<app.lilaverse.astrostatsandroid.VK.Kind> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0082\b\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B-\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\u0002\u0010\u0007J\u0015\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0015\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J5\u0010\r\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0004H\u00d6\u0001R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0014"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$NatalFacts;", "", "signs", "", "", "houses", "", "(Ljava/util/Map;Ljava/util/Map;)V", "getHouses", "()Ljava/util/Map;", "getSigns", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "Companion", "app_debug"})
    static final class NatalFacts {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, java.lang.String> signs = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, java.lang.Integer> houses = null;
        @org.jetbrains.annotations.NotNull()
        private static final java.util.Set<java.lang.String> tracked = null;
        @org.jetbrains.annotations.NotNull()
        public static final app.lilaverse.astrostatsandroid.VK.NatalFacts.Companion Companion = null;
        
        public NatalFacts(@org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> signs, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Integer> houses) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.String> getSigns() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Integer> getHouses() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.String> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, java.lang.Integer> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.NatalFacts copy(@org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> signs, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.Integer> houses) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$NatalFacts$Companion;", "", "()V", "tracked", "", "", "from", "Lapp/lilaverse/astrostatsandroid/VK$NatalFacts;", "chart", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final app.lilaverse.astrostatsandroid.VK.NatalFacts from(@org.jetbrains.annotations.NotNull()
            app.lilaverse.astrostatsandroid.ChartCake chart) {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$Owner;", "", "(Ljava/lang/String;I)V", "USER", "PARTNER", "app_debug"})
    public static enum Owner {
        /*public static final*/ USER /* = new USER() */,
        /*public static final*/ PARTNER /* = new PARTNER() */;
        
        Owner() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<app.lilaverse.astrostatsandroid.VK.Owner> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$PronounAuditor;", "", "()V", "audit", "", "text", "app_debug"})
    static final class PronounAuditor {
        @org.jetbrains.annotations.NotNull()
        public static final app.lilaverse.astrostatsandroid.VK.PronounAuditor INSTANCE = null;
        
        private PronounAuditor() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String audit(@org.jetbrains.annotations.NotNull()
        java.lang.String text) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J=\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f\u00a8\u0006\u001b"}, d2 = {"Lapp/lilaverse/astrostatsandroid/VK$Result;", "", "corrected", "", "notes", "", "warnings", "criticalFix", "", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;Z)V", "getCorrected", "()Ljava/lang/String;", "getCriticalFix", "()Z", "getNotes", "()Ljava/util/List;", "getWarnings", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class Result {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String corrected = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> notes = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> warnings = null;
        private final boolean criticalFix = false;
        
        public Result(@org.jetbrains.annotations.NotNull()
        java.lang.String corrected, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> notes, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> warnings, boolean criticalFix) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCorrected() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getNotes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getWarnings() {
            return null;
        }
        
        public final boolean getCriticalFix() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.VK.Result copy(@org.jetbrains.annotations.NotNull()
        java.lang.String corrected, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> notes, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> warnings, boolean criticalFix) {
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