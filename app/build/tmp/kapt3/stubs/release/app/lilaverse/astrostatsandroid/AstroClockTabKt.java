package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000`\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\u0003\u001a\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0007\u001a\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\nH\u0003\u001a\u0018\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0003\u001a\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010\u0018\u001a\u0015\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\u0010\u001b\u001aB\u0010\u001c\u001a\u00020\b*\u00020\u001d2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010&\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\'"}, d2 = {"planetColorMap", "", "", "Landroidx/compose/ui/graphics/Color;", "planetOrder", "", "Lapp/lilaverse/astrostatsandroid/Planet;", "AstroClockFace", "", "hands", "Lapp/lilaverse/astrostatsandroid/ClockHand;", "AstroClockTab", "chart", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "LegendRow", "hand", "StrengthRow", "entry", "maxScore", "", "computeAstroClockSnapshot", "Lapp/lilaverse/astrostatsandroid/AstroClockSnapshot;", "moment", "Ljava/util/Date;", "(Lapp/lilaverse/astrostatsandroid/model/Chart;Ljava/util/Date;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "planetColor", "name", "(Ljava/lang/String;)J", "drawHand", "Landroidx/compose/ui/graphics/drawscope/DrawScope;", "center", "Landroidx/compose/ui/geometry/Offset;", "radius", "", "strokeWidth", "pathEffect", "Landroidx/compose/ui/graphics/PathEffect;", "drawHand-1hIXUjU", "(Landroidx/compose/ui/graphics/drawscope/DrawScope;Lapp/lilaverse/astrostatsandroid/ClockHand;JFFLandroidx/compose/ui/graphics/PathEffect;)V", "app_release"})
public final class AstroClockTabKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<app.lilaverse.astrostatsandroid.Planet> planetOrder = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, androidx.compose.ui.graphics.Color> planetColorMap = null;
    
    @androidx.compose.runtime.Composable()
    public static final void AstroClockTab(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AstroClockFace(java.util.List<app.lilaverse.astrostatsandroid.ClockHand> hands) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LegendRow(app.lilaverse.astrostatsandroid.ClockHand hand) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StrengthRow(app.lilaverse.astrostatsandroid.ClockHand entry, double maxScore) {
    }
    
    private static final java.lang.Object computeAstroClockSnapshot(app.lilaverse.astrostatsandroid.model.Chart chart, java.util.Date moment, kotlin.coroutines.Continuation<? super app.lilaverse.astrostatsandroid.AstroClockSnapshot> $completion) {
        return null;
    }
    
    private static final long planetColor(java.lang.String name) {
        return 0L;
    }
}