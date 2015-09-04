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
public class NewsFeedDatabase {

    private static final String DATABASE_NAME = "news_feed.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE = "news_feed_items";

    public static final String KEY_ID = "id";
    public static final String KEY_FACEBOOK_ID = "facebook_id";
    public static final String KEY_CREATED_TIME = "created_time";
    public static final String KEY_LINK = "link";
    public static final String KEY_STORY = "story";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_IMAGE_URL = "image_url";

    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_FACEBOOK_ID_INDEX = 1;
    public static final int COLUMN_CREATED_TIME_INDEX = 2;
    public static final int COLUMN_LINK_INDEX = 3;
    public static final int COLUMN_STORY_INDEX = 4;
    public static final int COLUMN_MESSAGE_INDEX = 5;
    public static final int COLUMN_IMAGE_URL_INDEX = 6;

    private NewsFeedDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public NewsFeedDatabase(Context context){
        dbHelper = new NewsFeedDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long addNewsFeedItem(NewsFeedItem item){
        ContentValues newNewsFeedValues = new ContentValues();

        newNewsFeedValues.put(KEY_FACEBOOK_ID, item.getFacebookID());
        newNewsFeedValues.put(KEY_CREATED_TIME, item.getCreatedTime());
        newNewsFeedValues.put(KEY_LINK, item.getLink());
        newNewsFeedValues.put(KEY_STORY, item.getStory());
        newNewsFeedValues.put(KEY_MESSAGE, item.getMessage());
        newNewsFeedValues.put(KEY_IMAGE_URL, item.getImageURL());

        return db.insert(DATABASE_TABLE, null, newNewsFeedValues);
    }

    //Not needed!
    // public FoodieItem getFoodieItem(long foodieItemID){
    //   return null;
    // }


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

    public long updateCreatedTime(long id, String createdTime){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_CREATED_TIME, createdTime);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    public long updateLink(long id, String link){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_LINK, link);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    public long updateStory(long id, String story){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_STORY, story);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    public long updateMessage(long id, String message){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    public long updateImageURL(long id, String imageURL){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_URL, imageURL);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public int deleteNewsFeedItem(long id){
        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.delete(DATABASE_TABLE, selection, selectionArgs);

        return 1;
    }


    public ArrayList<NewsFeedItem> getAllNewsFeedItems(){
        ArrayList<NewsFeedItem> items = new ArrayList<NewsFeedItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_FACEBOOK_ID, KEY_CREATED_TIME, KEY_LINK, KEY_STORY, KEY_MESSAGE, KEY_IMAGE_URL}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(COLUMN_ID_INDEX);
                String facebookID = cursor.getString(COLUMN_FACEBOOK_ID_INDEX);
                String createdTime = cursor.getString(COLUMN_CREATED_TIME_INDEX);
                String link = cursor.getString(COLUMN_LINK_INDEX);
                String story = cursor.getString(COLUMN_STORY_INDEX);
                String message = cursor.getString(COLUMN_MESSAGE_INDEX);
                String imageURL = cursor.getString(COLUMN_IMAGE_URL_INDEX);
                items.add(new NewsFeedItem(id, facebookID, createdTime, link, story, message, imageURL));
            }
            while(cursor.moveToNext());
        }
        return items;
    }


    private class NewsFeedDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_FACEBOOK_ID + " text not null, " + KEY_CREATED_TIME + " text,"  + KEY_LINK + " text," + KEY_STORY + " text," + KEY_MESSAGE + " text," + KEY_IMAGE_URL + " text);";

        public NewsFeedDBOpenHelper(Context c, String dbName, SQLiteDatabase.CursorFactory factory, int version){
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
