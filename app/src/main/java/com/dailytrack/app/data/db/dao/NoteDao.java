package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.NoteEntity;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NoteEntity note);

    @Update
    void update(NoteEntity note);

    @Delete
    void delete(NoteEntity note);

    @Query("SELECT * FROM notes ORDER BY updated_at DESC")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("SELECT * FROM notes WHERE category = :category ORDER BY updated_at DESC")
    LiveData<List<NoteEntity>> getNotesByCategory(String category);

    @Query("SELECT * FROM notes WHERE title LIKE :query OR content LIKE :query ORDER BY updated_at DESC")
    LiveData<List<NoteEntity>> searchNotes(String query);

    @Query("SELECT COUNT(*) FROM notes WHERE date(created_at/1000, 'unixepoch', 'localtime') = date(:date/1000, 'unixepoch', 'localtime')")
    LiveData<Integer> getNotesCountForDate(long date);

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    NoteEntity getNoteSync(long id);

    @Query("DELETE FROM notes")
    void deleteAll();
}
