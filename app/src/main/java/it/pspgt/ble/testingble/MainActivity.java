package it.pspgt.ble.testingble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private Button b, b1, b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button) findViewById(R.id.button);
        b.setOnClickListener(this);
        b1 = (Button) findViewById(R.id.button2);
        b1.setOnClickListener(this);
        b2 = (Button) findViewById(R.id.button3);
        b2.setOnClickListener(this);
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
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled())
        scanner.stopScan(scannerCallback);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == b.getId()) {
            while (!scannerCallback.isStatus()) {

            }
            BluetoothDevice device = scannerCallback.getDevice();
            GattCallback gattCallback = new GattCallback();
            device.connectGatt(this, true, gattCallback);
            while (!gattCallback.isReady()) {

            }
            caratteristiche = gattCallback.getCaratteristiche();
            byte prova[] = new byte[32];
            prova = "Stringa da inviare\0".getBytes();

            caratteristiche.get(0).setValue(prova);
            gattCallback.getGatt().writeCharacteristic(caratteristiche.get(0));

            //   gattCallback.getGatt().disconnect();
        } else if (v.getId() == b1.getId()) {
            while (!scannerCallback.isStatus()) {

            }
            BluetoothDevice device = scannerCallback.getDevice();
            GattCallback gattCallback = new GattCallback();
            device.connectGatt(this, true, gattCallback);
            while (!gattCallback.isReady()) {

            }
            caratteristiche = gattCallback.getCaratteristiche();
            byte prova[] = new byte[1];
            prova[0] = (byte) '1';

            caratteristiche.get(1).setValue(prova);
            gattCallback.getGatt().writeCharacteristic(caratteristiche.get(1));

        } else {
            while (!scannerCallback.isStatus()) {

            }
            BluetoothDevice device = scannerCallback.getDevice();
            GattCallback gattCallback = new GattCallback();
            device.connectGatt(this, true, gattCallback);
            while (!gattCallback.isReady()) {

            }
            caratteristiche = gattCallback.getCaratteristiche();
            byte prova[] = new byte[1];
            prova[0] = (byte) '0';

            caratteristiche.get(1).setValue(prova);
            gattCallback.getGatt().writeCharacteristic(caratteristiche.get(1));
        }
    }

}
