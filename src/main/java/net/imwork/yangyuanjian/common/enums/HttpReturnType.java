package net.imwork.yangyuanjian.common.enums;

/**
 * Created by thunderobot on 2017/11/18.
 */
public enum HttpReturnType {
    Xls(".xls","返回excel文件","application/x-xls"),
    Html(".html","返回html文件","text/xml"),
    Htm(".htm","返回htm文件","text/html"),
    Css(".css","返回css文件","text/css");
    String fileTypeName;
    String fileDescription;
    String httpContentType;

    HttpReturnType(String fileTypeName, String fileDescription, String httpContentType) {
        this.fileTypeName = fileTypeName;
        this.fileDescription = fileDescription;
        this.httpContentType = httpContentType;
    }

    @Override
    public String toString() {
        return "HttpReturnType{" +
                "fileTypeName='" + fileTypeName + '\'' +
                ", fileDescription='" + fileDescription + '\'' +
                ", httpContentType='" + httpContentType + '\'' +
                '}';
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getHttpContentType() {
        return httpContentType;
    }

    public void setHttpContentType(String httpContentType) {
        this.httpContentType = httpContentType;
    }
}
