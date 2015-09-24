package mi.ur.de.dasilvaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by blu on 01.09.2015.
 */
public class EventDatabase {

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "event_items";

    public static final String KEY_ID = "id";
    public static final String KEY_FACEBOOK_ID = "facebook_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_OPENING_TIME = "opening_time";
    public static final String KEY_CLOSING_TIME = "closing_time";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE_URL = "image_url";

    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_FACEBOOK_ID_INDEX = 1;
    public static final int COLUMN_NAME_INDEX = 2;
    public static final int COLUMN_OPENING_TIME_INDEX = 3;
    public static final int COLUMN_CLOSING_TIME_INDEX = 4;
    public static final int COLUMN_DESCRIPTION_INDEX = 5;
    public static final int COLUMN_IMAGE_URL_INDEX = 6;

    private EventDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public EventDatabase(Context context){
        dbHelper = new EventDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long addNewsFeedItem(DaSilvaEvent event){
        ContentValues eventValues = new ContentValues();

        eventValues.put(KEY_FACEBOOK_ID, event.getFacebookId());
        eventValues.put(KEY_NAME, event.getName());
        eventValues.put(KEY_OPENING_TIME, event.getOpeningTime());
        eventValues.put(KEY_CLOSING_TIME, event.getClosingTime());
        eventValues.put(KEY_DESCRIPTION, event.getDescription());
        eventValues.put(KEY_IMAGE_URL, event.getImageURL());

        return db.insert(DATABASE_TABLE, null, eventValues);
    }

    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public long updateFacebookID(long id, String facebookID){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_FACEBOOK_ID, facebookID);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }


    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public int deleteEvent(long id){
        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.delete(DATABASE_TABLE, selection, selectionArgs);

        return 1;
    }


    public ArrayList<DaSilvaEvent> getAllEvents(){
        ArrayList<DaSilvaEvent> events = new ArrayList<DaSilvaEvent>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_FACEBOOK_ID, KEY_NAME, KEY_OPENING_TIME, KEY_OPENING_TIME, KEY_DESCRIPTION}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(COLUMN_ID_INDEX);
                long facebookID = cursor.getLong(COLUMN_FACEBOOK_ID_INDEX);
                String name = cursor.getString(COLUMN_NAME_INDEX);
                String openingTime = cursor.getString(COLUMN_OPENING_TIME_INDEX);
                String closingTime = cursor.getString(COLUMN_CLOSING_TIME_INDEX);
                String description = cursor.getString(COLUMN_DESCRIPTION_INDEX);
                String imageURL = cursor.getString(COLUMN_IMAGE_URL_INDEX);
                events.add(new DaSilvaEvent(id, facebookID, name, openingTime, closingTime, description, imageURL));
            }
            while(cursor.moveToNext());
        }
        return events;
    }


    private class EventDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_FACEBOOK_ID + " text, " + KEY_NAME + " text not null,"  + KEY_OPENING_TIME + " text," + KEY_CLOSING_TIME + " text," + KEY_DESCRIPTION + " text," + KEY_IMAGE_URL + " text);";

        public EventDBOpenHelper(Context c, String dbName, SQLiteDatabase.CursorFactory factory, int version){
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
