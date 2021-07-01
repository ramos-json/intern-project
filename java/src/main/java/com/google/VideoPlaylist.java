package com.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private ArrayList<Video> videoPlaylist;

    private String name;
    public VideoPlaylist(String names){
        this.name = names;
        videoPlaylist = new ArrayList<>();

    }
     ArrayList<Video> getvideoPlaylist(){
        return this.videoPlaylist;
    }
    String getName(){
        return this.name;
    }

    public void putVideoPlaylist(Video vid) {
        this.videoPlaylist.add(vid);
    }
    public void removeList(String vid){

        videoPlaylist.remove();
    }
}
