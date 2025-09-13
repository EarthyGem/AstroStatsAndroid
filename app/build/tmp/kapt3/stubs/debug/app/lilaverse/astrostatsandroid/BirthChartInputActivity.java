package app.lilaverse.astrostatsandroid;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\b\u0010\u001b\u001a\u00020\u0018H\u0002J\u0012\u0010\u001c\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\b\u0010\u001f\u001a\u00020\u0018H\u0014J\b\u0010 \u001a\u00020\u0018H\u0002J\b\u0010!\u001a\u00020\u0018H\u0002J\b\u0010\"\u001a\u00020\u0018H\u0002J\u0010\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00140\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lapp/lilaverse/astrostatsandroid/BirthChartInputActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TAG", "", "birthDate", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "dateButton", "Landroid/widget/Button;", "debugText", "Landroid/widget/TextView;", "latitude", "", "longitude", "nameField", "Landroid/widget/EditText;", "placeField", "placeLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "timeButton", "timezone", "getTimezoneFromLocation", "", "lat", "lng", "launchPlacePicker", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "showDatePicker", "showTimePicker", "updateBirthDateTimezone", "updateDebugText", "message", "app_debug"})
public final class BirthChartInputActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "BirthChartInputActivity";
    private android.widget.EditText nameField;
    private android.widget.TextView placeField;
    private android.widget.Button dateButton;
    private android.widget.Button timeButton;
    private android.widget.TextView debugText;
    private double latitude = 0.0;
    private double longitude = 0.0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String timezone = "";
    private java.util.Calendar birthDate;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> placeLauncher = null;
    
    public BirthChartInputActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void updateDebugText(java.lang.String message) {
    }
    
    private final void launchPlacePicker() {
    }
    
    private final void getTimezoneFromLocation(double lat, double lng) {
    }
    
    private final void updateBirthDateTimezone() {
    }
    
    private final void showDatePicker() {
    }
    
    private final void showTimePicker() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}