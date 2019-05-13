package Emulator.ReferenceTables;

public class OPTable_Item {
    String mnc, cls;
    int m_code, length;
    OPTable_Item(String mnc, String cls, int m_code, int length){
        this.mnc = mnc;
        this.cls = cls;
        this.m_code = m_code;
        this.length = length;
    }

    public String getMnc() { return mnc; }
    public String getCls() { return cls; }
    public int getM_code() { return m_code; }
    public int getLength() { return length; }
}
