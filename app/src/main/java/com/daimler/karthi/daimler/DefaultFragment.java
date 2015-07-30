package com.daimler.karthi.daimler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefaultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String baseURL;

    TextView minpre = null,maxpre = null,minpay = null,maxpay = null;
    SeekBar sminpre,smaxpre,sminpay,smaxpay;
    int thres=5000,max=50000;
    String dateString,timeString;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultFragment newInstance(String param1, String param2) {
        DefaultFragment fragment = new DefaultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DefaultFragment() {
        super();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_default, container, false);
        minpre=(TextView)view.findViewById(R.id.premiummin);
        maxpre=(TextView)view.findViewById(R.id.premiummax);
        minpay=(TextView)view.findViewById(R.id.initialpaymentmin);
        maxpay=(TextView)view.findViewById(R.id.initialpaymentmax);
        baseURL=getString(R.string.baseURL);

        ((SeekBar)view.findViewById(R.id.premiumseekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                minpre.setText((thres + (max * progress / 100)) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar)view.findViewById(R.id.initialpayseekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                maxpay.setText((thres + (max * progress / 10)) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar)view.findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                maxpre.setText((thres + (max * progress / 100)) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar)view.findViewById(R.id.seekBar2)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                minpay.setText((thres + (max * progress / 10)) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((Button)view.findViewById(R.id.schedule)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule();
            }
        });
        return view;
    }

    private void schedule() {

        TimePickerFragment timeFrag=new TimePickerFragment();
        timeFrag.setListener(new MyClickListener() {
            @Override
            public void onClick(String data) {
                timeString=data;
                sendData();

            }
        });
        timeFrag.show(getActivity().getSupportFragmentManager(), "TimePicker");

        DatePickerFragment dateFrag=new DatePickerFragment();
        dateFrag.setListener(new MyClickListener() {
            @Override
            public void onClick(String data) {
                dateString = data;

            }
        });
        dateFrag.show(getActivity().getSupportFragmentManager(), "DatePicker");



    }

    private void sendData() {

        String url=baseURL+"scheduleAppointment.php";
        ArrayList param=new ArrayList();
        param.add(new BasicNameValuePair("userid",getActivity().getSharedPreferences("daimler", Context.MODE_PRIVATE).getString("userid","")));
        param.add(new BasicNameValuePair("minpre",minpre.getText().toString()));
        param.add(new BasicNameValuePair("maxpre",maxpre.getText().toString()));
        param.add(new BasicNameValuePair("minpay", minpay.getText().toString()));
        param.add(new BasicNameValuePair("maxpay", maxpay.getText().toString()));
        param.add(new BasicNameValuePair("date", dateString));
        param.add(new BasicNameValuePair("time", timeString));


        final ProgressDialog loading=new ProgressDialog(getActivity());
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Submitting...");
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.show();

        new AsyncPost(url, param, new AsyncListener() {
            @Override
            public void onResponse(String response) {
                loading.cancel();
                if(response==null){
                    myToast("Try Again");
                    return;
                }
                if(response.equals("success")){
                    myToast("Successfully Schedule");
                    return;
                }else{
                    myToast("Try Again");
                    return;
                }
            }
        });
    }

    private void myToast(String s) {
        Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
