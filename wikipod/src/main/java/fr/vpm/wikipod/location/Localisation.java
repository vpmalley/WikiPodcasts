package fr.vpm.wikipod.location;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.vpm.wikipod.db.DatabaseHelper;
import fr.vpm.wikipod.wiki.Article;

/**
 * Created by vince on 21/12/14.
 */
@DatabaseTable(tableName = "localisation")
public class Localisation implements Parcelable {

  public static final int DEFAULT_MAX_RESULTS = 10;

  private static final String SRC_GEOCODER = "src_geocoder";
  private static final String SRC_GPS = "src_gps";
  private static final String SRC_DB = "src_db";

  private static final String SOURCE_KEY = "source";
  private static final String LAT_KEY = "lat";
  private static final String LON_KEY = "lon";
  private static final String NAME_KEY = "name";
  private static final String COUNTRY_KEY = "country";
  private static final String CITY_KEY = "city";
  private static final String ID_KEY = "id";

  @DatabaseField(generatedId = true)
  private long id;

  @DatabaseField
  private double latitude;

  @DatabaseField
  private double longitude;

  @DatabaseField
  private String name;

  @DatabaseField
  private String country;

  @DatabaseField
  private String city;

  @DatabaseField(canBeNull = false)
  private final String source;

  private List<Address> nearbyAddresses = new ArrayList<>();

  private ArrayList<Article> articles = new ArrayList<>();

  Localisation() {
    source = SRC_DB;
  }

  public Localisation(Location l) {
    latitude = l.getLatitude();
    longitude = l.getLongitude();
    source = SRC_GPS;
  }

  public Localisation(Address nearbyAddress) {
    setNearbyAddress(nearbyAddress);
    source = SRC_GEOCODER;
  }

  public void setNearbyAddress(Address nearbyAddress) {
    if (nearbyAddress.hasLatitude()){
      latitude = nearbyAddress.getLatitude();
    }
    if (nearbyAddress.hasLongitude()){
      longitude = nearbyAddress.getLongitude();
    }
    name = getAddressLines(nearbyAddress);
    country = nearbyAddress.getCountryName();
    city = nearbyAddress.getLocality();
  }

  public long getDbId() {
    return id;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }

  public String getCity() {
    return city;
  }

  public void setNearbyAddresses(List<Address> nearbyAddresses) {
    this.nearbyAddresses = nearbyAddresses;
  }

  public List<Address> getNearbyAddresses() {
    return nearbyAddresses;
  }

  @Override
  public String toString() {
    String display;
    if (name != null) {
      display = name;
    } else {
      display = "Lat " + latitude + ", Lon " + longitude;
    }
    return display;
  }

  public static String getAddressLines(Address address) {
    StringBuilder addressLines = new StringBuilder();
    int i = 0;
    while (i < address.getMaxAddressLineIndex()){
      addressLines.append(address.getAddressLine(i++));
    }
    return addressLines.toString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    Bundle b = new Bundle();
    b.putLong(ID_KEY, id);
    b.putString(SOURCE_KEY, source);
    b.putDouble(LAT_KEY, latitude);
    b.putDouble(LON_KEY, longitude);
    b.putString(NAME_KEY, name);
    b.putString(COUNTRY_KEY, country);
    b.putString(CITY_KEY, city);
    parcel.writeBundle(b);
  }

  private Localisation(Parcel in) {
    Bundle b = in.readBundle(Localisation.class.getClassLoader());
    // without setting the classloader, it fails on BadParcelableException : ClassNotFoundException when
    // unmarshalling Media class
    id = b.getLong(ID_KEY);
    source = b.getString(SOURCE_KEY);
    latitude = b.getDouble(LAT_KEY);
    longitude = b.getDouble(LON_KEY);
    name = b.getString(NAME_KEY);
    country = b.getString(COUNTRY_KEY);
    city = b.getString(CITY_KEY);
  }

  public static final Parcelable.Creator<Localisation> CREATOR
          = new Parcelable.Creator<Localisation>() {
    public Localisation createFromParcel(Parcel in) {
      return new Localisation(in);
    }

    public Localisation[] newArray(int size) {
      return new Localisation[size];
    }
  };

  public ArrayList<Article> getArticles(DatabaseHelper dbHelper) {
    if (articles.isEmpty()) {
      try {
        Dao<Article, Long> articleDao = dbHelper.getDao(Article.class);
        Map<String, Object> articleMatching = new HashMap<>();
        articleMatching.put("localisationId", getDbId());
        articles.addAll(articleDao.queryForFieldValues(articleMatching));
      } catch (SQLException e) {
        Log.w("db", "Cannot retrieve articles for a localisation. " + e.toString());
      }
    }
    return articles;
  }
}
