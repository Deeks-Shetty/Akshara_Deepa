package com.example.aksharadeepa.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val icon: String? = null // For UI display
)

@Entity(
    tableName = "chapters",
    foreignKeys = [ForeignKey(
        entity = Subject::class,
        parentColumns = ["id"],
        childColumns = ["subjectId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Chapter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val subjectId: Int,
    val name: String
)

@Entity(
    tableName = "topics",
    foreignKeys = [ForeignKey(
        entity = Chapter::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterId: Int,
    val name: String,
    val isCompleted: Boolean = false
)

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = Chapter::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Topic::class,
            parentColumns = ["id"],
            childColumns = ["topicId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterId: Int,
    val topicId: Int? = null, // Linked to a specific topic
    val text: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String,
    val explanation: String
)

@Entity(
    tableName = "quiz_results",
    foreignKeys = [ForeignKey(
        entity = Chapter::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class QuizResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterId: Int,
    val topicId: Int? = null, // Can be null for chapter-wide quiz
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)

data class QuizResultWithChapter(
    val id: Int,
    val chapterId: Int,
    val chapterName: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long
)

@Entity(tableName = "daily_goals")
data class DailyGoal(
    @PrimaryKey val date: String, // Format: YYYY-MM-DD
    val targetTopics: Int = 1,
    val completedTopics: Int = 0
)

@Entity(tableName = "app_settings")
data class AppSetting(
    @PrimaryKey val key: String,
    val value: String
)
