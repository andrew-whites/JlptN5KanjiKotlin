package com.example.jlptn5kanjikotlin

import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.jlptn5kanjikotlin.models.Kanji
import com.example.jlptn5kanjikotlin.services.CsvToKanji
import kotlinx.android.synthetic.main.kanji_game.*
import kotlin.random.Random

class KanjiGameActivity : AppCompatActivity(){

    private var currentIndex = 0
    private var maxAttempt = 3
    private var attempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.kanji_game)
        // House keeping
        next_button.visibility = View.INVISIBLE
        kana_and_reading.text = ""

        // Get initial list.
        val kanjiList: List<Kanji> = CsvToKanji().parseCsvOfVocab(getResources().openRawResource(R.raw.jlptn5kanjilist))
        // Get list passed from menu
        val selectedKanjiList = intent.getStringArrayListExtra("kanji") as ArrayList<Int>
        var testingKanji = copyToList(selectedKanjiList)
        // Set current context the the first element in the passed list (maybe change to picking a random number :/ ).
        currentIndex = selectedKanjiList[0]
        // set the question from the current context
        kanji_question.text = kanjiList[currentIndex].kanji

        next_button.setOnClickListener{
            testingKanji.remove(currentIndex)
            if(testingKanji.isEmpty()){
                testingKanji = copyToList(selectedKanjiList)
            }
            currentIndex = testingKanji[Random.nextInt(0,testingKanji.size)]
            kanji_question.text = newQuestion(kanjiList, currentIndex)
        }

        answer.setOnKeyListener(View.OnKeyListener{ v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if(testAnswer(answer, kanjiList[currentIndex]) || attempts == maxAttempt) {
                    next_button.visibility = View.VISIBLE
                    kana_and_reading.text = newKanaAndReadingText(kanjiList[currentIndex])
                    attempts = 0
                    return@OnKeyListener true
                }
                attempts++
                Toast.makeText(this, attempts.toString() +
                        (if(attempts > 1) " attempts" else " attempt") +" made, " +
                        ((maxAttempt-attempts) + 1).toString() +
                        (if(maxAttempt == attempts)  " attempt left." else " attempts left."),
                    Toast.LENGTH_LONG).show()
            }
            false
        })
    }

    fun testAnswer(guess: EditText, kanji: Kanji) : Boolean{
        val mGuess = guess.text
        for (r in kanji.readingList){
            if(mGuess.toString() != "" && r.toLowerCase().equals(mGuess.toString().toLowerCase()))
                return true
        }
        for (k in kanji.kanaList){
            if(mGuess.toString() != "" && k.toLowerCase().equals(mGuess.toString().toLowerCase()))
                return true
        }

        return false
    }

    fun newQuestion(kanjiList: List<Kanji>, index: Int) : String{
        next_button.visibility = View.INVISIBLE
        kana_and_reading.text = ""
        answer.setText("")
        return kanjiList[index].kanji
    }

    fun newKanaAndReadingText(kanji: Kanji) : String{
        return kanji.kana + "\n" + kanji.reading + "\n" + kanji.definition
    }

    fun copyToList(listB: ArrayList<Int>) : ArrayList<Int>{
        var listA = arrayListOf<Int>()
        for (k in listB){
            listA.add(k)
        }
        return listA
    }
}