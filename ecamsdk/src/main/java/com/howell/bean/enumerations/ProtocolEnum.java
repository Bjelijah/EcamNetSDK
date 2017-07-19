package com.howell.bean.enumerations;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ProtocolEnum {

    public enum PtzDirection{
        STOP("Stop"),
        UP("Up"),
        DOWN("Down"),
        LEFT("Left"),
        RIGHT("Right");
        private final String s;
        PtzDirection(String str){
            this.s = str;
        }
        public String getVal(){
            return s;
        }
    }

    public enum PtzLens{
        STOP("Stop"),
        IRIS_OPEN("IrisOpen"),
        IRIS_CLOSE("IrisClose"),
        ZOOM_TELE("ZoomTele"),
        ZOOM_WIDE("ZoomWide"),
        FOCUS_FAR("FocusFar"),
        FOCUS_Near("FocusNear");

        private  String s;
        PtzLens(String s){
            this.s = s;
        }
        public String getVal(){
            return this.s;
        }
    }

    public enum PtzPreset{
        CLEAR("Clear"),
        SET("Set"),
        GOTO("Goto");

        private  String s;
        PtzPreset(String s){
            this.s = s;
        }
        public String getVal() {
            return this.s;
        }
    }




    public enum DeviceClassification{
        NONE("None"),
        IP_CAMERA("IPCamera"),
        DVS("DVS"),
        NVR("NVR"),
        DVR("DVR"),
        DIGITAL_MATRIX("DigitalMatrix"),
        HD_Decoder("HDDecoder"),
        ANALOG_MATRIX("AnalogMatrix"),
        VAS("VAS"),
        AAM("AAM"),
        NAM("NAM"),
        VPS("VPS"),
        INTEGRATED_MATRIX("IntegratedMatrix"),
        MATRIX_CONTROL_UNIT("MatrixControlUnit"),
        STREAMING_MEDIA_SERVER("StreamingMediaServer"),
        DECODING_UNIT("DecodingUnit "),
        ENCODING_UNIT("EncodingUnit "),
        NVS("NVS"),
        DATA_SERVER("DataServer"),
        ACQUISITION_SERVER("AcquisitionServer"),
        SYSTEM_GATEWAY("SystemGateway"),
        CAMERA("Camera"),
        ULTRASONIC_PROBE("UltrasonicProbe"),
        RFID_ANTENNA("RFIDAntenna"),
        ATM_ANALYSER("ATMAnalyser");

        private String s;
        DeviceClassification(String s){
            this.s=s;
        }
        public String getVal(){
            return this.s;
        }
    }

    public enum IOState{
        INACTIVE("Inactive"),
        ACTIVE("Active");
        private String s;
        IOState(String s){
            this.s = s;
        }
        public String getVal(){
            return this.s;
        }
    }

    public enum NoticeClassification{
        NONE("None"),
        EMERGENCY("Emergency"),
        WARNING("Warning"),
        INFO("Info");
        private String s;
        NoticeClassification(String s){
            this.s=s;
        }
        public String getVal(){
            return this.s;
        }
    }

    public enum NoticeStatusType{
        UNREAD("Unread"),
        READ("Read");
        private String s;
        NoticeStatusType(String s){
            this.s = s;
        }
        public String getVal(){
            return this.s;
        }
    }

    public enum NoticeType{
        NONE("None"),
        ALERT_PROCESS("AleertProcess");
        private String s;
        NoticeType(String s){
            this.s = s;
        }
        public String getVal(){
            return this.s;
        }
    }



}
