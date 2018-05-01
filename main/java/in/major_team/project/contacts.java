package in.major_team.project;

/**
 * Created by AmNayak on 16-01-2018.
 */

public class contacts {
    private String tool,status,data,alive;

    public contacts(String t,String s,String d,String a)
    {
        this.setTool(t);
        this.setStatus(s);
        this.setData(d);
        this.setAlive(a);
    }


    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public void setAlive(String tool) {
        this.alive = tool;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
