public class WebPage {
    private String title;
    private int pageid;
    public WebPage(String title, int pageid){
        this.title = title;
        this.pageid = pageid;
    }
    public String getTitle(){
        return title;
    }
    public int getPageId(){
        return pageid;
    }
}

