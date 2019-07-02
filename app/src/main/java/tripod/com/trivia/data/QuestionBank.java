package tripod.com.trivia.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import tripod.com.trivia.controller.AppController;
import tripod.com.trivia.model.Question;

public class QuestionBank {
private Context qbContext ;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";


   public List<Question> getQuestions(final AnswerListAsyncResponse callBack){


       JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.GET, url, (JSONArray) null,
               new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                      // Log.d("JSON Suff","  "+response);
                     for (int i =0 ; i<response.length(); i++)
                     {
                         try {
                             Question question = new Question();
                             question.setAnswer(response.getJSONArray(i).get(0).toString());
                             question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

                          //Add questions to list

                          questionArrayList.add(question);

                          // Log.d("JSON","OnResponse"+response.getJSONArray(i).get(0));
                        //     Log.d("JSON","OnResponse"+response.getJSONArray(i).get(1));
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                       if (null != callBack) callBack.processFinished(questionArrayList);
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       });

       AppController.getInstance(qbContext).addToRequestQueue(jsonArrayRequest);

       return questionArrayList;
   }

}
