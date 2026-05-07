package com.example.aksharadeepa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM subjects")
    fun getAllSubjects(): Flow<List<Subject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<Subject>)

    @Query("SELECT * FROM chapters WHERE subjectId = :subjectId")
    fun getChaptersBySubject(subjectId: Int): Flow<List<Chapter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<Chapter>)

    @Query("SELECT * FROM topics WHERE chapterId = :chapterId")
    fun getTopicsByChapter(chapterId: Int): Flow<List<Topic>>

    @Update
    suspend fun updateTopic(topic: Topic)

    @Query("UPDATE topics SET isCompleted = 1 WHERE id = :topicId")
    suspend fun markTopicAsCompleted(topicId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<Topic>)

    @Query("SELECT * FROM questions WHERE chapterId = :chapterId")
    suspend fun getQuestionsByChapter(chapterId: Int): List<Question>

    @Query("SELECT * FROM questions WHERE topicId = :topicId")
    suspend fun getQuestionsByTopic(topicId: Int): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: QuizResult)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllQuizResults(): Flow<List<QuizResult>>

    @Query("SELECT qr.id, qr.chapterId, c.name as chapterName, qr.score, qr.totalQuestions, qr.timestamp FROM quiz_results qr INNER JOIN chapters c ON qr.chapterId = c.id ORDER BY qr.timestamp DESC")
    fun getAllQuizResultsWithChapter(): Flow<List<QuizResultWithChapter>>

    @Query("SELECT * FROM quiz_results WHERE chapterId = :chapterId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestQuizResult(chapterId: Int): Flow<QuizResult?>

    @Query("SELECT AVG(score * 100.0 / totalQuestions) FROM quiz_results WHERE chapterId = :chapterId")
    fun getChapterMastery(chapterId: Int): Flow<Double?>

    @Query("SELECT chapters.* FROM chapters INNER JOIN quiz_results ON chapters.id = quiz_results.chapterId GROUP BY chapters.id HAVING AVG(quiz_results.score * 100.0 / quiz_results.totalQuestions) < 50")
    fun getGapChapters(): Flow<List<Chapter>>

    @Query("SELECT * FROM daily_goals WHERE date = :date")
    fun getDailyGoal(date: String): Flow<DailyGoal?>

    @Query("SELECT * FROM daily_goals WHERE date = :date")
    suspend fun getDailyGoalSync(date: String): DailyGoal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyGoal(goal: DailyGoal)

    @Query("UPDATE daily_goals SET completedTopics = completedTopics + 1 WHERE date = :date")
    suspend fun updateDailyGoalProgress(date: String)

    @Transaction
    suspend fun incrementDailyGoalProgress(date: String) {
        val goal = getDailyGoalSync(date)
        if (goal == null) {
            insertDailyGoal(DailyGoal(date, targetTopics = 1, completedTopics = 1))
        } else {
            updateDailyGoalProgress(date)
        }
    }

    @Query("SELECT * FROM app_settings WHERE `key` = :key")
    suspend fun getSetting(key: String): AppSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: AppSetting)
    
    @Query("SELECT COUNT(*) FROM topics WHERE isCompleted = 1")
    fun getCompletedTopicsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM topics")
    fun getTotalTopicsCount(): Flow<Int>

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    suspend fun getChapterById(chapterId: Int): Chapter?

    @Transaction
    suspend fun clearProgress() {
        deleteQuizResults()
        resetTopicsCompletion()
        deleteDailyGoals()
    }

    @Query("DELETE FROM quiz_results")
    suspend fun deleteQuizResults()

    @Query("UPDATE topics SET isCompleted = 0")
    suspend fun resetTopicsCompletion()

    @Query("DELETE FROM daily_goals")
    suspend fun deleteDailyGoals()
}

@Database(entities = [Subject::class, Chapter::class, Topic::class, Question::class, QuizResult::class, DailyGoal::class, AppSetting::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "akshara_deepa_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
