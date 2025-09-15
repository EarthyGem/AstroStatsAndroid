package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\u001at\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\n2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\r2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\rH\u0007\u001aX\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u00a8\u0006\u0018"}, d2 = {"AstrologyChartListScreen", "", "charts", "", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "modifier", "Landroidx/compose/ui/Modifier;", "onAddChartClicked", "Lkotlin/Function0;", "onChartSelected", "Lkotlin/Function2;", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "onChartEdit", "Lkotlin/Function1;", "onChartDelete", "SwipeableChartItem", "chart", "isLast", "", "isFirst", "singleItem", "onItemClick", "onEdit", "onDelete", "app_debug"})
public final class AstrologyChartListScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void AstrologyChartListScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.model.Chart> charts, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddChartClicked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super app.lilaverse.astrostatsandroid.model.Chart, ? super app.lilaverse.astrostatsandroid.ChartCake, kotlin.Unit> onChartSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super app.lilaverse.astrostatsandroid.model.Chart, kotlin.Unit> onChartEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super app.lilaverse.astrostatsandroid.model.Chart, kotlin.Unit> onChartDelete) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SwipeableChartItem(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart, boolean isLast, boolean isFirst, boolean singleItem, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super app.lilaverse.astrostatsandroid.ChartCake, kotlin.Unit> onItemClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
}