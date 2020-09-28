package com.sdaemon.oakstudiotv.dto;

import java.util.List;

/**
 * Created by linux122 on 19/3/16.
 */
public class ResultDTO<OBJ1, OBJ2, OBJ3, OBJ4, L1, L2, L3, L4, L5, L6> {
    String success = "";
    String latitude = "",longitude = "",address = "";
    private OBJ1 obj1;
    private OBJ2 obj2;
    private OBJ3 obj3;
    private OBJ4 obj4;
    private List<L1> list1;
    private List<L2> list2;
    private List<L3> list3;
    private List<L1> list4;
    private List<L2> list5;
    private List<L3> list6;

    public OBJ1 getObj1() {
        return obj1;
    }

    public void setObj1(OBJ1 obj1) {
        this.obj1 = obj1;
    }

    public OBJ2 getObj2() {
        return obj2;
    }

    public void setObj2(OBJ2 obj2) {
        this.obj2 = obj2;
    }

    public OBJ3 getObj3() {
        return obj3;
    }

    public void setObj3(OBJ3 obj3) {
        this.obj3 = obj3;
    }

    public OBJ4 getObj4() {
        return obj4;
    }

    public void setObj4(OBJ4 obj4) {
        this.obj4 = obj4;
    }

    public List<L1> getList1() {
        return list1;
    }

    public void setList1(List<L1> list1) {
        this.list1 = list1;
    }

    public List<L2> getList2() {
        return list2;
    }

    public void setList2(List<L2> list2) {
        this.list2 = list2;
    }

    public List<L3> getList3() {
        return list3;
    }

    public void setList3(List<L3> list3) {
        this.list3 = list3;
    }

    public List<L1> getList4() {
        return list4;
    }

    public void setList4(List<L1> list4) {
        this.list4 = list4;
    }

    public List<L2> getList5() {
        return list5;
    }

    public void setList5(List<L2> list5) {
        this.list5 = list5;
    }

    public List<L3> getList6() {
        return list6;
    }

    public void setList6(List<L3> list6) {
        this.list6 = list6;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
