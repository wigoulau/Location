package com.example.modeminfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ModemInfoActivity extends AppCompatActivity {

    private static final String TAG = "ModemInfo";
    private final int mRequestCode = 100;//权限请求码

    private int mCid;   // Cell identity
    private int mLac;   // location area code
    private String mMcc;    // mobile country code
    private String mMnc;    // mobile network code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modem_info);
        checkRequiredPermissions();
        try {
            getModemInfo();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void checkRequiredPermissions() {
        String[] permissions = new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        List<String> mPermissionList = new ArrayList<>();

        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        // 申请权限
        if (mPermissionList.size() > 0) {
            Log.d(TAG, "requeset permission");
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            // 权限通过
            Log.d(TAG, "requeset permission pass ");
        }
    }

    private void getModemInfo() {
        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        List<CellInfo> cellInfos = telManager.getAllCellInfo();
        for (CellInfo cellInfo : cellInfos) {
            if (cellInfo instanceof CellInfoGsm) {
                mCid = ((CellInfoGsm)cellInfo).getCellIdentity().getCid();
                mLac = ((CellInfoGsm)cellInfo).getCellIdentity().getLac();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    mMcc = ((CellInfoGsm)cellInfo).getCellIdentity().getMccString();
                    mMnc = ((CellInfoGsm)cellInfo).getCellIdentity().getMncString();
                } else {
                    int mcc = ((CellInfoGsm)cellInfo).getCellIdentity().getMcc();
                    int mnc = ((CellInfoGsm)cellInfo).getCellIdentity().getMnc();
                    if (mcc < 10)
                        mMcc = "0" + mcc;
                    else
                        mMcc = "" + mcc;
                    if (mnc < 10)
                        mMnc = "0" + mnc;
                    else
                        mMnc = "" + mnc;
                }
            } else if (cellInfo instanceof CellInfoLte) {
                mCid = ((CellInfoLte)cellInfo).getCellIdentity().getCi();
                mLac = ((CellInfoLte)cellInfo).getCellIdentity().getTac();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    mMcc = ((CellInfoLte)cellInfo).getCellIdentity().getMccString();
                    mMnc = ((CellInfoLte)cellInfo).getCellIdentity().getMncString();
                } else {
                    int mcc = ((CellInfoLte)cellInfo).getCellIdentity().getMcc();
                    int mnc = ((CellInfoLte)cellInfo).getCellIdentity().getMnc();
                    if (mcc < 10)
                        mMcc = "0" + mcc;
                    else
                        mMcc = "" + mcc;
                    if (mnc < 10)
                        mMnc = "0" + mnc;
                    else
                        mMnc = "" + mnc;
                }
            } else if (cellInfo instanceof CellInfoWcdma) {
                mCid = ((CellInfoWcdma)cellInfo).getCellIdentity().getCid();
                mLac = ((CellInfoWcdma)cellInfo).getCellIdentity().getLac();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    mMcc = ((CellInfoWcdma)cellInfo).getCellIdentity().getMccString();
                    mMnc = ((CellInfoWcdma)cellInfo).getCellIdentity().getMncString();
                } else {
                    int mcc = ((CellInfoWcdma)cellInfo).getCellIdentity().getMcc();
                    int mnc = ((CellInfoWcdma)cellInfo).getCellIdentity().getMnc();
                    if (mcc < 10)
                        mMcc = "0" + mcc;
                    else
                        mMcc = "" + mcc;
                    if (mnc < 10)
                        mMnc = "0" + mnc;
                    else
                        mMnc = "" + mnc;
                }
            } else if (cellInfo instanceof CellInfoCdma) {
                mCid = ((CellInfoCdma)cellInfo).getCellIdentity().getBasestationId();
                mLac = ((CellInfoCdma)cellInfo).getCellIdentity().getNetworkId();
            }

            Log.i(TAG, "Modem Info: cid="+mCid+", lac="+mLac+", mcc="+mMcc+", mnc="+mMnc);
        }
    }

}
