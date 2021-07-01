package com.google;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private List<Video> playing = new ArrayList<>();
  private boolean paused = false;
  private int count = 0;
  private List<VideoPlaylist> plist = new ArrayList<>();


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    var vidlist = this.videoLibrary.getVideos();

    vidlist = vidlist.stream().sorted((object1,object2) ->object1.getTitle().compareTo(object2.getTitle())).collect(Collectors.toList());
    for(Video video: vidlist)
      System.out.println(String.format("%s (%s) %s", video.getTitle(), video.getVideoId(), video.getTags().toString().replace("," , "")));
  }

  public void playVideo(String videoId) {
    paused = false;
    int decision = 0;
    var vidlist = this.videoLibrary.getVideos();
    for(Video video: vidlist)
      if(video.getVideoId().equals(videoId))
        decision=1;
      if(decision ==0){
        System.out.println("Cannot play video: Video does not exist");
      }
      else {
        playing.add(this.videoLibrary.getVideo(videoId));


        if (playing.size() == 1)
          System.out.println("Playing video: " + playing.get(0).getTitle());
        else  {
          System.out.println("Stopping video: " + playing.get(0).getTitle());
          System.out.println("Playing video: " + playing.get(1).getTitle());
          playing.remove(0);


        }
      }

  }

  public void stopVideo() {
    if (playing.isEmpty()) {
      System.out.println("Cannot stop video: No video is currently playing");
    } else if (playing.size() == 1) {
      System.out.println("Stopping video: " + playing.get(0).getTitle());
      playing.remove(0);
    } else {
      System.out.println("Stopping video :" + playing.get(1).getTitle());
      playing.remove(1);
      playing.remove(0);
    }
  }

  public void playRandomVideo() {
    var vidlist = this.videoLibrary.getVideos();
    Random rand = new Random();
    int upperbound = vidlist.size();
    int int_random = rand.nextInt(upperbound);


    playing.add(vidlist.get(int_random));


    if (playing.size() == 1)
      System.out.println("Playing video: " + playing.get(0).getTitle());
    else  {
      System.out.println("Stopping video: " + playing.get(0).getTitle());
      System.out.println("Playing video: " + playing.get(1).getTitle());
      playing.remove(0);


    }

  }

  public void pauseVideo() {
    if(playing.isEmpty())
      System.out.println("Cannot pause video: No video is currently playing");
    else if(paused){
      System.out.println("Video already paused: " +playing.get(playing.size()-1).getTitle());
    }
    else{
      paused = true;
      System.out.println("Pausing video: " +playing.get(playing.size()-1).getTitle());
    }
  }

  public void continueVideo() {
    if(playing.isEmpty())
      System.out.println("Cannot continue video: No video is currently playing");
    else if(!paused)
      System.out.println("Cannot continue video: Video is not paused");
    else{
      paused = false;
      System.out.println("Continuing video: " +playing.get(playing.size()-1).getTitle());
    }
  }

  public void showPlaying() {
    if(playing.isEmpty())
      System.out.println("No video is currently playing");
    else{
      if(paused)
        System.out.println("Currently playing: " +String.format("%s (%s) %s", playing.get(playing.size()-1).getTitle(), playing.get(playing.size()-1).getVideoId(), playing.get(playing.size()-1).getTags().toString().replace("," , "")) +" - PAUSED");
      else
        System.out.println("Currently playing: " +String.format("%s (%s) %s", playing.get(playing.size()-1).getTitle(), playing.get(playing.size()-1).getVideoId(), playing.get(playing.size()-1).getTags().toString().replace("," , "")));
    }
  }

  public void createPlaylist(String playlistName) {
    boolean exists = false;
    if (count == 0) {
      plist.add(new VideoPlaylist(playlistName));
      System.out.println("Succesfully created playlist: " + playlistName);
      count++;

    } else {

      for (VideoPlaylist vplist : plist)
        if (playlistName.equalsIgnoreCase(vplist.getName())) {
          System.out.println("Cannot create playlist: A playlist with the same name already exists");
          exists = true;
        }
      if (!exists) {
        plist.add(new VideoPlaylist(playlistName));
        System.out.println("Succesfully created playlist: " + playlistName);

      }


    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    int decision = 0;
    for(VideoPlaylist vplist: plist)
      if(!playlistName.equalsIgnoreCase(vplist.getName())) {
        System.out.println("Cannot add video to another_playlist: Playlist does not exist");
        decision = 1;
        return;
      }
    if(decision == 0){
      for(VideoPlaylist vplist: plist)
        for(Video vid: vplist.getvideoPlaylist()){
          if(vid.getVideoId().equals(videoId)) {
            System.out.println("Cannot add video to " + playlistName + ": Video already added");
            return;
          }

        }
    }
    var dlist = videoLibrary.getVideos();
    for(Video vid: dlist)
      if(vid.getVideoId().equals(videoId))
        decision = 2;
    if(decision == 0) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
      return;
    }

    for(VideoPlaylist vplist: plist)
        if(playlistName.equalsIgnoreCase(vplist.getName())) {
          vplist.putVideoPlaylist(videoLibrary.getVideo(videoId));
          System.out.println("Added video to " + playlistName + ":" +videoLibrary.getVideo(videoId).getTitle());
        }
  }

  public void showAllPlaylists() {
    if(plist.isEmpty())
      System.out.println("No playlists exist yet");
      else{
        System.out.println("Showing all playlists:");
        for(VideoPlaylist list: plist)
          System.out.println(list.getName());
    }
  }

  public void showPlaylist(String playlistName) {
    int decision = 0;
    List<Video> checklist = new ArrayList<>();
    for(VideoPlaylist list: plist)
      if(list.getName().equalsIgnoreCase(playlistName)) {
        checklist.addAll(list.getvideoPlaylist());
        decision = 1;
      }

    if(decision == 0){
      System.out.println("Cannot show playlist " +playlistName  +": Playlist does not exist");
      return;
    }

    System.out.println("Showimg playlist: " +playlistName);
    if(checklist.isEmpty())
      System.out.println("No videos here yet");
    else{
      for(Video vid: checklist)
        System.out.println(String.format("%s (%s) %s", vid.getTitle(), vid.getVideoId(), vid.getTags().toString().replace("," , "")));
    }

  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    int decision = 0;
    boolean valid = false;
    for(VideoPlaylist vplist: plist)
      if(playlistName.equalsIgnoreCase(vplist.getName()))
       decision = 1;

    if(decision ==0)
      return;
    var dlist = videoLibrary.getVideos();
    for(Video vid: dlist)
      if(vid.getVideoId().equals(videoId))
        valid = true;
        if(!valid) {
          System.out.println("Cannot remove video from " + playlistName + ": Video does not exist\n");
          return;
        }

      for(VideoPlaylist vplist: plist)
        for(Video vid: vplist.getvideoPlaylist())
          if(vid.getVideoId().equals(videoId)) {
            decision = 2;
            System.out.println("Removed video from " +playlistName +":" +videoLibrary.getVideo(videoId).getTitle());
            vplist.removeList(videoId);
          }

      if(decision == 1)
        System.out.println("Cannot remove video from " +playlistName +": Video is not in playlist");







  }

  public void clearPlaylist(String playlistName) {
    System.out.println("clearPlaylist needs implementation");
  }

  public void deletePlaylist(String playlistName) {
    System.out.println("deletePlaylist needs implementation");
  }

  public void searchVideos(String searchTerm) {
    System.out.println("searchVideos needs implementation");
  }

  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}