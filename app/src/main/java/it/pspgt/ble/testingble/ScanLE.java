package it.pspgt.ble.testingble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class ScanLE extends ScanCallback {
    private BluetoothDevice device;
    private boolean status=false;
    private Context c;
    private List<BluetoothGattCharacteristic> caratteristiche;
    public ScanLE(Context c){
        this.c=c;
    }
    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        device= result.getDevice();
        Log.i("BLE", device.getName());

      //  GattCallback gattCallback=new GattCallback();
      //  device.connectGatt(c, true, gattCallback);
      //  while(!gattCallback.isReady()) {
      //      status = false;
      //  }
      //  caratteristiche=gattCallback.getCaratteristiche();
        status=true;
    }
    public BluetoothDevice getDevice(){
        return device;
    }
    public boolean isStatus(){
        return status;
    }

    public List<BluetoothGattCharacteristic> getCaratteristiche() {
        return caratteristiche;
    }
}
