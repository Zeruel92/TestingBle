package it.pspgt.ble.testingble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner scanner;
    private List<BluetoothGattCharacteristic> caratteristiche;
    private ScanLE scannerCallback;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button) findViewById(R.id.button);
        b.setOnClickListener(this);
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        ArrayList<ScanFilter> filterList=new ArrayList<ScanFilter>();
        filterList.add(new ScanFilter.Builder().setDeviceName("Nucleo BLE").build());
        scannerCallback=new ScanLE(this.getApplicationContext());
        ScanSettings settings=new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_POWER).build();
        scanner= bluetoothAdapter.getBluetoothLeScanner();
        scanner.startScan(filterList,settings, scannerCallback);
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        scanner.stopScan(scannerCallback);
    }

    @Override
    public void onClick(View v) {
        while(!scannerCallback.isStatus()){

        }
        BluetoothDevice device=scannerCallback.getDevice();
        GattCallback gattCallback=new GattCallback();
        device.connectGatt(this,true,gattCallback);
        while(!gattCallback.isReady()){

        }
        caratteristiche=gattCallback.getCaratteristiche();
        byte prova[]=new byte[32];
        prova="Stringa da inviare\0".getBytes();

        caratteristiche.get(0).setValue(prova);
        gattCallback.getGatt().writeCharacteristic(caratteristiche.get(0));

     //   gattCallback.getGatt().disconnect();
    }
}
