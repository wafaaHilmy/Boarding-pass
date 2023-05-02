package com.example.android.boardingpass;

/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.boardingpass.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    ActivityMainBinding flightBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);


        BoardingPassInfo boardingPassInfo=FakeDataUtils.generateFakeBoardingPassInfo();
        displayBoardingPassInfo(boardingPassInfo);
    }

    private void displayBoardingPassInfo(BoardingPassInfo boardingPassInfo) {

        mBinding.flightInfo.passengerName.setText(boardingPassInfo.passengerName);
        mBinding.flightInfo.originCode.setText(boardingPassInfo.originCode);
        mBinding.flightInfo.flightCode.setText(boardingPassInfo.flightCode);
        mBinding.flightInfo.destinationCode.setText(boardingPassInfo.destCode);

        // Use a SimpleDateFormat formatter to set the formatted value in time text views
        SimpleDateFormat formatter=new SimpleDateFormat("hh: mm a", Locale.getDefault());

        String boardingTime = formatter.format(boardingPassInfo.boardingTime);
        String departureTime=formatter.format(boardingPassInfo.departureTime);
        String arrivalTime=formatter.format(boardingPassInfo.arrivalTime);

        mBinding.flightInfo.boardingTime.setText(boardingTime);
        mBinding.departureTime.setText(departureTime);
        mBinding.arrivalTime.setText(arrivalTime);


//get time until boarding in hours and minutes
        long totalMinutesUntilBoarding = boardingPassInfo.getMinutesUntilBoarding();
        long hoursUntilBoarding = TimeUnit.MINUTES.toHours(totalMinutesUntilBoarding);
        long minutesLessHoursUntilBoarding =
                totalMinutesUntilBoarding - TimeUnit.HOURS.toMinutes(hoursUntilBoarding);

//string format to display it
        String hoursAndMinutesUntilBoarding = getString(R.string.countDownFormat,
                hoursUntilBoarding,
                minutesLessHoursUntilBoarding);

        mBinding.flightInfo.boardingInTime.setText(hoursAndMinutesUntilBoarding);

//from boarding table bind data
        mBinding.boardingTable.textViewTerminal.setText(boardingPassInfo.departureTerminal);
       mBinding.boardingTable.textViewGate.setText(boardingPassInfo.departureGate);
       mBinding.boardingTable.textViewSeat.setText(boardingPassInfo.seatNumber);

    }

}
