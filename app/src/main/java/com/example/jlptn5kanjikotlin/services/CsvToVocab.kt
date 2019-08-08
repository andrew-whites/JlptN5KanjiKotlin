package com.example.jlptn5kanjikotlin.services

import android.content.res.Resources
import com.example.jlptn5kanjikotlin.models.Vocab
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.io.*

public class CsvToVocab() {

//    val mCsvFile = csvFile
    fun parseCsvOfVocab(csv: InputStream): List<Vocab> {

//        var fileReader: BufferedReader? = null
        var csvReader: CSVReader? = null

        var vocabList = ArrayList<Vocab>()

        try {
            println("--- Read line by line ---")

//            InputStreamReader(csv) = csv
            csvReader = CSVReader(InputStreamReader(csv))

            var record: Array<String>?
            csvReader.readNext() // skip Header

            record = csvReader.readNext()
            while (record != null) {
                println(record[0] + " | " + record[1] + " | " + record[2] + " | " + record[3])
                var vocab = Vocab(record[0].toInt(), record[1], record[2], record[3], record[4])
                vocabList.add(vocab)
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

        return vocabList
    }
}