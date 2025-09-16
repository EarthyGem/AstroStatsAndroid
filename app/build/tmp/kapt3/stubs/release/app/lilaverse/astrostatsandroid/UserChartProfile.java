package app.lilaverse.astrostatsandroid;

/**
 * Data class describing key natal chart metrics for a user.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0010\n\u0002\u0010$\n\u0002\bx\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u00cd\u0003\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u000b\u0012\u0006\u0010\u0013\u001a\u00020\r\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u0012\u0006\u0010\u0017\u001a\u00020\u000b\u0012\u0006\u0010\u0018\u001a\u00020\r\u0012\u0006\u0010\u0019\u001a\u00020\u0015\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u0012\u0006\u0010\u001b\u001a\u00020\u000b\u0012\u0006\u0010\u001c\u001a\u00020\r\u0012\u0006\u0010\u001d\u001a\u00020\u000b\u0012\u0006\u0010\u001e\u001a\u00020\u0015\u0012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u0012\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u0012\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u0012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u0012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u000f\u0012\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u0012\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00150&\u0012\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150&\u0012\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&\u0012\u0006\u0010)\u001a\u00020\t\u0012\u0006\u0010*\u001a\u00020\t\u0012\u0006\u0010+\u001a\u00020\u000b\u0012\u0006\u0010,\u001a\u00020\r\u0012\u0006\u0010-\u001a\u00020\u000b\u0012\u0006\u0010.\u001a\u00020\r\u0012\u0006\u0010/\u001a\u00020\u000b\u0012\u0006\u00100\u001a\u00020\r\u0012\u0006\u00101\u001a\u00020\u000b\u0012\u0006\u00102\u001a\u00020\r\u0012\u0006\u00103\u001a\u00020\u000b\u0012\u0006\u00104\u001a\u00020\r\u0012\u0006\u00105\u001a\u00020\u000b\u0012\u0006\u00106\u001a\u00020\r\u0012\u0006\u00107\u001a\u00020\u000b\u0012\u0006\u00108\u001a\u00020\r\u00a2\u0006\u0002\u00109J\t\u0010p\u001a\u00020\u0003H\u00c6\u0003J\t\u0010q\u001a\u00020\rH\u00c6\u0003J\t\u0010r\u001a\u00020\u0015H\u00c6\u0003J\u000f\u0010s\u001a\b\u0012\u0004\u0012\u00020\u00110\u000fH\u00c6\u0003J\t\u0010t\u001a\u00020\u000bH\u00c6\u0003J\t\u0010u\u001a\u00020\rH\u00c6\u0003J\t\u0010v\u001a\u00020\u0015H\u00c6\u0003J\u000f\u0010w\u001a\b\u0012\u0004\u0012\u00020\u00110\u000fH\u00c6\u0003J\t\u0010x\u001a\u00020\u000bH\u00c6\u0003J\t\u0010y\u001a\u00020\rH\u00c6\u0003J\t\u0010z\u001a\u00020\u000bH\u00c6\u0003J\t\u0010{\u001a\u00020\u0005H\u00c6\u0003J\t\u0010|\u001a\u00020\u0015H\u00c6\u0003J\u000f\u0010}\u001a\b\u0012\u0004\u0012\u00020\u00110\u000fH\u00c6\u0003J\u000f\u0010~\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fH\u00c6\u0003J\u000f\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u00c6\u0003J\u0010\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u00c6\u0003J\u0010\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020\u00150\u000fH\u00c6\u0003J\u0010\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u000fH\u00c6\u0003J\u0016\u0010\u0083\u0001\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00150&H\u00c6\u0003J\u0016\u0010\u0084\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150&H\u00c6\u0003J\u0016\u0010\u0085\u0001\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&H\u00c6\u0003J\n\u0010\u0086\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u008a\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u008b\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u008c\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u008d\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u008e\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u008f\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u0090\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u0091\u0001\u001a\u00020\tH\u00c6\u0003J\n\u0010\u0092\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u0093\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u0094\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u0095\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u0096\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u0097\u0001\u001a\u00020\rH\u00c6\u0003J\n\u0010\u0098\u0001\u001a\u00020\u000bH\u00c6\u0003J\n\u0010\u0099\u0001\u001a\u00020\rH\u00c6\u0003J\u0010\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u00c6\u0003J\u0010\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u000fH\u00c6\u0003J\n\u0010\u009c\u0001\u001a\u00020\u000bH\u00c6\u0003J\u00ac\u0004\u0010\u009d\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\u000b2\b\b\u0002\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u000b2\b\b\u0002\u0010\u0018\u001a\u00020\r2\b\b\u0002\u0010\u0019\u001a\u00020\u00152\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010\u001c\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\u000b2\b\b\u0002\u0010\u001e\u001a\u00020\u00152\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f2\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u000f2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f2\u0014\b\u0002\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00150&2\u0014\b\u0002\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150&2\u0014\b\u0002\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&2\b\b\u0002\u0010)\u001a\u00020\t2\b\b\u0002\u0010*\u001a\u00020\t2\b\b\u0002\u0010+\u001a\u00020\u000b2\b\b\u0002\u0010,\u001a\u00020\r2\b\b\u0002\u0010-\u001a\u00020\u000b2\b\b\u0002\u0010.\u001a\u00020\r2\b\b\u0002\u0010/\u001a\u00020\u000b2\b\b\u0002\u00100\u001a\u00020\r2\b\b\u0002\u00101\u001a\u00020\u000b2\b\b\u0002\u00102\u001a\u00020\r2\b\b\u0002\u00103\u001a\u00020\u000b2\b\b\u0002\u00104\u001a\u00020\r2\b\b\u0002\u00105\u001a\u00020\u000b2\b\b\u0002\u00106\u001a\u00020\r2\b\b\u0002\u00107\u001a\u00020\u000b2\b\b\u0002\u00108\u001a\u00020\rH\u00c6\u0001J\u0016\u0010\u009e\u0001\u001a\u00030\u009f\u00012\t\u0010\u00a0\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u00a1\u0001\u001a\u00020\rH\u00d6\u0001J\n\u0010\u00a2\u0001\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u001e\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010=R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010=R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010=R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010=R\u0011\u0010\u001d\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u001d\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00150&\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010FR\u001d\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00150&\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010FR\u001d\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150&\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010FR\u0011\u00100\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bI\u0010JR\u0011\u0010/\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bK\u0010BR\u0011\u0010.\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u0010JR\u0011\u0010-\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bM\u0010BR\u0011\u0010\u001c\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u0010JR\u0011\u0010\u001b\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010BR\u0011\u0010\u0018\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bP\u0010JR\u0011\u0010\u0019\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010;R\u0011\u0010\u0017\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bR\u0010BR\u0011\u0010*\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u0011\u0010)\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u0010TR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010WR\u0011\u00106\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010JR\u0011\u00105\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010BR\u0011\u00108\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u0010JR\u0011\u00107\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010BR\u0011\u00102\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u0010JR\u0011\u00101\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010BR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u0010_R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010TR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010JR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bb\u0010=R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bc\u0010BR\u0011\u0010\u0013\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u0010JR\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\be\u0010;R\u0011\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bf\u0010BR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bg\u0010=R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bh\u0010=R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bi\u0010=R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bj\u0010=R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bk\u0010=R\u0011\u00104\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bl\u0010JR\u0011\u00103\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bm\u0010BR\u0011\u0010,\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\bn\u0010JR\u0011\u0010+\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\bo\u0010B\u00a8\u0006\u00a3\u0001"}, d2 = {"Lapp/lilaverse/astrostatsandroid/UserChartProfile;", "", "name", "", "birthDate", "Ljava/util/Date;", "sex", "Lapp/lilaverse/astrostatsandroid/Sex;", "strongestPlanet", "Lapp/lilaverse/astrostatsandroid/CelestialObject;", "strongestPlanetSign", "Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "strongestPlanetHouse", "", "strongestPlanetRuledHouses", "", "topAspectsToStrongestPlanet", "Lapp/lilaverse/astrostatsandroid/NatalAspectScore;", "sunSign", "sunHouse", "sunPower", "", "topAspectsToSun", "moonSign", "moonHouse", "moonPower", "topAspectsToMoon", "mercurySign", "mercuryHouse", "ascendantSign", "ascendantPower", "topAspectsToAscendant", "ascendantRulerSigns", "ascendantRulers", "ascendantRulerHouses", "ascendantRulerPowers", "topAspectsToAscendantRulers", "dominantHouseScores", "", "dominantSignScores", "dominantPlanetScores", "mostHarmoniousPlanet", "mostDiscordantPlanet", "venusSign", "venusHouse", "marsSign", "marsHouse", "jupiterSign", "jupiterHouse", "saturnSign", "saturnHouse", "uranusSign", "uranusHouse", "neptuneSign", "neptuneHouse", "plutoSign", "plutoHouse", "(Ljava/lang/String;Ljava/util/Date;Lapp/lilaverse/astrostatsandroid/Sex;Lapp/lilaverse/astrostatsandroid/CelestialObject;Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILjava/util/List;Ljava/util/List;Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;IDLjava/util/List;Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;IDLjava/util/List;Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;DLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Lapp/lilaverse/astrostatsandroid/CelestialObject;Lapp/lilaverse/astrostatsandroid/CelestialObject;Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;ILapp/lilaverse/astrostatsandroid/Zodiac$Sign;I)V", "getAscendantPower", "()D", "getAscendantRulerHouses", "()Ljava/util/List;", "getAscendantRulerPowers", "getAscendantRulerSigns", "getAscendantRulers", "getAscendantSign", "()Lapp/lilaverse/astrostatsandroid/Zodiac$Sign;", "getBirthDate", "()Ljava/util/Date;", "getDominantHouseScores", "()Ljava/util/Map;", "getDominantPlanetScores", "getDominantSignScores", "getJupiterHouse", "()I", "getJupiterSign", "getMarsHouse", "getMarsSign", "getMercuryHouse", "getMercurySign", "getMoonHouse", "getMoonPower", "getMoonSign", "getMostDiscordantPlanet", "()Lapp/lilaverse/astrostatsandroid/CelestialObject;", "getMostHarmoniousPlanet", "getName", "()Ljava/lang/String;", "getNeptuneHouse", "getNeptuneSign", "getPlutoHouse", "getPlutoSign", "getSaturnHouse", "getSaturnSign", "getSex", "()Lapp/lilaverse/astrostatsandroid/Sex;", "getStrongestPlanet", "getStrongestPlanetHouse", "getStrongestPlanetRuledHouses", "getStrongestPlanetSign", "getSunHouse", "getSunPower", "getSunSign", "getTopAspectsToAscendant", "getTopAspectsToAscendantRulers", "getTopAspectsToMoon", "getTopAspectsToStrongestPlanet", "getTopAspectsToSun", "getUranusHouse", "getUranusSign", "getVenusHouse", "getVenusSign", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_release"})
public final class UserChartProfile {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Date birthDate = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Sex sex = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.CelestialObject strongestPlanet = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign strongestPlanetSign = null;
    private final int strongestPlanetHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> strongestPlanetRuledHouses = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToStrongestPlanet = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign sunSign = null;
    private final int sunHouse = 0;
    private final double sunPower = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToSun = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign moonSign = null;
    private final int moonHouse = 0;
    private final double moonPower = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToMoon = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign mercurySign = null;
    private final int mercuryHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign ascendantSign = null;
    private final double ascendantPower = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToAscendant = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.Zodiac.Sign> ascendantRulerSigns = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.CelestialObject> ascendantRulers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> ascendantRulerHouses = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Double> ascendantRulerPowers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToAscendantRulers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, java.lang.Double> dominantHouseScores = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Double> dominantSignScores = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> dominantPlanetScores = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.CelestialObject mostHarmoniousPlanet = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.CelestialObject mostDiscordantPlanet = null;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign venusSign = null;
    private final int venusHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign marsSign = null;
    private final int marsHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign jupiterSign = null;
    private final int jupiterHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign saturnSign = null;
    private final int saturnHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign uranusSign = null;
    private final int uranusHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign neptuneSign = null;
    private final int neptuneHouse = 0;
    @org.jetbrains.annotations.NotNull()
    private final app.lilaverse.astrostatsandroid.Zodiac.Sign plutoSign = null;
    private final int plutoHouse = 0;
    
    public UserChartProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.Date birthDate, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Sex sex, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject strongestPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign strongestPlanetSign, int strongestPlanetHouse, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> strongestPlanetRuledHouses, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToStrongestPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign sunSign, int sunHouse, double sunPower, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToSun, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign moonSign, int moonHouse, double moonPower, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToMoon, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign mercurySign, int mercuryHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign ascendantSign, double ascendantPower, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToAscendant, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.Zodiac.Sign> ascendantRulerSigns, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends app.lilaverse.astrostatsandroid.CelestialObject> ascendantRulers, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> ascendantRulerHouses, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Double> ascendantRulerPowers, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToAscendantRulers, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Double> dominantHouseScores, @org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Double> dominantSignScores, @org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> dominantPlanetScores, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject mostHarmoniousPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject mostDiscordantPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign venusSign, int venusHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign marsSign, int marsHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign jupiterSign, int jupiterHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign saturnSign, int saturnHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign uranusSign, int uranusHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign neptuneSign, int neptuneHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign plutoSign, int plutoHouse) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date getBirthDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Sex getSex() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject getStrongestPlanet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getStrongestPlanetSign() {
        return null;
    }
    
    public final int getStrongestPlanetHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getStrongestPlanetRuledHouses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> getTopAspectsToStrongestPlanet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getSunSign() {
        return null;
    }
    
    public final int getSunHouse() {
        return 0;
    }
    
    public final double getSunPower() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> getTopAspectsToSun() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getMoonSign() {
        return null;
    }
    
    public final int getMoonHouse() {
        return 0;
    }
    
    public final double getMoonPower() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> getTopAspectsToMoon() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getMercurySign() {
        return null;
    }
    
    public final int getMercuryHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getAscendantSign() {
        return null;
    }
    
    public final double getAscendantPower() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> getTopAspectsToAscendant() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Zodiac.Sign> getAscendantRulerSigns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.CelestialObject> getAscendantRulers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getAscendantRulerHouses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Double> getAscendantRulerPowers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> getTopAspectsToAscendantRulers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, java.lang.Double> getDominantHouseScores() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Double> getDominantSignScores() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> getDominantPlanetScores() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject getMostHarmoniousPlanet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject getMostDiscordantPlanet() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getVenusSign() {
        return null;
    }
    
    public final int getVenusHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getMarsSign() {
        return null;
    }
    
    public final int getMarsHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getJupiterSign() {
        return null;
    }
    
    public final int getJupiterHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getSaturnSign() {
        return null;
    }
    
    public final int getSaturnHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getUranusSign() {
        return null;
    }
    
    public final int getUranusHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getNeptuneSign() {
        return null;
    }
    
    public final int getNeptuneHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign getPlutoSign() {
        return null;
    }
    
    public final int getPlutoHouse() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component13() {
        return null;
    }
    
    public final int component14() {
        return 0;
    }
    
    public final double component15() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component17() {
        return null;
    }
    
    public final int component18() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Date component2() {
        return null;
    }
    
    public final double component20() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.Zodiac.Sign> component22() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.CelestialObject> component23() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component24() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Double> component25() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> component26() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, java.lang.Double> component27() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Double> component28() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> component29() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Sex component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject component30() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject component31() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component32() {
        return null;
    }
    
    public final int component33() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component34() {
        return null;
    }
    
    public final int component35() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component36() {
        return null;
    }
    
    public final int component37() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component38() {
        return null;
    }
    
    public final int component39() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.CelestialObject component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component40() {
        return null;
    }
    
    public final int component41() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component42() {
        return null;
    }
    
    public final int component43() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component44() {
        return null;
    }
    
    public final int component45() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.Zodiac.Sign component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final app.lilaverse.astrostatsandroid.UserChartProfile copy(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.util.Date birthDate, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Sex sex, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject strongestPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign strongestPlanetSign, int strongestPlanetHouse, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> strongestPlanetRuledHouses, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToStrongestPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign sunSign, int sunHouse, double sunPower, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToSun, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign moonSign, int moonHouse, double moonPower, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToMoon, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign mercurySign, int mercuryHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign ascendantSign, double ascendantPower, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToAscendant, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.Zodiac.Sign> ascendantRulerSigns, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends app.lilaverse.astrostatsandroid.CelestialObject> ascendantRulers, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> ascendantRulerHouses, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Double> ascendantRulerPowers, @org.jetbrains.annotations.NotNull()
    java.util.List<app.lilaverse.astrostatsandroid.NatalAspectScore> topAspectsToAscendantRulers, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Double> dominantHouseScores, @org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.Zodiac.Sign, java.lang.Double> dominantSignScores, @org.jetbrains.annotations.NotNull()
    java.util.Map<app.lilaverse.astrostatsandroid.CelestialObject, java.lang.Double> dominantPlanetScores, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject mostHarmoniousPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.CelestialObject mostDiscordantPlanet, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign venusSign, int venusHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign marsSign, int marsHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign jupiterSign, int jupiterHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign saturnSign, int saturnHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign uranusSign, int uranusHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign neptuneSign, int neptuneHouse, @org.jetbrains.annotations.NotNull()
    app.lilaverse.astrostatsandroid.Zodiac.Sign plutoSign, int plutoHouse) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}