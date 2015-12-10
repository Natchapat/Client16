package com.android.client16;

/**
 * Created by Alpaca_Alice on 3/12/2558.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Spring-Sama on 7/21/2015.
 */
public class Connected extends Activity {
    Button buttonClear,buttonDisconnect,buttonComet,buttonServiceLine,buttonPandd,buttonSend;
    TextView textResponse,statusComet,statusServiceLine,statusPandd;
    EditText commandSend;
    String dstAddress;
    Integer dstPort;
    ScrollView scrollview;
    BufferedWriter out;
    BufferedReader in;
    Socket socket;
    String Ipandport,txt="";
    String inMsg2="";
    String stone="Arrived at Goal1\n",sttwo="Arrived at Goal2\n";
    String staone="Going to Goal1\n",statwo="Going to Goal2\n";
    String statone="Receive from goal1\n",stattwo="Receive from goal2\n";
    String error="Error: Failed going to goal\n";
    String pass="Enter password:\n";
    String beforecommand="";
    String inMsg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected);
        Bundle extras = getIntent().getExtras();
        this.dstAddress = extras.getString("dstAddress");
        this.dstPort = extras.getInt("dstPort");
        Ipandport = "IP :" + dstAddress + "\nPort :" + dstPort + "\n";
        txt = Ipandport;
        MyClientTask myClientTask = new MyClientTask(dstAddress, dstPort);
        myClientTask.execute();

        scrollview = (ScrollView) findViewById(R.id.scrollView);
        textResponse = (TextView) findViewById(R.id.textresponse);
        statusServiceLine = (TextView) findViewById(R.id.textView2);
        statusPandd = (TextView) findViewById(R.id.textView3);
        commandSend = (EditText) findViewById(R.id.editText);
        textResponse.setText(txt);
        scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollview.post(new Runnable() {
                    public void run() {
                        scrollview.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }
    public void Clearbtn (View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                txt = Ipandport;
                textResponse.setText(Ipandport);
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void Disconbtn (View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void Dockwork (View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                try {
                    String outMsg = "dock"+ System.getProperty("line.separator");
                    txt = txt+outMsg;
                    textResponse.setText(txt);
                    out.write(outMsg);
                    out.flush();
                    Log.i("TcpClient", "sent: " + outMsg);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

        });


        AlertDialog alert = builder.create();
        alert.show();
    }
    public void Undockwork (View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                try {
                    String outMsg = "undock" + System.getProperty("line.separator");
                    txt = txt + outMsg;
                    textResponse.setText(txt);
                    out.write(outMsg);
                    out.flush();
                    Log.i("TcpClient", "sent: " + outMsg);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Send (View view)
    {
        String sendTxt = commandSend.getText().toString();
        try {
            String outMsg = sendTxt+ System.getProperty("line.separator");
            out.write(outMsg);
            out.flush();
            //System.out.println(outMsg);
            Log.i("TcpClient", "sent: " + outMsg);
            txt = txt+outMsg;
            textResponse.setText(txt);
            commandSend.setText("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Station1 (View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                try {
                    String outMsg = "goto goal1"+ System.getProperty("line.separator");
                    txt = txt+outMsg;
                    textResponse.setText(txt);
                    statusServiceLine.setText("Sending to FOLA");
                    SystemClock.sleep(100);
                    out.write(outMsg);
                    out.flush();
                    Log.i("TcpClient", "sent: " + outMsg);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void Station2 (View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                try {
                    String outMsg = "goto goal2"+ System.getProperty("line.separator");
                    txt = txt+outMsg;
                    textResponse.setText(txt);
                    statusPandd.setText("Sending to Cleaning");
                    SystemClock.sleep(100);
                    out.write(outMsg);
                    out.flush();
                    Log.i("TcpClient", "sent: " + outMsg);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;

        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                socket = new Socket(dstAddress, dstPort);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                while (socket.isConnected()) {
                    //accept server response
                    beforecommand = inMsg;
                    inMsg = in.readLine() + System.getProperty("line.separator");
                    //System.out.println(inMsg);
                    //String inMsg2 = in.readLine();
                    Log.i("TcpClient", "received: " + inMsg);

                    if(pass.equals(inMsg))
                    {
                        try{
                            out.write("adept\n");
                            out.flush();
                        }catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(stone.equals(inMsg))
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                statusServiceLine.setText("Arrived FOLA station");
                            }
                        });
                    }
                    else if(sttwo.equals(inMsg))
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                statusPandd.setText("Arrived Cleaning station");
                            }
                        });
                    }
                    else if((statone.equals(inMsg))|| (staone.equals(inMsg)))
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                statusServiceLine.setText("Receive from FOLA");
                            }
                        });
                    }
                    else if(stattwo.equals(inMsg)||(statwo.equals(inMsg)))
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                statusPandd.setText("Receive from Cleaning");
                            }
                        });
                    }
                    else if((beforecommand.equals(statone)) && (error.equals(inMsg)))
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                statusServiceLine.setText("Error: Failed going to FOLA");
                            }
                        });
                    }
                    else if((beforecommand.equals(stattwo)) && (error.equals(inMsg)))
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                statusPandd.setText("Error: Failed going to Cleaning");
                            }
                        });
                    }
                    /*else
                    {
                        Connected.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textResponse.setText(inMsg);
                                //System.out.println("receive :"+inMsg);
                            }
                        });
                    }*/
                    txt = txt+inMsg;
                    //System.out.println(txt);
                    Connected.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textResponse.setText(txt);
                            textResponse.refreshDrawableState();
                        }
                    });
                /*//send output msg
                String outMsg = "adept" + System.getProperty("line.separator");
                out.write(outMsg);
                out.flush();
                Log.i("TcpClient", "sent: " + outMsg);*/
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //textResponse.setText(response);
            super.onPostExecute(result);
        }

    }
}
