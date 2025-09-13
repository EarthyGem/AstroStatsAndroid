package app.lilaverse.astrostatsandroid.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J&\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00032\u0014\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\u00070\nH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u000b"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/AIService;", "", "providerName", "", "getProviderName", "()Ljava/lang/String;", "generateResponse", "", "prompt", "callback", "Lkotlin/Function1;", "app_debug"})
public abstract interface AIService {
    
    public abstract void generateResponse(@org.jetbrains.annotations.NotNull()
    java.lang.String prompt, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback);
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getProviderName();
}