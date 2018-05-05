package bdjobs.lict.linearlayoutwithweight.model;


public class Student {
    private String id;
    private String userName, Email, PhoneNo, PassWord;
    private Float CGpa;

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) { Email = email; }

    public void setPhoneNo(String phoneNo) { PhoneNo = phoneNo; }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public void setCGpa(Float CGpa) {
        this.CGpa = CGpa;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getPassWord() {
        return PassWord;
    }

    public Float getCGpa() {
        return CGpa;
    }

    @Override
    public String toString() {
        return "UserName='" + userName + '\'' +
                " Email='" + Email + '\'' +
                " PhoneNo='" + PhoneNo + '\'' +
                " CGpa=" + CGpa;
    }

    public String toStringForDialog() {
        return "UserName='" + userName + '\'' +
                ", Email='" + Email + '\'' +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", CGpa=" + CGpa ;
    }
}
