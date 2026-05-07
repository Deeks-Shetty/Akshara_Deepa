package com.example.aksharadeepa.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aksharadeepa.R
import com.example.aksharadeepa.data.Question
import com.example.aksharadeepa.data.QuizResult
import com.example.aksharadeepa.databinding.DialogFeedbackBinding
import com.example.aksharadeepa.databinding.FragmentQuizBinding
import com.example.aksharadeepa.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val args: QuizFragmentArgs by navArgs()

    private var questions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private var score = 0
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val allQuestions = viewModel.getQuestions(args.chapterId, args.topicId)
            // Limit to 5 questions as requested
            questions = allQuestions.shuffled().take(5)
            
            if (questions.isNotEmpty()) {
                displayQuestion()
                startTimer()
            } else {
                Toast.makeText(context, "No questions available for this selection", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        binding.nextButton.setOnClickListener {
            checkAnswerAndShowFeedback()
        }
    }

    private fun displayQuestion() {
        val q = questions[currentQuestionIndex]
        binding.questionCounter.text = String.format(Locale.getDefault(), "Question %d/%d", currentQuestionIndex + 1, questions.size)
        binding.questionText.text = q.text
        binding.optionA.text = q.optionA
        binding.optionB.text = q.optionB
        binding.optionC.text = q.optionC
        binding.optionD.text = q.optionD
        binding.optionsRadioGroup.clearCheck()
        
        // Update progress bar
        val progress = ((currentQuestionIndex + 1) * 100) / questions.size
        binding.quizProgressBar.setProgress(progress, true)
        
        if (currentQuestionIndex == questions.size - 1) {
            binding.nextButton.text = "Finish"
        } else {
            binding.nextButton.text = "Next"
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timerTextView.text = String.format(Locale.getDefault(), "00:%02d", millisUntilFinished / 1000)
            }

            override fun onFinish() {
                checkAnswerAndShowFeedback()
            }
        }.start()
    }

    private fun checkAnswerAndShowFeedback() {
        timer?.cancel()
        val selectedId = binding.optionsRadioGroup.checkedRadioButtonId
        val q = questions[currentQuestionIndex]
        val selectedAnswer = when (selectedId) {
            R.id.optionA -> "A"
            R.id.optionB -> "B"
            R.id.optionC -> "C"
            R.id.optionD -> "D"
            else -> ""
        }

        val isCorrect = selectedAnswer == q.correctAnswer
        if (isCorrect) score++

        showFeedbackDialog(isCorrect, q)
    }

    private fun showFeedbackDialog(isCorrect: Boolean, question: Question) {
        val dialogBinding = DialogFeedbackBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        dialogBinding.feedbackTitle.text = if (isCorrect) "Correct!" else "Incorrect"
        dialogBinding.feedbackTitle.setTextColor(if (isCorrect) 0xFF4CAF50.toInt() else 0xFFFF5252.toInt())
        dialogBinding.feedbackIcon.setImageResource(
            if (isCorrect) android.R.drawable.checkbox_on_background 
            else android.R.drawable.ic_delete
        )
        
        dialogBinding.correctAnswerFeedback.visibility = if (isCorrect) View.GONE else View.VISIBLE
        dialogBinding.correctAnswerFeedback.text = "Correct Answer: ${question.correctAnswer}"
        dialogBinding.explanationText.text = question.explanation

        dialogBinding.continueButton.setOnClickListener {
            dialog.dismiss()
            moveToNextQuestion()
        }

        dialog.show()
    }

    private fun moveToNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            displayQuestion()
            startTimer()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val topicIdForSaving = if (args.topicId == -1) null else args.topicId
        val result = QuizResult(
            chapterId = args.chapterId, 
            topicId = topicIdForSaving,
            score = score, 
            totalQuestions = questions.size
        )
        viewModel.saveQuizResult(result)
        
        // Update progress: Mark topic as completed if it's a topic quiz
        if (topicIdForSaving != null) {
            viewModel.markTopicAsCompleted(topicIdForSaving)
            // Increment daily goal progress
            viewModel.incrementDailyGoal()
        }
        
        val action = QuizFragmentDirections.actionQuizFragmentToQuizResultFragment(
            chapterId = args.chapterId,
            topicId = args.topicId,
            score = score,
            total = questions.size
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        _binding = null
    }
}
