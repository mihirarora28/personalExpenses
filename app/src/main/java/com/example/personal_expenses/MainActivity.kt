package com.example.personal_expenses

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import java.time.Duration

class MainActivity : AppCompatActivity() {
     val keys = "Mihir"
    var lists = mutableListOf<expense>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listviews = findViewById<ListView>(R.id.lst1);


         var cc = adapter1(lists);
        listviews.adapter = cc;
       getPReferences();
       calculateScore();
        var reset = findViewById<Button>(R.id.reset);

        reset.setOnClickListener {
            var builder = AlertDialog.Builder(this);
            with(builder){
                setTitle("ALERT !")
                setMessage("Do you really want to clear the list")
                setNegativeButton("NO", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }

                })
                setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        lists.clear();
                        cc.notifyDataSetChanged();
                        calculateScore();
                        savePreferences();
                    }

                })

            }
            builder.create().show()
        }
        ////////////////
        var add = findViewById<Button>(R.id.add);
        add.setOnClickListener {
            var builder = AlertDialog.Builder(this);
            var xx = LayoutInflater.from(this).inflate(R.layout.alert, null);
            var e1 = xx.findViewById<EditText>(R.id.etn1)
            var e2 = xx.findViewById<EditText>(R.id.etn2)

            with(builder){
                setTitle("Add your Product !")
                setView(xx);
                setNegativeButton("NO", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        ;
                    }

                })
                setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                            lists.add(expense(e1.text.toString(), e2.text.toString().toDouble()))
                            savePreferences();
                            calculateScore();

                    }

                })

            }
            builder.create().show()
        }
    }

    private fun calculateScore() {
        var Score :Double = 0.0;
       for(i in  lists){
           Score+=i.Value;
       }
        var s: TextView = findViewById(R.id.Score);
        s.setText(String.format("%.2f", Score));
//        s.text = "${Score.toString()} Rupees" ;
    }
    private fun savePreferences(){
        var shrd = getPreferences(Context.MODE_PRIVATE);
        var gson = Gson()
        var items = lists.map { gson.toJson(it) }
        with(shrd.edit()){
            putStringSet(keys, items.toSet())
            commit()
        }
    }
    private fun getPReferences(){
        val shrd = getPreferences(Context.MODE_PRIVATE);
        val gson = Gson()
        val items =  shrd.getStringSet(keys, null);

        items?.forEach {
            lists.add(gson.fromJson(it,expense::class.java));
        }

    }
}