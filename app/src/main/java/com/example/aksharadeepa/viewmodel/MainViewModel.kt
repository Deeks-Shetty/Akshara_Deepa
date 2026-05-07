package com.example.aksharadeepa.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.aksharadeepa.data.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allSubjects: LiveData<List<Subject>>
    val allQuizResults: LiveData<List<QuizResult>>
    val allQuizResultsWithChapter: LiveData<List<QuizResultWithChapter>>
    val progressPercentage: LiveData<Int>
    val gapChapters: LiveData<List<Chapter>>

    init {
        val dao = AppDatabase.getDatabase(application).appDao()
        repository = AppRepository(dao)
        allSubjects = repository.allSubjects.asLiveData()
        allQuizResults = repository.allQuizResults.asLiveData()
        allQuizResultsWithChapter = repository.allQuizResultsWithChapter.asLiveData()
        gapChapters = repository.getGapChapters().asLiveData()
        
        progressPercentage = combine(
            repository.completedTopicsCount,
            repository.totalTopicsCount
        ) { completed, total ->
            if (total > 0) (completed * 100) / total else 0
        }.asLiveData()
    }

    fun getChapters(subjectId: Int): LiveData<List<Chapter>> {
        return repository.getChapters(subjectId).asLiveData()
    }

    fun getTopics(chapterId: Int): LiveData<List<Topic>> {
        return repository.getTopics(chapterId).asLiveData()
    }

    fun updateTopic(topic: Topic) = viewModelScope.launch {
        repository.updateTopic(topic)
    }
    
    fun markTopicAsCompleted(topicId: Int) = viewModelScope.launch {
        repository.markTopicAsCompleted(topicId)
    }

    suspend fun getQuestions(chapterId: Int, topicId: Int = -1): List<Question> {
        return if (topicId != -1) {
            repository.getQuestionsByTopic(topicId)
        } else {
            repository.getQuestions(chapterId)
        }
    }

    fun saveQuizResult(result: QuizResult) = viewModelScope.launch {
        repository.saveQuizResult(result)
    }

    fun getLatestResult(chapterId: Int): LiveData<QuizResult?> {
        return repository.getLatestResult(chapterId).asLiveData()
    }

    fun getChapterMastery(chapterId: Int): LiveData<Double?> {
        return repository.getChapterMastery(chapterId).asLiveData()
    }

    fun getDailyGoal(): LiveData<DailyGoal?> {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return repository.getDailyGoal(date).asLiveData()
    }

    fun updateDailyGoal(target: Int) = viewModelScope.launch {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        repository.updateDailyGoal(DailyGoal(date, targetTopics = target))
    }

    fun incrementDailyGoal() = viewModelScope.launch {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        // Ensure goal exists first
        repository.updateDailyGoal(DailyGoal(date, targetTopics = 1))
        repository.incrementDailyGoalProgress(date)
    }

    fun clearProgress() = viewModelScope.launch {
        repository.clearProgress()
    }
}
