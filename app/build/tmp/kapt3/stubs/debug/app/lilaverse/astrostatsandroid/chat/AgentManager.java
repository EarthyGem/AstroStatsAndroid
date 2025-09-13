package app.lilaverse.astrostatsandroid.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u00042\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJT\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00042\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u0014\u0010\u0011\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u000f0\u0012J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004\u00a8\u0006\u0015"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/AgentManager;", "", "()V", "getSystemInstructions", "", "chartCake", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "otherChart", "userName", "partnerName", "chartContextType", "Lapp/lilaverse/astrostatsandroid/chat/ChartContextType;", "chartTimeContext", "Lapp/lilaverse/astrostatsandroid/chat/ChartTimeContext;", "sendMessageToAgent", "", "prompt", "callback", "Lkotlin/Function1;", "toneAdjustedResponse", "userInput", "app_debug"})
public final class AgentManager {
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.chat.AgentManager INSTANCE = null;
    
    private AgentManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSystemInstructions(@org.jetbrains.annotations.Nullable()
    app.lilaverse.astrostatsandroid.ChartCake chartCake, @org.jetbrains.annotations.Nullable()
    app.lilaverse.astrostatsandroid.ChartCake otherChart, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.Nullable()
    java.lang.String partnerName, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.chat.ChartContextType chartContextType, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.chat.ChartTimeContext chartTimeContext) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toneAdjustedResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String userInput) {
        return null;
    }
    
    public final void sendMessageToAgent(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.ChartCake chartCake, @org.jetbrains.annotations.NotNull()
    java.lang.String userName, @org.jetbrains.annotations.Nullable()
    app.lilaverse.astrostatsandroid.ChartCake otherChart, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.chat.ChartContextType chartContextType, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.chat.ChartTimeContext chartTimeContext, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback) {
    }
}