package sg.edu.rp.soi.c347.knowyournationalday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    RadioGroup rgGrp1, rgGrp2, rgGrp3;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<String>();
    String code = "738964";
    String userCode;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        arrayList.add("Singapore National Day is on 9 Aug");
        arrayList.add("Singapore is 52 years old");
        arrayList.add("Theme is '#OneationTogether'");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(arrayAdapter);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        final SharedPreferences.Editor prefEdit = prefs.edit();

        userCode = prefs.getString("userCode", "");

        if (userCode.equals(code)) {
            Toast.makeText(MainActivity.this, "Welcome Back", Toast.LENGTH_LONG).show();
        } else {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout passWord = (LinearLayout) inflater.inflate(R.layout.password, null);
            final EditText etPassWord = (EditText) passWord.findViewById(R.id.etPassWord);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Please login");
            builder.setView(passWord);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (etPassWord.getText().toString().equals(code)) {

                        prefEdit.putString("userCode", etPassWord.getText().toString());
                        prefEdit.commit();

                        Toast.makeText(MainActivity.this, "Correct access code", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect access code", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });
            builder.setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "No access code entered", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sendToFriend) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend");

            builder.setItems(new String[]{"Email", "SMS"},
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Toast.makeText(MainActivity.this, "You have selected email", Toast.LENGTH_LONG).show();

                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"14036719@myrp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT, "Know Yor National Day");
                                email.putExtra(Intent.EXTRA_TEXT, arrayList.toString());

                                email.setType("message/rfc822");

                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                            } else if (which == 1)

                            {
                                Toast.makeText(MainActivity.this, "You have selected SMS", Toast.LENGTH_LONG).show();
                                Intent sms = new Intent(Intent.ACTION_VIEW);
                                sms.setData(Uri.parse("sms:"));
                                sms.putExtra("sms_body", arrayList.toString());
                                startActivity(sms);
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        } else if (item.getItemId() == R.id.quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?");

            builder.setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor prefEdit = prefs.edit();
                    prefEdit.clear();
                    prefEdit.commit();
                    finish();
                    Toast.makeText(MainActivity.this, "You chose to quit", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("NOT REALLY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Lets continue on work", Toast.LENGTH_LONG).show();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (item.getItemId() == R.id.quiz) {
            score = 0;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout quiz = (LinearLayout) inflater.inflate(R.layout.quiz, null);
            //final EditText etPassCode = (EditText) passCode.findViewById(R.id.etPassCode);
            rgGrp1 = (RadioGroup) quiz.findViewById(R.id.gp1);
            rgGrp2 = (RadioGroup) quiz.findViewById(R.id.gp2);
            rgGrp3 = (RadioGroup) quiz.findViewById(R.id.gp3);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lets do some quiz");
            builder.setView(quiz);
            builder.setCancelable(false);
            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (rgGrp1.getCheckedRadioButtonId() == R.id.rbNo1) {
                        score += 1;
                    } else {
                        score += 0;
                    }

                    if (rgGrp2.getCheckedRadioButtonId() == R.id.rbYes2) {
                        score += 1;
                    } else {
                        score += 0;
                    }

                    if (rgGrp3.getCheckedRadioButtonId() == R.id.rbYes3) {
                        score += 1;
                    } else {
                        score += 0;
                    }
                    Toast.makeText(MainActivity.this, "Score: " + score, Toast.LENGTH_LONG).show();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "No access code entered", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();
        userCode = prefs.getString("userCode", "");
    }
}


