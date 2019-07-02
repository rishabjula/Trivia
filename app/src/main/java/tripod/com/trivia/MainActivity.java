package tripod.com.trivia;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tripod.com.trivia.data.AnswerListAsyncResponse;
import tripod.com.trivia.data.QuestionBank;
import tripod.com.trivia.model.Question;

import static android.graphics.Color.GREEN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView CounterTextView, questionTextView;
    private Button trueButton , falseButton;
    private ImageButton prevImageButton , nextImageButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CounterTextView = findViewById(R.id.counterTextView);
        questionTextView = findViewById(R.id.question_textview);
        trueButton = findViewById(R.id.TrueButton);
        falseButton = findViewById(R.id.FalseButton);
        prevImageButton = findViewById(R.id.prevButton);
        nextImageButton = findViewById(R.id.nextButton);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevImageButton.setOnClickListener(this);
        nextImageButton.setOnClickListener(this);


      questionList =  new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
          @Override
          public void processFinished(ArrayList<Question> questionArrayList) {
              questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());

          }
      });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.nextButton:
                currentQuestionIndex = (currentQuestionIndex +1) % questionList.size() ;
                updateQuestion();
                CounterTextView.setText(currentQuestionIndex + "/" +questionList.size());
                break;
            case R.id.prevButton:
             if(currentQuestionIndex> 0)
             {
                 currentQuestionIndex =(currentQuestionIndex -1) % questionList.size();
                 updateQuestion();
                 CounterTextView.setText(currentQuestionIndex + "/" +questionList.size());
             }
                break;
            case R.id.TrueButton:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.FalseButton:
                checkAnswer(false);
                updateQuestion();
                break;


        }
    }

    private void checkAnswer(boolean b) {

        Boolean ActualAnswer = questionList.get(currentQuestionIndex).isAnswerTrue();
        if(b==ActualAnswer)
        {   fadeView();
            Toast.makeText(this,"Correct!",Toast.LENGTH_LONG).show();
        }
        else {
            shake();
            Toast.makeText(this,"InCorrect!",Toast.LENGTH_LONG).show();
        }
    }

    private void updateQuestion() {

        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
    }

    private void shake(){

        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeView() {

        final CardView cardView = findViewById(R.id.cardView);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
