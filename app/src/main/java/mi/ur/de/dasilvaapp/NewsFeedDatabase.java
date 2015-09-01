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
    public static final String KEY_DATA_NAME = "data_name";
    public static final String KEY_FOODIE_TITLE = "foodie_name";
    public static final String KEY_RATING = "rating";

    public static final int COLUMN_ID_INDEX = 0;
    public static final int COLUMN_DATA_INDEX = 1;
    public static final int COLUMN_TITLE_INDEX = 2;
    public static final int COLUMN_RATING_INDEX = 3;

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

        newNewsFeedValues.put(KEY_DATA_NAME, item.getFoodieDataName());
        newNewsFeedValues.put(KEY_FOODIE_TITLE, item.getFoodieTitle());
        newNewsFeedValues.put(KEY_RATING, item.getFoodieRating());

        return db.insert(DATABASE_TABLE, null, newNewsFeedValues);
    }

    //Not needed!
    // public FoodieItem getFoodieItem(long foodieItemID){
    //   return null;
    // }


    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public long updateRating(long foodieItemID, float rating){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_RATING, rating);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(foodieItemID) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public long updateTitle(long foodieItemID, String title){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KEY_FOODIE_TITLE, title);

        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(foodieItemID) };

        int count = db.update(DATABASE_TABLE, values, selection, selectionArgs);

        return 1;
    }

    // update and delete methods Vgl. developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow
    public int deleteNewsFeedItem(long foodieItemID){
        // Which row to update, based on the ID
        String selection = KEY_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(foodieItemID) };

        int count = db.delete(DATABASE_TABLE, selection, selectionArgs);

        return 1;
    }


    public ArrayList<NewsFeedItem> getAllNewsFeedItems(){
        ArrayList<NewsFeedItem> items = new ArrayList<NewsFeedItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_DATA_NAME, KEY_FOODIE_TITLE, KEY_RATING}, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                long id = cursor.getLong(COLUMN_ID_INDEX);
                String dataName = cursor.getString(COLUMN_DATA_INDEX);
                String foodieName = cursor.getString(COLUMN_TITLE_INDEX);
                float rating = cursor.getFloat(COLUMN_RATING_INDEX);
                items.add(new NewsFeedItem(id, dataName, foodieName, rating));
            }
            while(cursor.moveToNext());
        }
        return items;
    }


    private class NewsFeedDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_DATA_NAME + " text not null, " + KEY_FOODIE_TITLE + " text," + KEY_RATING + " text);";

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
