package it.pspgt.ble.testingble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import java.io.BufferedReader;
import java.util.List;


public class GattCallback extends BluetoothGattCallback {

    private List<BluetoothGattCharacteristic> caratteristiche;
    private BluetoothGatt gatt;
    private boolean ready=false;
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        Log.i("onConnectionStateChange", "Status: " + status);
        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                Log.i("gattCallback", "STATE_CONNECTED");
                gatt.discoverServices();
                this.gatt=gatt;
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
                Log.e("gattCallback", "STATE_DISCONNECTED");
                break;
            default:
                Log.e("gattCallback", "STATE_OTHER");
        }
    }
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        List<BluetoothGattService> services = gatt.getServices();
        caratteristiche=services.get(2).getCharacteristics();
        ready=true;
        Log.i("onServicesDiscovered", services.toString());
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        String prova= new String(characteristic.getValue());
        Log.i("onCharacteristicRead", prova);
    }

    public boolean isReady() {
        return ready;
    }
    public BluetoothGatt getGatt(){
        return gatt;
    }
    public List<BluetoothGattCharacteristic> getCaratteristiche() {
        return caratteristiche;
    }
}
