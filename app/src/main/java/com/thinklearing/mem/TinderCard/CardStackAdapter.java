package com.thinklearing.mem.TinderCard;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thinklearing.mem.Data.DataBaseArg;
import com.thinklearing.mem.Data.DataBaseHelper;
import com.thinklearing.mem.Data.StaticData;
import com.thinklearing.mem.R;

import java.util.List;
import java.util.Locale;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    private List<Vocabulary> vocabularies;
    TextToSpeech t1;
    Context context;
    DataBaseHelper dbHelper;

    public CardStackAdapter(Context context, List<Vocabulary> objects) {
        this.vocabularies = objects;
        this.context = context;
        this.dbHelper = new DataBaseHelper(context);
        inflater = LayoutInflater.from(context);
        Log.i("adapter", " " + objects.size());
        t1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tinder_card_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vocabulary selectedProduct = vocabularies.get(position);
        holder.setData(selectedProduct, position);
        Log.i("setData", "selectedProduct");

    }

    public void setItems(List<Vocabulary> items) {
        vocabularies = items;
    }

    public List<Vocabulary> getItems() {
        return vocabularies;
    }

    @Override
    public long getItemId(int position) {
        return vocabularies.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{//} implements View.OnClickListener {
        TextView word;
        TextView translate;
        TextView sentence;
        ImageView speak;
        ImageView favorit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.txt_word);
            translate = itemView.findViewById(R.id.txt_translate);
            sentence = itemView.findViewById(R.id.txt_sentence);
            speak = itemView.findViewById(R.id.imageView4);
            favorit = itemView.findViewById(R.id.imageView3);

            if(StaticData.Category==DataBaseArg.TBL_klm)
                favorit.setVisibility(View.INVISIBLE);


            speak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    t1.speak(word.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            });


            favorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("favorit-ID", "clickk");
                    int id = dbHelper.getId(DataBaseArg.TBL_klm);
                    int level = 0;
                    if (id != -1)
                        level = (int) (Math.ceil(id / DataBaseArg.LevelSize));
                    Log.w("favorit-ID", id + " "+level);
                    dbHelper.VeriEkle(
                            DataBaseArg.TBL_klm,
                            new String[]{DataBaseArg.Word, DataBaseArg.Eq,DataBaseArg.Level},
                            new String[]{word.getText().toString(), translate.getText().toString(),String.valueOf(level)}
                            );
                    Toast.makeText(context, "Eklendi", Toast.LENGTH_SHORT).show();
                    favorit.setEnabled(false);
                }
            });


//            deleteproduct = (ImageView) itemView.findViewById(R.id.deleteproduct);
//            deleteproduct.setOnClickListener(this);

        }

        public void setData(Vocabulary selectedProduct, int position) {

            this.word.setText(selectedProduct.getWord());
            this.sentence.setText(selectedProduct.getSentence());
            this.translate.setText(selectedProduct.getTranslate());


        }

//        @Override
//        public void onClick(View view) {
//            Log.i("onClick", "onClick");
//        }
    }


}


