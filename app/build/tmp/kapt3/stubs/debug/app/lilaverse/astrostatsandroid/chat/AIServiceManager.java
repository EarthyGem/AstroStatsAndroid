package app.lilaverse.astrostatsandroid.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0014\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u00060\nJ\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/AIServiceManager;", "", "()V", "currentService", "Lapp/lilaverse/astrostatsandroid/chat/AIService;", "generateResponse", "", "prompt", "", "callback", "Lkotlin/Function1;", "setService", "serviceType", "Lapp/lilaverse/astrostatsandroid/chat/ServiceType;", "Companion", "app_debug"})
public final class AIServiceManager {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile app.lilaverse.astrostatsandroid.chat.AIServiceManager INSTANCE;
    @org.jetbrains.annotations.NotNull()
    private app.lilaverse.astrostatsandroid.chat.AIService currentService;
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.chat.AIServiceManager.Companion Companion = null;
    
    private AIServiceManager() {
        super();
    }
    
    public final void setService(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.chat.ServiceType serviceType) {
    }
    
    public final void generateResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/AIServiceManager$Companion;", "", "()V", "INSTANCE", "Lapp/lilaverse/astrostatsandroid/chat/AIServiceManager;", "getInstance", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final app.lilaverse.astrostatsandroid.chat.AIServiceManager getInstance() {
            return null;
        }
    }
}