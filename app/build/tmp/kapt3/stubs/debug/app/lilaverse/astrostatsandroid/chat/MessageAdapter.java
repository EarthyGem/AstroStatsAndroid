package app.lilaverse.astrostatsandroid.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\bH\u0016J\u0018\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/MessageAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lapp/lilaverse/astrostatsandroid/chat/MessageAdapter$MessageViewHolder;", "messages", "", "Lapp/lilaverse/astrostatsandroid/chat/ChatMessage;", "(Ljava/util/List;)V", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "MessageViewHolder", "app_debug"})
public final class MessageAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<app.lilaverse.astrostatsandroid.chat.MessageAdapter.MessageViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.chat.ChatMessage> messages = null;
    
    public MessageAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.chat.ChatMessage> messages) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public app.lilaverse.astrostatsandroid.chat.MessageAdapter.MessageViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.chat.MessageAdapter.MessageViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lapp/lilaverse/astrostatsandroid/chat/MessageAdapter$MessageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Landroidx/viewbinding/ViewBinding;", "(Landroidx/viewbinding/ViewBinding;)V", "bind", "", "message", "Lapp/lilaverse/astrostatsandroid/chat/ChatMessage;", "app_debug"})
    public static final class MessageViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final androidx.viewbinding.ViewBinding binding = null;
        
        public MessageViewHolder(@org.jetbrains.annotations.NotNull()
        androidx.viewbinding.ViewBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        app.lilaverse.astrostatsandroid.chat.ChatMessage message) {
        }
    }
}