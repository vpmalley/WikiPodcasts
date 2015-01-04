package fr.vpm.wikipod.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.vpm.wikipod.location.Localisation;
import fr.vpm.wikipod.wiki.Article;

/**
 * Created by vince on 04/01/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

  private static final int DB_VERSION = 1;
  private static final String DB_NAME = "fr.vpm.wikipod";

  public DatabaseHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    try {
    TableUtils.createTable(connectionSource, Localisation.class);
      TableUtils.createTable(connectionSource, Article.class);
    } catch (SQLException e) {
      Log.e("db", "Could not create database. " + e.toString());
    }

  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

  }
}
