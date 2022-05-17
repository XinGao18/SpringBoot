package entity;

public class Result {
    private Integer check;// Status code
    public Result(){};
    public Result(Integer check) {
        this.check = check;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }
}
