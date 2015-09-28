package mi.ur.de.dasilvaapp.RegularGuest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by blu on 01.09.2015.
 */
public class RegularGuestDatabase {

    private static final String DATABASE_NAME = "regular_guest.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "regular_guest_items";

    public static final String KEY_ID = "id";
    public static final String KEY_NUMBER_OF_VISITS = "number_of_visits";
    public static final String KEY_TIME_OF_ENTERING = "time_of_entering";

    public static final int COLUMN_NUMBER_OF_VISITS_INDEX = 1;
    public static final int COLUMN_TIME_OF_ENTERING_INDEX = 2;

    private RegularGuestDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public RegularGuestDatabase(Context context) {
        dbHelper = new RegularGuestDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long addRegularGuestItem(RegularGuestItem item) {
        ContentValues newRegularGuestValues = new ContentValues();

        newRegularGuestValues.put(KEY_NUMBER_OF_VISITS, item.getNumberOfVisits());
        newRegularGuestValues.put(KEY_TIME_OF_ENTERING, item.getTimeOfEntering());

        return db.insert(DATABASE_TABLE, null, newRegularGuestValues);
    }

    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public long updateNumberOfVisits(long id, int numberOfVisits) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_NUMBER_OF_VISITS, numberOfVisits);

        // Which row to update, based on the ID
        //String selection = KEY_PHOTO + " = '" + foodieItemID + "'";
        String selection = KEY_NUMBER_OF_VISITS + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        return db.update(DATABASE_TABLE, values, selection, selectionArgs);
    }

    public long updateTimeOfEntering(long id, String timeOfEntering) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_TIME_OF_ENTERING, timeOfEntering);

        // Which row to update, based on the ID
        String selection = KEY_TIME_OF_ENTERING + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        return db.update(DATABASE_TABLE, values, selection, selectionArgs);
    }

    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public int deleteRegularGuestItem(long id) {
        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        return db.delete(DATABASE_TABLE, selection, selectionArgs);
    }



    public ArrayList<RegularGuestItem> getAllRegularGuestItems() {
        ArrayList<RegularGuestItem> items = new ArrayList<>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NUMBER_OF_VISITS, KEY_TIME_OF_ENTERING}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int numberOfVisits = cursor.getInt(COLUMN_NUMBER_OF_VISITS_INDEX);
                String timeOfEntering = cursor.getString(COLUMN_TIME_OF_ENTERING_INDEX);
                items.add(new RegularGuestItem(numberOfVisits, timeOfEntering));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }


    private class RegularGuestDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_NUMBER_OF_VISITS + " integer," + KEY_TIME_OF_ENTERING + " text);";

        public RegularGuestDBOpenHelper(Context c, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbName, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
