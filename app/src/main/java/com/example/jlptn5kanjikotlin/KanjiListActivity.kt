package com.example.jlptn5kanjikotlin

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.example.jlptn5kanjikotlin.models.Kanji
import com.example.jlptn5kanjikotlin.services.CsvToKanji

class KanjiListActivity : AppCompatActivity(){

    val listOfSelectedItems = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mActionbar = supportActionBar
        mActionbar!!.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById<ListView>(R.id.main_list_view)

        val kanjiList: List<Kanji> = CsvToKanji().parseCsvOfVocab(getResources().openRawResource(R.raw.jlptn5kanjilist))

        listView.adapter = KanjiListAdapter(this, kanjiList)

        listView.setOnItemClickListener { adapterView, view, position: Int, id: Long ->

            if(!listOfSelectedItems.contains(position)){
                view.setBackgroundColor(Color.parseColor("#77df79"))
                listOfSelectedItems.add(position)
            }else{
                view.setBackgroundColor(Color.parseColor("#ffffff"))
                listOfSelectedItems.remove(position)
            }

            println(listOfSelectedItems)
//            val background = view.background as? ColorDrawable
//            view.setTag(R.color.childArticle)
////            background.color
//            Toast.makeText(this, background!!.color, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_kanji_list_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        else if (item?.itemId == R.id.play){
            if(listOfSelectedItems.size > 0) {
                val intent: Intent = Intent(this, KanjiGameActivity::class.java)
                intent.putExtra("kanji", listOfSelectedItems)
                startActivity(intent)
                return true
            }
            Toast.makeText(this, "Please select some kanji.", Toast.LENGTH_SHORT).show()
        }

        println("Nope!")
        return super.onOptionsItemSelected(item)
    }

    private class KanjiListAdapter(context: Context, kanjiList: List<Kanji>) : BaseAdapter(){

        val mKanjiList = kanjiList
        val mContext : Context = context

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)

            val rowMain = layoutInflater.inflate(R.layout.vocab_layout, parent, false)

            return populateKanjiView(rowMain, mKanjiList[position])
        }

        override fun getItem(position: Int): Any {
            println("Kanji String")
            return "Kanji String"
        }

        override fun getItemId(position: Int): Long {
            println(mKanjiList[position].kanji)
            return position.toLong()
        }

        override fun getCount(): Int {
            return mKanjiList.count()
        }

        private fun populateKanjiView(view: View, kanji: Kanji): View{
            val kanaView = view.findViewById<TextView>(R.id.kanjiView)
            val kanjiView = view.findViewById<TextView>(R.id.kanaView)
            val readingView = view.findViewById<TextView>(R.id.typeView)
            val definitionView = view.findViewById<TextView>(R.id.definition_view)

            kanjiView.text = kanji.kanji
            kanaView.text = kanji.kana
            readingView.text = kanji.reading
            definitionView.text = kanji.definition

            return view
        }

    }

//    class CustomViewHolder(view: View): BaseAdapter.View
}