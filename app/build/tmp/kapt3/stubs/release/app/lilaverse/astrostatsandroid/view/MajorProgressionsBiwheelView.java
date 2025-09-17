package app.lilaverse.astrostatsandroid.view;

/**
 * Custom view that renders a bi-wheel chart showing natal placements on the inner wheel
 * and major progressed placements on the outer wheel. The implementation mirrors the
 * drawing rules used by [BirthChartView] so that glyph sizes, spacing logic and overlap
 * handling remain consistent across chart presentations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0001kB%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0018\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00162\u0006\u00105\u001a\u000206H\u0002J\u0006\u00107\u001a\u000208J*\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\u001a2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u00104\u001a\u00020\u0016H\u0002J(\u0010;\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010@\u001a\u00020\u000bH\u0002Jp\u0010A\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u00104\u001a\u00020\u00162\u0006\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010@\u001a\u00020\u000b2\u0006\u0010B\u001a\u00020\u000b2\u0006\u0010C\u001a\u00020\u000f2\u0006\u0010D\u001a\u00020\u000f2\u0006\u0010E\u001a\u00020\u000f2\u0006\u0010F\u001a\u00020\u000f2\u0006\u0010G\u001a\u00020\u000b2\u0006\u0010H\u001a\u00020\u000b2\u0006\u0010I\u001a\u00020\u000bH\u0002J(\u0010J\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010K\u001a\u00020\u000bH\u0002J(\u0010L\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010M\u001a\u00020\u000bH\u0002J~\u0010N\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010O\u001a\u00020\u000b2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0012\u0010P\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\u001a2\u0006\u0010Q\u001a\u00020\u000b2\u0006\u0010R\u001a\u00020\u000b2\u0012\u0010S\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070T2\u0006\u0010U\u001a\u00020\u00072\u0006\u0010V\u001a\u00020\u0007H\u0002J(\u0010W\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u000b2\u0006\u0010?\u001a\u00020\u000b2\u0006\u0010@\u001a\u00020\u000bH\u0002J$\u0010X\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00132\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00132\u0006\u00104\u001a\u00020\u0016H\u0002J\u0010\u0010Z\u001a\u00020 2\u0006\u0010[\u001a\u00020\u001bH\u0002J\u0018\u0010\\\u001a\u00020\u00072\u0006\u0010]\u001a\u00020\u00072\u0006\u0010^\u001a\u00020\u000bH\u0002J\u0010\u0010_\u001a\u0002062\u0006\u0010`\u001a\u000206H\u0002J\u0010\u0010a\u001a\u00020\u000b2\u0006\u0010b\u001a\u00020\u000bH\u0002J\u0010\u0010c\u001a\u0002082\u0006\u0010<\u001a\u00020=H\u0014J\u0010\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gH\u0016J\b\u0010h\u001a\u00020eH\u0016J\u000e\u0010i\u001a\u0002082\u0006\u0010\f\u001a\u00020\rJ\b\u0010j\u001a\u000208H\u0002R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00070\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u000b0\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u000bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010/\u001a\b\u0012\u0004\u0012\u00020 00X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u00101\u00a8\u0006l"}, d2 = {"Lapp/lilaverse/astrostatsandroid/view/MajorProgressionsBiwheelView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "activePointerId", "baseFontSize", "", "chartCake", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "innerRingPaint", "Landroid/graphics/Paint;", "lastTouchX", "lastTouchY", "natalBodies", "", "Lapp/lilaverse/astrostatsandroid/Coordinate;", "natalHouseCusps", "Lapp/lilaverse/astrostatsandroid/HouseCusps;", "natalLinePaint", "natalNumberPaint", "natalPositions", "", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "natalSmallTextPaint", "natalTextPaint", "outerRingPaint", "planetColors", "", "progressedBodies", "progressedHouseCusps", "progressedLinePaint", "progressedNumberPaint", "progressedPositions", "progressedSmallTextPaint", "progressedTextPaint", "scaleDetector", "Landroid/view/ScaleGestureDetector;", "scaleFactor", "signColors", "smallBaseFontSize", "translateX", "translateY", "zodiacGlyphs", "", "[Ljava/lang/String;", "calculateHouseDistances", "", "houseCusps", "ascendantOffset", "", "clearChart", "", "computePositions", "bodies", "drawChartBackground", "canvas", "Landroid/graphics/Canvas;", "centerX", "centerY", "outerRadius", "drawHouseRing", "innerRadius", "numberPaint", "degreePaint", "minutePaint", "linePaint", "degreeRadius", "signRadius", "minuteRadius", "drawLilaLogo", "radius", "drawNatalLayer", "ringOuterRadius", "drawPlanets", "baseRadius", "positions", "glyphScale", "textScale", "glyphColorTransform", "Lkotlin/Function1;", "textColor", "minuteColor", "drawProgressedLayer", "fixPositionsIfNecessary", "planets", "glyphFor", "planet", "lightenColor", "color", "factor", "normalizeAngle", "angle", "normalizeAngleDifference", "diff", "onDraw", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "performClick", "setChartCake", "updateChartData", "ScaleListener", "app_release"})
public final class MajorProgressionsBiwheelView extends android.view.View {
    private final float baseFontSize = 18.0F;
    private final float smallBaseFontSize = 14.0F;
    @org.jetbrains.annotations.Nullable()
    private app.lilaverse.astrostatsandroid.ChartCake chartCake;
    @org.jetbrains.annotations.Nullable()
    private app.lilaverse.astrostatsandroid.HouseCusps natalHouseCusps;
    @org.jetbrains.annotations.Nullable()
    private app.lilaverse.astrostatsandroid.HouseCusps progressedHouseCusps;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<app.lilaverse.astrostatsandroid.Coordinate> natalBodies;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<app.lilaverse.astrostatsandroid.Coordinate> progressedBodies;
    @org.jetbrains.annotations.NotNull()
    private java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Float> natalPositions;
    @org.jetbrains.annotations.NotNull()
    private java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Float> progressedPositions;
    private float scaleFactor = 1.0F;
    private float translateX = 0.0F;
    private float translateY = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final android.view.ScaleGestureDetector scaleDetector = null;
    private float lastTouchX = 0.0F;
    private float lastTouchY = 0.0F;
    private int activePointerId = android.view.MotionEvent.INVALID_POINTER_ID;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint outerRingPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint innerRingPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint progressedLinePaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint natalLinePaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint progressedNumberPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint natalNumberPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint progressedTextPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint progressedSmallTextPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint natalTextPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint natalSmallTextPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String[] zodiacGlyphs = {"\u2648", "\u2649", "\u264a", "\u264b", "\u264c", "\u264d", "\u264e", "\u264f", "\u2650", "\u2651", "\u2652", "\u2653"};
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> planetColors = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, java.lang.Integer> signColors = null;
    
    @kotlin.jvm.JvmOverloads()
    public MajorProgressionsBiwheelView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public MajorProgressionsBiwheelView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public MajorProgressionsBiwheelView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    public final void setChartCake(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.ChartCake chartCake) {
    }
    
    public final void clearChart() {
    }
    
    private final void updateChartData() {
    }
    
    @java.lang.Override()
    protected void onDraw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas) {
    }
    
    private final void drawChartBackground(android.graphics.Canvas canvas, float centerX, float centerY, float outerRadius) {
    }
    
    private final void drawProgressedLayer(android.graphics.Canvas canvas, float centerX, float centerY, float outerRadius) {
    }
    
    private final void drawNatalLayer(android.graphics.Canvas canvas, float centerX, float centerY, float ringOuterRadius) {
    }
    
    private final void drawHouseRing(android.graphics.Canvas canvas, app.lilaverse.astrostatsandroid.HouseCusps houseCusps, float centerX, float centerY, float outerRadius, float innerRadius, android.graphics.Paint numberPaint, android.graphics.Paint degreePaint, android.graphics.Paint minutePaint, android.graphics.Paint linePaint, float degreeRadius, float signRadius, float minuteRadius) {
    }
    
    private final void drawPlanets(android.graphics.Canvas canvas, float centerX, float centerY, float baseRadius, java.util.List<app.lilaverse.astrostatsandroid.Coordinate> bodies, java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Float> positions, float glyphScale, float textScale, kotlin.jvm.functions.Function1<? super java.lang.Integer, java.lang.Integer> glyphColorTransform, int textColor, int minuteColor) {
    }
    
    private final java.lang.String glyphFor(app.lilaverse.astrostatsandroid.CelestialObject planet) {
        return null;
    }
    
    private final void drawLilaLogo(android.graphics.Canvas canvas, float centerX, float centerY, float radius) {
    }
    
    private final java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Float> computePositions(java.util.List<app.lilaverse.astrostatsandroid.Coordinate> bodies, app.lilaverse.astrostatsandroid.HouseCusps houseCusps) {
        return null;
    }
    
    private final double normalizeAngle(double angle) {
        return 0.0;
    }
    
    private final float normalizeAngleDifference(float diff) {
        return 0.0F;
    }
    
    private final float[] calculateHouseDistances(app.lilaverse.astrostatsandroid.HouseCusps houseCusps, double ascendantOffset) {
        return null;
    }
    
    private final java.util.List<java.lang.Float> fixPositionsIfNecessary(java.util.List<java.lang.Float> planets, app.lilaverse.astrostatsandroid.HouseCusps houseCusps) {
        return null;
    }
    
    private final int lightenColor(int color, float factor) {
        return 0;
    }
    
    @java.lang.Override()
    public boolean onTouchEvent(@org.jetbrains.annotations.NotNull()
    android.view.MotionEvent event) {
        return false;
    }
    
    @java.lang.Override()
    public boolean performClick() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2 = {"Lapp/lilaverse/astrostatsandroid/view/MajorProgressionsBiwheelView$ScaleListener;", "Landroid/view/ScaleGestureDetector$SimpleOnScaleGestureListener;", "(Lapp/lilaverse/astrostatsandroid/view/MajorProgressionsBiwheelView;)V", "onScale", "", "detector", "Landroid/view/ScaleGestureDetector;", "app_release"})
    public final class ScaleListener extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener {
        
        public ScaleListener() {
            super();
        }
        
        @java.lang.Override()
        public boolean onScale(@org.jetbrains.annotations.NotNull()
        android.view.ScaleGestureDetector detector) {
            return false;
        }
    }
}