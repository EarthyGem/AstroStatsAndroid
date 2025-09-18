package app.lilaverse.astrostatsandroid.model;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import app.lilaverse.astrostatsandroid.HouseCusps;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ChartDao_Impl implements ChartDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Chart> __insertionAdapterOfChart;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Chart> __deletionAdapterOfChart;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCharts;

  public ChartDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChart = new EntityInsertionAdapter<Chart>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `Chart` (`id`,`name`,`date`,`birthPlace`,`locationName`,`latitude`,`longitude`,`timezone`,`timezoneId`,`timezoneLabel`,`rawOffsetMinutes`,`dstOffsetMinutes`,`isDstActive`,`planetaryPositions`,`sunSign`,`moonSign`,`risingSign`,`houseCusps`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Chart entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final long _tmp = __converters.fromDate(entity.getDate());
        statement.bindLong(3, _tmp);
        if (entity.getBirthPlace() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getBirthPlace());
        }
        if (entity.getLocationName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocationName());
        }
        statement.bindDouble(6, entity.getLatitude());
        statement.bindDouble(7, entity.getLongitude());
        if (entity.getTimezone() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTimezone());
        }
        if (entity.getTimezoneId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTimezoneId());
        }
        if (entity.getTimezoneLabel() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getTimezoneLabel());
        }
        statement.bindLong(11, entity.getRawOffsetMinutes());
        statement.bindLong(12, entity.getDstOffsetMinutes());
        final int _tmp_1 = entity.isDstActive() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        final String _tmp_2 = __converters.fromStringList(entity.getPlanetaryPositions());
        if (_tmp_2 == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, _tmp_2);
        }
        if (entity.getSunSign() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getSunSign());
        }
        if (entity.getMoonSign() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getMoonSign());
        }
        if (entity.getRisingSign() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getRisingSign());
        }
        final String _tmp_3 = __converters.fromHouseCusps(entity.getHouseCusps());
        if (_tmp_3 == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, _tmp_3);
        }
      }
    };
    this.__deletionAdapterOfChart = new EntityDeletionOrUpdateAdapter<Chart>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Chart` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Chart entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllCharts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM Chart";
        return _query;
      }
    };
  }

  @Override
  public Object insertChart(final Chart chart, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChart.insert(chart);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteChart(final Chart chart, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChart.handle(chart);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllCharts(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCharts.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllCharts.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Chart>> getAllCharts() {
    final String _sql = "SELECT * FROM Chart ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"Chart"}, new Callable<List<Chart>>() {
      @Override
      @NonNull
      public List<Chart> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfTimezoneId = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneId");
          final int _cursorIndexOfTimezoneLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneLabel");
          final int _cursorIndexOfRawOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "rawOffsetMinutes");
          final int _cursorIndexOfDstOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dstOffsetMinutes");
          final int _cursorIndexOfIsDstActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isDstActive");
          final int _cursorIndexOfPlanetaryPositions = CursorUtil.getColumnIndexOrThrow(_cursor, "planetaryPositions");
          final int _cursorIndexOfSunSign = CursorUtil.getColumnIndexOrThrow(_cursor, "sunSign");
          final int _cursorIndexOfMoonSign = CursorUtil.getColumnIndexOrThrow(_cursor, "moonSign");
          final int _cursorIndexOfRisingSign = CursorUtil.getColumnIndexOrThrow(_cursor, "risingSign");
          final int _cursorIndexOfHouseCusps = CursorUtil.getColumnIndexOrThrow(_cursor, "houseCusps");
          final List<Chart> _result = new ArrayList<Chart>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Chart _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Date _tmpDate;
            final long _tmp;
            _tmp = _cursor.getLong(_cursorIndexOfDate);
            _tmpDate = __converters.toDate(_tmp);
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpTimezoneId;
            if (_cursor.isNull(_cursorIndexOfTimezoneId)) {
              _tmpTimezoneId = null;
            } else {
              _tmpTimezoneId = _cursor.getString(_cursorIndexOfTimezoneId);
            }
            final String _tmpTimezoneLabel;
            if (_cursor.isNull(_cursorIndexOfTimezoneLabel)) {
              _tmpTimezoneLabel = null;
            } else {
              _tmpTimezoneLabel = _cursor.getString(_cursorIndexOfTimezoneLabel);
            }
            final int _tmpRawOffsetMinutes;
            _tmpRawOffsetMinutes = _cursor.getInt(_cursorIndexOfRawOffsetMinutes);
            final int _tmpDstOffsetMinutes;
            _tmpDstOffsetMinutes = _cursor.getInt(_cursorIndexOfDstOffsetMinutes);
            final boolean _tmpIsDstActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDstActive);
            _tmpIsDstActive = _tmp_1 != 0;
            final List<String> _tmpPlanetaryPositions;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPlanetaryPositions)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPlanetaryPositions);
            }
            _tmpPlanetaryPositions = __converters.toStringList(_tmp_2);
            final String _tmpSunSign;
            if (_cursor.isNull(_cursorIndexOfSunSign)) {
              _tmpSunSign = null;
            } else {
              _tmpSunSign = _cursor.getString(_cursorIndexOfSunSign);
            }
            final String _tmpMoonSign;
            if (_cursor.isNull(_cursorIndexOfMoonSign)) {
              _tmpMoonSign = null;
            } else {
              _tmpMoonSign = _cursor.getString(_cursorIndexOfMoonSign);
            }
            final String _tmpRisingSign;
            if (_cursor.isNull(_cursorIndexOfRisingSign)) {
              _tmpRisingSign = null;
            } else {
              _tmpRisingSign = _cursor.getString(_cursorIndexOfRisingSign);
            }
            final HouseCusps _tmpHouseCusps;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfHouseCusps)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfHouseCusps);
            }
            _tmpHouseCusps = __converters.toHouseCusps(_tmp_3);
            _item = new Chart(_tmpId,_tmpName,_tmpDate,_tmpBirthPlace,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpTimezone,_tmpTimezoneId,_tmpTimezoneLabel,_tmpRawOffsetMinutes,_tmpDstOffsetMinutes,_tmpIsDstActive,_tmpPlanetaryPositions,_tmpSunSign,_tmpMoonSign,_tmpRisingSign,_tmpHouseCusps);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getChartById(final int id, final Continuation<? super Chart> $completion) {
    final String _sql = "SELECT * FROM Chart WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Chart>() {
      @Override
      @Nullable
      public Chart call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfBirthPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "birthPlace");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfTimezoneId = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneId");
          final int _cursorIndexOfTimezoneLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneLabel");
          final int _cursorIndexOfRawOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "rawOffsetMinutes");
          final int _cursorIndexOfDstOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "dstOffsetMinutes");
          final int _cursorIndexOfIsDstActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isDstActive");
          final int _cursorIndexOfPlanetaryPositions = CursorUtil.getColumnIndexOrThrow(_cursor, "planetaryPositions");
          final int _cursorIndexOfSunSign = CursorUtil.getColumnIndexOrThrow(_cursor, "sunSign");
          final int _cursorIndexOfMoonSign = CursorUtil.getColumnIndexOrThrow(_cursor, "moonSign");
          final int _cursorIndexOfRisingSign = CursorUtil.getColumnIndexOrThrow(_cursor, "risingSign");
          final int _cursorIndexOfHouseCusps = CursorUtil.getColumnIndexOrThrow(_cursor, "houseCusps");
          final Chart _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Date _tmpDate;
            final long _tmp;
            _tmp = _cursor.getLong(_cursorIndexOfDate);
            _tmpDate = __converters.toDate(_tmp);
            final String _tmpBirthPlace;
            if (_cursor.isNull(_cursorIndexOfBirthPlace)) {
              _tmpBirthPlace = null;
            } else {
              _tmpBirthPlace = _cursor.getString(_cursorIndexOfBirthPlace);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpTimezoneId;
            if (_cursor.isNull(_cursorIndexOfTimezoneId)) {
              _tmpTimezoneId = null;
            } else {
              _tmpTimezoneId = _cursor.getString(_cursorIndexOfTimezoneId);
            }
            final String _tmpTimezoneLabel;
            if (_cursor.isNull(_cursorIndexOfTimezoneLabel)) {
              _tmpTimezoneLabel = null;
            } else {
              _tmpTimezoneLabel = _cursor.getString(_cursorIndexOfTimezoneLabel);
            }
            final int _tmpRawOffsetMinutes;
            _tmpRawOffsetMinutes = _cursor.getInt(_cursorIndexOfRawOffsetMinutes);
            final int _tmpDstOffsetMinutes;
            _tmpDstOffsetMinutes = _cursor.getInt(_cursorIndexOfDstOffsetMinutes);
            final boolean _tmpIsDstActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsDstActive);
            _tmpIsDstActive = _tmp_1 != 0;
            final List<String> _tmpPlanetaryPositions;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfPlanetaryPositions)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfPlanetaryPositions);
            }
            _tmpPlanetaryPositions = __converters.toStringList(_tmp_2);
            final String _tmpSunSign;
            if (_cursor.isNull(_cursorIndexOfSunSign)) {
              _tmpSunSign = null;
            } else {
              _tmpSunSign = _cursor.getString(_cursorIndexOfSunSign);
            }
            final String _tmpMoonSign;
            if (_cursor.isNull(_cursorIndexOfMoonSign)) {
              _tmpMoonSign = null;
            } else {
              _tmpMoonSign = _cursor.getString(_cursorIndexOfMoonSign);
            }
            final String _tmpRisingSign;
            if (_cursor.isNull(_cursorIndexOfRisingSign)) {
              _tmpRisingSign = null;
            } else {
              _tmpRisingSign = _cursor.getString(_cursorIndexOfRisingSign);
            }
            final HouseCusps _tmpHouseCusps;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfHouseCusps)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfHouseCusps);
            }
            _tmpHouseCusps = __converters.toHouseCusps(_tmp_3);
            _result = new Chart(_tmpId,_tmpName,_tmpDate,_tmpBirthPlace,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpTimezone,_tmpTimezoneId,_tmpTimezoneLabel,_tmpRawOffsetMinutes,_tmpDstOffsetMinutes,_tmpIsDstActive,_tmpPlanetaryPositions,_tmpSunSign,_tmpMoonSign,_tmpRisingSign,_tmpHouseCusps);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
