package com.pcy.bean;

public class FileURL {

    private String groupName;
    private String remoteFileName;
    private String realUrl;
    private String visitUrl;

    public FileURL() {
    }

    public FileURL(String groupName, String remoteFileName, String realUrl, String visitUrl) {
        this.groupName = groupName;
        this.remoteFileName = remoteFileName;
        this.realUrl = realUrl;
        this.visitUrl = visitUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    @Override
    public String toString() {
        return "FileURL{" +
                "groupName='" + groupName + '\'' +
                ", remoteFileName='" + remoteFileName + '\'' +
                ", realUrl='" + realUrl + '\'' +
                ", visitUrl='" + visitUrl + '\'' +
                '}';
    }
}
