package com.app.photoapp.data.local.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SavedImageDao_Impl implements SavedImageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SavedImageEntity> __insertionAdapterOfSavedImageEntity;

  private final EntityDeletionOrUpdateAdapter<SavedImageEntity> __deletionAdapterOfSavedImageEntity;

  private final EntityDeletionOrUpdateAdapter<SavedImageEntity> __updateAdapterOfSavedImageEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavouriteStatus;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public SavedImageDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSavedImageEntity = new EntityInsertionAdapter<SavedImageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `saved_images` (`id`,`imagePath`,`templateUrl`,`createdAt`,`isFavourite`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SavedImageEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getImagePath() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getImagePath());
        }
        if (entity.getTemplateUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTemplateUrl());
        }
        statement.bindLong(4, entity.getCreatedAt());
        final int _tmp = entity.isFavourite() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfSavedImageEntity = new EntityDeletionOrUpdateAdapter<SavedImageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `saved_images` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SavedImageEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSavedImageEntity = new EntityDeletionOrUpdateAdapter<SavedImageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `saved_images` SET `id` = ?,`imagePath` = ?,`templateUrl` = ?,`createdAt` = ?,`isFavourite` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SavedImageEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getImagePath() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getImagePath());
        }
        if (entity.getTemplateUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTemplateUrl());
        }
        statement.bindLong(4, entity.getCreatedAt());
        final int _tmp = entity.isFavourite() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateFavouriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE saved_images SET isFavourite = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM saved_images WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final SavedImageEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfSavedImageEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SavedImageEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSavedImageEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final SavedImageEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSavedImageEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFavouriteStatus(final int id, final boolean isFav) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavouriteStatus.acquire();
    int _argIndex = 1;
    final int _tmp = isFav ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateFavouriteStatus.release(_stmt);
    }
  }

  @Override
  public void deleteById(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public LiveData<List<SavedImageEntity>> getAllSavedImages() {
    final String _sql = "SELECT * FROM saved_images ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"saved_images"}, false, new Callable<List<SavedImageEntity>>() {
      @Override
      @Nullable
      public List<SavedImageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfTemplateUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "templateUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavourite");
          final List<SavedImageEntity> _result = new ArrayList<SavedImageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavedImageEntity _item;
            _item = new SavedImageEntity();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImagePath(_tmpImagePath);
            final String _tmpTemplateUrl;
            if (_cursor.isNull(_cursorIndexOfTemplateUrl)) {
              _tmpTemplateUrl = null;
            } else {
              _tmpTemplateUrl = _cursor.getString(_cursorIndexOfTemplateUrl);
            }
            _item.setTemplateUrl(_tmpTemplateUrl);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final boolean _tmpIsFavourite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavourite);
            _tmpIsFavourite = _tmp != 0;
            _item.setFavourite(_tmpIsFavourite);
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
  public LiveData<List<SavedImageEntity>> getFavouriteImages() {
    final String _sql = "SELECT * FROM saved_images WHERE isFavourite = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"saved_images"}, false, new Callable<List<SavedImageEntity>>() {
      @Override
      @Nullable
      public List<SavedImageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
          final int _cursorIndexOfTemplateUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "templateUrl");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfIsFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavourite");
          final List<SavedImageEntity> _result = new ArrayList<SavedImageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavedImageEntity _item;
            _item = new SavedImageEntity();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpImagePath;
            if (_cursor.isNull(_cursorIndexOfImagePath)) {
              _tmpImagePath = null;
            } else {
              _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
            }
            _item.setImagePath(_tmpImagePath);
            final String _tmpTemplateUrl;
            if (_cursor.isNull(_cursorIndexOfTemplateUrl)) {
              _tmpTemplateUrl = null;
            } else {
              _tmpTemplateUrl = _cursor.getString(_cursorIndexOfTemplateUrl);
            }
            _item.setTemplateUrl(_tmpTemplateUrl);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final boolean _tmpIsFavourite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavourite);
            _tmpIsFavourite = _tmp != 0;
            _item.setFavourite(_tmpIsFavourite);
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
  public SavedImageEntity getByPath(final String path) {
    final String _sql = "SELECT * FROM saved_images WHERE imagePath = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (path == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, path);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "imagePath");
      final int _cursorIndexOfTemplateUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "templateUrl");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfIsFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavourite");
      final SavedImageEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new SavedImageEntity();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpImagePath;
        if (_cursor.isNull(_cursorIndexOfImagePath)) {
          _tmpImagePath = null;
        } else {
          _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        }
        _result.setImagePath(_tmpImagePath);
        final String _tmpTemplateUrl;
        if (_cursor.isNull(_cursorIndexOfTemplateUrl)) {
          _tmpTemplateUrl = null;
        } else {
          _tmpTemplateUrl = _cursor.getString(_cursorIndexOfTemplateUrl);
        }
        _result.setTemplateUrl(_tmpTemplateUrl);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final boolean _tmpIsFavourite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsFavourite);
        _tmpIsFavourite = _tmp != 0;
        _result.setFavourite(_tmpIsFavourite);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
