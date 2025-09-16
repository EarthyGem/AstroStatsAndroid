package app.lilaverse.astrostatsandroid.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\nH\'J\u0018\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u0011"}, d2 = {"Lapp/lilaverse/astrostatsandroid/model/ChartDao;", "", "deleteAllCharts", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChart", "chart", "Lapp/lilaverse/astrostatsandroid/model/Chart;", "(Lapp/lilaverse/astrostatsandroid/model/Chart;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllCharts", "Lkotlinx/coroutines/flow/Flow;", "", "getChartById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertChart", "app_release"})
@androidx.room.Dao()
public abstract interface ChartDao {
    
    @androidx.room.Query(value = "SELECT * FROM Chart ORDER BY date DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<app.lilaverse.astrostatsandroid.model.Chart>> getAllCharts();
    
    @androidx.room.Query(value = "SELECT * FROM Chart WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChartById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super app.lilaverse.astrostatsandroid.model.Chart> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertChart(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteChart(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.model.Chart chart, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM Chart")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllCharts(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}