package com.example.jlptn5kanjikotlin.services

import com.example.jlptn5kanjikotlin.models.Kanji
import com.example.jlptn5kanjikotlin.models.Vocab
import com.opencsv.CSVReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

public class CsvToKanji() {

    //    val mCsvFile = csvFile
    fun parseCsvOfVocab(csv: InputStream): List<Kanji> {

//        var fileReader: BufferedReader? = null
        var csvReader: CSVReader? = null

        var kanjiList = ArrayList<Kanji>()

        try {
            println("--- Read line by line ---")

//            InputStreamReader(csv) = csv
            csvReader = CSVReader(InputStreamReader(csv))

            var record: Array<String>?
            csvReader.readNext() // skip Header

            record = csvReader.readNext()
            while (record != null) {
                println(record[0] + " | " + record[1] + " | " + record[2] + " | " + record[3])
                var kanji = Kanji(record[0], record[1], record[2], record[3], record[1].split("・"), record[2].replace(" ", "").split("/"))
                kanjiList.add(kanji)
                record = csvReader.readNext()
            }

            csvReader.close()
        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
        } finally {
            try {
//                fileReader!!.close()
                csvReader!!.close()
            } catch (e: IOException) {
                println("Closing fileReader/csvParser Error!")
                e.printStackTrace()
            }
        }

        return kanjiList
    }
}