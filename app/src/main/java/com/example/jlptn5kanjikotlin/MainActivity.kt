package com.example.jlptn5kanjikotlin

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.jlptn5kanjikotlin.models.Vocab
import com.example.jlptn5kanjikotlin.services.CsvToVocab
import kotlinx.android.synthetic.main.main_menu.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_menu)

        kanji_button.setOnClickListener{
            val intent = Intent(this, KanjiListActivity::class.java)

            startActivity(intent)
        }
//        setContentView(R.layout.activity_main)
//
//        val listView = findViewById<ListView>(R.id.main_list_view)
//
//        val vocabList: List<Vocab> = CsvToVocab().parseCsvOfVocab( getResources().openRawResource(R.raw.jlptn5kanji))
//
//        listView.adapter = MyKanjiListAdapter(this, vocabList)
    }

    private class MyKanjiListAdapter(context: Context, vocabList: List<Vocab>) : BaseAdapter(){

        val mVocabList = vocabList

        override fun getItem(position: Int): Any {
            return "Kanji String"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return mVocabList.size
        }

        private val mContext : Context

        init {
            mContext = context
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val vocab = Vocab(5,"kana", "kanji", "type", "def")

            val layoutInflater = LayoutInflater.from(mContext)

            val rowMain = layoutInflater.inflate(R.layout.vocab_layout, parent, false)
            return populateVocabView(rowMain, mVocabList[position])
//            val textView = TextView(mContext)
//            textView.text = vocab.toString()
//            return textView
        }

        private fun populateVocabView(view: View, vocab: Vocab): View {
            val kanaView = view.findViewById<TextView>(R.id.kanaView)
            val kanjiView = view.findViewById<TextView>(R.id.kanjiView)
            val typeView = view.findViewById<TextView>(R.id.typeView)
            val descriptionView = view.findViewById<TextView>(R.id.definition_view)

            kanaView.text = vocab.kana
            kanjiView.text = vocab.kanji
            typeView.text = vocab.type
            descriptionView.text = vocab.definition

//            if (vocab.type.contains("n ") || vocab.type == "n"  || vocab.type.contains("n,"))
//                view.setBackgroundColor(Color.parseColor("#ff0000"))

            return view
        }

    }
}
