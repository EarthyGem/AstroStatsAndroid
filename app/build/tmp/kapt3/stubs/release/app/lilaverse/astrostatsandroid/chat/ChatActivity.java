package app.lilaverse.astrostatsandroid.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u0012\u0010\u0013\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u000fH\u0014J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\b\u0010\u0018\u001a\u00020\u000fH\u0002J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\b\u0010\u001a\u001a\u00020\u000fH\u0002J\b\u0010\u001b\u001a\u00020\u000fH\u0002J\b\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/ChatActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lapp/lilaverse/astrostatsandroid/databinding/ActivityChatBinding;", "chartCake", "Lapp/lilaverse/astrostatsandroid/ChartCake;", "messageAdapter", "Lapp/lilaverse/astrostatsandroid/chat/MessageAdapter;", "messages", "", "Lapp/lilaverse/astrostatsandroid/chat/ChatMessage;", "userName", "", "addAssistantMessage", "", "message", "addSystemMessage", "addUserMessage", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "scrollToBottom", "sendMessage", "setupInputField", "setupRecyclerView", "testChartCakeData", "tryBackupService", "tryGeminiService", "Companion", "app_release"})
public final class ChatActivity extends androidx.appcompat.app.AppCompatActivity {
    private app.lilaverse.astrostatsandroid.databinding.ActivityChatBinding binding;
    private app.lilaverse.astrostatsandroid.chat.MessageAdapter messageAdapter;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.chat.ChatMessage> messages = null;
    private app.lilaverse.astrostatsandroid.ChartCake chartCake;
    private java.lang.String userName;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ChatActivity";
    @org.jetbrains.annotations.NotNull()
    public static final app.lilaverse.astrostatsandroid.chat.ChatActivity.Companion Companion = null;
    
    public ChatActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void tryBackupService() {
    }
    
    private final void tryGeminiService() {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupInputField() {
    }
    
    private final void addSystemMessage(java.lang.String message) {
    }
    
    private final void addUserMessage(java.lang.String message) {
    }
    
    private final void addAssistantMessage(java.lang.String message) {
    }
    
    private final void scrollToBottom() {
    }
    
    private final void sendMessage() {
    }
    
    private final void testChartCakeData() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/ChatActivity$Companion;", "", "()V", "TAG", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}