package com.dailytrack.app.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.dailytrack.app.data.db.entity.ExamEntity;
import java.util.List;

@Dao
public interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ExamEntity exam);

    @Update
    void update(ExamEntity exam);

    @Delete
    void delete(ExamEntity exam);

    @Query("SELECT * FROM exams WHERE exam_date >= :now ORDER BY exam_date ASC")
    LiveData<List<ExamEntity>> getUpcomingExams(long now);

    @Query("SELECT * FROM exams ORDER BY exam_date ASC")
    LiveData<List<ExamEntity>> getAllExams();

    @Query("DELETE FROM exams")
    void deleteAll();
}
