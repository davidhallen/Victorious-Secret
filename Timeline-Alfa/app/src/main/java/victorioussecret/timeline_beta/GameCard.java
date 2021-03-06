package victorioussecret.timeline_beta;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.CENTER_HORIZONTAL;

/**
 * Created by Piotri on 2015-05-16.
 */
public class GameCard extends LinearLayout {

    private String state;

    private Resources resCards;
    private Drawable d_card;
    private Drawable d_markedCard;
    private Drawable d_wrongCard;

    //Drawables defined for use of specific shapes for Bing Bang & Ragnarok cards
    private Drawable d_bigBang;
    private Drawable d_markedBigbang;
    private Drawable d_ragnarok;
    private Drawable d_markedRagnarok;

    Context mContext;

    private TextView yearView;
    private TextView yearLabelView;
    private TextView questionView;
    private LinearLayout yearLayout;

    private String question;
    private String yearLabel;
    private int year;

    LinearLayout.LayoutParams layoutParams =
            new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER_HORIZONTAL);
    LinearLayout.LayoutParams vg1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams vg2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
    LinearLayout.LayoutParams vg3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public GameCard(Context context, Question q) {
        super(context);
        mContext = context;
        loadResources();

        state = "NORMAL";
        question = q.getQuestion();
        year = q.getYear();
        yearLabel = q.getYearLabel();
        setGravity(CENTER_HORIZONTAL);

        yearLayout = new LinearLayout(mContext);
        yearLayout.setOrientation(HORIZONTAL);
        yearLayout.setLayoutParams(vg3);
        vg3.setMargins(0, 0, 0, 0);
        yearLayout.setPadding(0, 0, 0, 0);

        layoutParams.setMargins(12, 0, 0, 0);
        setOrientation(LinearLayout.VERTICAL);
        setClickable(true);
        setBackground(d_card);
        setLayoutParams(layoutParams);

        yearView = new TextView(mContext);
        yearView.setPadding(0, 20, 0, 0);
        yearView.setTextSize(30);
        vg1.gravity = Gravity.CENTER;
        vg1.setMargins(0, 0, 5, 0);
        yearView.setLayoutParams(vg1);
        yearView.setText(String.valueOf(Math.abs(year)));

        yearLabelView = new TextView(mContext);
        yearLabelView.setTextSize(15);
        yearLabelView.setLayoutParams(vg1);
        yearLabelView.setPadding(0, 57, 0, 0);
        yearLabelView.setText(yearLabel);
        yearLabelView.setTextColor(Color.parseColor("#FF696769"));
        yearLabelView.setLayoutParams(vg1);

        questionView = new TextView(mContext);
        questionView.setPadding(0, 30, 0, 0);
        questionView.setTextSize(13);
        questionView.setGravity(CENTER_HORIZONTAL);
        questionView.setLayoutParams(vg2);
        questionView.setText(question);

        yearLayout.addView(yearView);
        yearLayout.addView(yearLabelView);
        addView(yearLayout);
        addView(questionView);

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setState(String s) {

        if (s == "NORMAL")
            setBackground(d_card);
        else if (s == "MARKED")
            setBackground(d_markedCard);
        else if (s == "WRONG")
            setBackground(d_wrongCard);
    }

    private void loadResources() {
        resCards = getResources();
        d_card = resCards.getDrawable(R.drawable.card);
        d_markedCard = resCards.getDrawable(R.drawable.marked_card);
        d_bigBang = resCards.getDrawable(R.drawable.bigbang);
        d_markedBigbang = resCards.getDrawable(R.drawable.marked_bigbang);
        d_ragnarok = resCards.getDrawable(R.drawable.ragnarrok);
        d_markedRagnarok = resCards.getDrawable(R.drawable.marked_ragnarok);
        d_wrongCard = resCards.getDrawable(R.drawable.wrong_card);
    }
}
