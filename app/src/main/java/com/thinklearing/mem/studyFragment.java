package com.thinklearing.mem;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;

import com.thinklearing.mem.Data.DataModel;
import com.thinklearing.mem.Data.StaticData;
import com.thinklearing.mem.TinderCard.CardStackAdapter;
import com.thinklearing.mem.TinderCard.SpotDiffCallback;
import com.thinklearing.mem.TinderCard.Vocabulary;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class studyFragment extends BaseFragment implements CardStackListener {

    ArrayList<Vocabulary> vocabularies;
    ArrayList<Vocabulary> temp;
    CardStackView cardStackView;
    CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private List<DataModel> data;
    private int level;

    int index;
    int position = 0;

    TextView indexText;

    public studyFragment(List<DataModel> data, int level) {
        this.data = data;
        this.level = level;
        LayoutId = R.layout.fragment_study;

    }

    @Override
    protected void initFragment() {
        super.initFragment();
        activity.setTitle(StaticData.Title);
        indexText = getActivity().findViewById(R.id.stdTxtIndex);
        TextView levelText = getActivity().findViewById(R.id.stdTxtLevel);
        levelText.setText("Level " + level);
        cartInit();
    }

    private void cartInit() {
        cardStackView = activity.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), this);
        manager.setStackFrom(StackFrom.None);
//        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.5f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(false);
        manager.setSwipeableMethod(SwipeableMethod.Manual);

        cardStackView.setLayoutManager(manager);
        prepareList();
        adapter = new CardStackAdapter(getContext(), vocabularies);
        cardStackView.setAdapter(adapter);
        indexText.setText(String.valueOf(adapter.getItemCount() - index+1));
        index = 0;
//        cardStackView.swipe();


    }

    private void prepareList() {
        vocabularies = new ArrayList<>();
        temp = new ArrayList<>();
        int index = 0;
        for (DataModel _data : data) {
            vocabularies.add(new Vocabulary(index, _data.Word, _data.Eq, " "));
            index++;
        }
        Log.i("prepareList", "vocabularies hazı");

    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
//        Log.d("onCardDragging: ", "" + ratio);
    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction.name().equals("Left")) {
            temp.add(vocabularies.get(this.position));
            Log.d("temp ekle ", vocabularies.get(this.position).getWord());

        }

        Log.w("getItemCount--index ", adapter.getItemCount() + " " + (index + 1));
        if (adapter.getItemCount() <= (index + 1)) {
            Log.i("temp: ", temp.size() + " ");
            vocabularies = temp;
            adapter.setItems(vocabularies);
            adapter.notifyDataSetChanged();
            index = 0;
            temp = new ArrayList<>();
            Log.i("vocabularies: ", vocabularies.size() + " ");

        } else
            index++;

        indexText.setText(String.valueOf(adapter.getItemCount() - index));
        if(adapter.getItemCount()==0){
            ((BaseActivity)getActivity()).showMessage("Çalışma Bitti");
            getActivity().onBackPressed();
        }
        Log.d("onCardSwiped: ", direction.name());

    }

    @Override
    public void onCardRewound() {
//        Log.d("onCardRewound: ", "onCardRewound");
    }

    @Override
    public void onCardCanceled() {
        Log.i("onCardCanceled: ", "dsdsdsd");
    }

    @Override
    public void onCardAppeared(View view, int position) {
        this.position = position;
//        Log.i("onCardAppeared: ", "ÖĞRENDİM--" + vocabularies.get(position).getWord());
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        this.position = position;

//        Log.i("onCardDisappeared: ", "TEKRAR--" + vocabularies.get(position).getWord());
//        vocabularies.add(new Vocabulary(vocabularies.get(position).getWord(),
//                vocabularies.get(position).getTranslate(), " "));
//        cardStackView.setAdapter(adapter);
//
    }


//    private void paginate(Vocabulary vocabulary) {
//        List<Vocabulary> old = adapter.getItems();
//        vocabulary.setId(old.size() + 1);
//        old.add(vocabulary);
//        List<Vocabulary> _new = old;
//        SpotDiffCallback callback = new SpotDiffCallback(old, _new);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//        adapter.setItems(_new);
//        result.dispatchUpdatesTo(adapter);
//        adapter.notifyDataSetChanged();
//    }
}
