package app.lilaverse.astrostatsandroid.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\bR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0010"}, d2 = {"Lapp/lilaverse/astrostatsandroid/viewmodel/ChartViewModel;", "Landroidx/lifecycle/ViewModel;", "chartDao", "Lapp/lilaverse/astrostatsandroid/model/ChartDao;", "(Lapp/lilaverse/astrostatsandroid/model/ChartDao;)V", "_charts", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "charts", "Lkotlinx/coroutines/flow/StateFlow;", "getCharts", "()Lkotlinx/coroutines/flow/StateFlow;", "addChart", "", "chart", "app_debug"})
public final class ChartViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.model.ChartDao chartDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<app.lilaverse.astrostatsandroid.model.Chart>> _charts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<app.lilaverse.astrostatsandroid.model.Chart>> charts = null;
    
    public ChartViewModel(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.ChartDao chartDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<app.lilaverse.astrostatsandroid.model.Chart>> getCharts() {
        return null;
    }
    
    public final void addChart(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart) {
    }
}