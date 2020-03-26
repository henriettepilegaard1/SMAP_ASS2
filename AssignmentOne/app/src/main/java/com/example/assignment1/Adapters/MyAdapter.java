package com.example.assignment1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1.R;
import com.example.assignment1.UpdateWordObject;
import com.example.assignment1.WordObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable
{
    Context context;
    List<WordObject> wordObjectList;
    UpdateWordObject updateWordObject;
    List<WordObject> wordObjectsAll;


    public MyAdapter(Context ct, List<WordObject> wordObjectList, UpdateWordObject updateWordObject)
    {
        context = ct;
        this.wordObjectList = wordObjectList;
        this.updateWordObject = updateWordObject;
        this.wordObjectsAll = new ArrayList<>(wordObjectList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.animal_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        final WordObject wordObject = wordObjectList.get(position);
        holder.myName.setText(wordObject.getWord());
        holder.myPron.setText(wordObject.getPronunciation());
        //holder.myImage.setImageResource(wordObject.getImage()); //Todo: Fix
        holder.myRating.setText(wordObject.getRating());
        holder.mainLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateWordObject.UpdateWordObjectMethod(wordObject, position);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return wordObjectList.size();
    }

    @Override
    public Filter getFilter()
    {
        return filter;
    }

    Filter filter= new Filter()
    {
        //The method is run on a background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence)
        {
            List<WordObject> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty())
            {
                filteredList.addAll(wordObjectsAll);
            }
            else
            {
                for (WordObject word: wordObjectsAll)
                {
                    if (word.getWord().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                     filteredList.add(word);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //The method is run on a UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
            wordObjectList.clear();
            wordObjectList.addAll((Collection<? extends WordObject>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView myPron, myName, myRating;
        ImageView myImage;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            myPron = itemView.findViewById(R.id.PronAnimal);
            myName = itemView.findViewById(R.id.NameOfAnimal);
            myImage = itemView.findViewById(R.id.PicOfAnimal);
            mainLayout = itemView.findViewById(R.id.MainLayout);
            myRating = itemView.findViewById(R.id.score);
        }
    }
}
