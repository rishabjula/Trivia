package tripod.com.trivia.data;

import java.util.ArrayList;

import tripod.com.trivia.model.Question;

public interface AnswerListAsyncResponse {

    void processFinished(ArrayList<Question> questionArrayList);
}
