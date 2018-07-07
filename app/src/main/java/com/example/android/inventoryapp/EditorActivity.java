package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.BookDbHelper;
import com.example.android.inventoryapp.data.BookContract.BookEntry;

public class EditorActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    // EditText fielt to enter the title of the book
    private EditText mTitleEditText;

    //EditText field to enter the author of the book
    private EditText mAuthorEditText;

    //EditText field to enter the price of the book
    private EditText mPriceEditText;

    //EditText field to enter the quantity of this type of book
    private EditText mQuantityEditText;

    //EditText field to enter the name of the supplier of the book
    private EditText mSupplierNameEditText;

    //EditText field to enter the phonenumber of the supplier of the book
    private EditText mSupplierPhonenumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //Find the relevant views that will be needed for the user to use the editor
        mTitleEditText = findViewById(R.id.edit_book_title);
        mAuthorEditText = findViewById(R.id.edit_book_author);
        mPriceEditText = findViewById(R.id.edit_book_price);
        mQuantityEditText = findViewById(R.id.edit_book_quantity);
        mSupplierNameEditText = findViewById(R.id.edit_book_supplier_name);
        mSupplierPhonenumberEditText = findViewById(R.id.edit_book_supplier_phonenumber);
    }

    //Get user input from the editor and save a new book into the database
    private void insertBook() {
        //Read from input fields
        //Use trim to eliminate leading or trailing with white space
        String titleString = mTitleEditText.getText().toString().trim();
        String authorString = mAuthorEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierPhonenumberString = mSupplierPhonenumberEditText.getText().toString().trim();

        //Create database helper
        mDbHelper = new BookDbHelper(this);

        //Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where column names are the keys, and the book attributes from the editor the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_TITLE, titleString);
        values.put(BookEntry.COLUMN_BOOK_AUTHOR, authorString);
        values.put(BookEntry.COLUMN_BOOK_PRICE, priceString);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_NAME, supplierNameString);
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONENUMBER, supplierPhonenumberString);

        //Insert a new row for the book in the database, returning the ID of that new row.
        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving book", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Book saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertBook();
                // Exit activity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
