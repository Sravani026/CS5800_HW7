import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Interface for the SongService
interface SongService {
    Song searchByID(Integer songID);
    List<Song> searchByTitle(String title);
    List<Song> searchByAlbum(String album);
}

// Class representing a Song
class Song {
    private Integer id;
    private String title;
    private String artist;
    private String album;
    private int duration;

    public Song(Integer id, String title, String artist, String album, int duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public Integer getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                '}';
    }
}

// Proxy class for caching song metadata
class SongProxy implements SongService {
    private SongService realSongServiceServer;
    private Map<Integer, Song> songIDCache;
    private Map<String, List<Song>> songTitleCache;
    private Map<String, List<Song>> songAlbumCache;

    public SongProxy(SongService realSongService) {
        this.realSongServiceServer = realSongService;
        this.songIDCache = new HashMap<>();
        this.songTitleCache = new HashMap<>();
        this.songAlbumCache = new HashMap<>();
    }

    @Override
    public Song searchByID(Integer songID) {
        if (songIDCache.containsKey(songID)) {
            System.out.println("Fetching song metadata for ID " + songID + " from cache.");
            return songIDCache.get(songID);
        }

        System.out.println("Fetching song metadata for ID " + songID + " from the real server.");
        Song song = realSongServiceServer.searchByID(songID);
        songIDCache.put(songID, song);
        return song;
    }

    @Override
    public List<Song> searchByTitle(String title) {
        if (songTitleCache.containsKey(title)) {
            System.out.println("Fetching songs with title '" + title + "' from cache.");
            return songTitleCache.get(title);
        }

        System.out.println("Fetching songs with title '" + title + "' from the real server.");
        List<Song> songs = realSongServiceServer.searchByTitle(title);
        songTitleCache.put(title, songs);
        return songs;
    }

    @Override
    public List<Song> searchByAlbum(String album) {
        if (songAlbumCache.containsKey(album)) {
            System.out.println("Fetching songs from album '" + album + "' from cache.");
            return songAlbumCache.get(album);
        }

        System.out.println("Fetching songs from album '" + album + "' from the real server.");
        List<Song> songs = realSongServiceServer.searchByAlbum(album);
        songAlbumCache.put(album, songs);
        return songs;
    }
}

// Real implementation of SongService fetching data from a server
class RealSongService implements SongService {
    private final List<Song> songs;

    public RealSongService() {
        songs = new ArrayList<>();
        songs.add(new Song(1, "Track 1", "Artist A", "Album X", 180));
        songs.add(new Song(2, "Track 2", "Artist B", "Album Y", 220));
        songs.add(new Song(3, "Track 3", "Artist C", "Album Z", 200));
        songs.add(new Song(4, "Track 4", "Artist A", "Album X", 240));
        songs.add(new Song(5, "Track 5", "Artist B", "Album Y", 190));
    }

    @Override
    public Song searchByID(Integer songID) {
        for (Song song : songs) {
            if (song.getID().equals(songID)) {
                return song;
            }
        }
        return null;
    }

    @Override
    public List<Song> searchByTitle(String title) {
        List<Song> foundSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                foundSongs.add(song);
            }
        }
        return foundSongs;
    }

    @Override
    public List<Song> searchByAlbum(String album) {
        List<Song> foundSongs = new ArrayList<>();
        for (Song song : songs) {
            if (song.getAlbum().equalsIgnoreCase(album)) {
                foundSongs.add(song);
            }
        }
        return foundSongs;
    }
}

// Main class to demonstrate usage of the proxy pattern
public class ProxyMain {
    public static void main(String[] args) {
        SongService songService = new SongProxy(new RealSongService());

        System.out.println("Searching for songs by ID:");
        System.out.println("1. " + songService.searchByID(1)); // Fetch from server and store in cache
        System.out.println("2. " + songService.searchByID(2)); // Fetch from server and store in cache
        System.out.println("3. " + songService.searchByID(3)); // Fetch from server and store in cache
        System.out.println("4. " + songService.searchByID(1)); // Fetch from cache

        System.out.println("\nSearching for songs by title:");
        System.out.println("1. " + songService.searchByTitle("Track 3")); // Fetch from server and store in cache
        System.out.println("2. " + songService.searchByTitle("Track 2")); // Fetch from server and store in cache
        System.out.println("3. " + songService.searchByTitle("Track 1")); // Fetch from server and store in cache
        System.out.println("4. " + songService.searchByTitle("Track 2")); // Fetch from cache

        System.out.println("\nSearching for songs by album:");
        System.out.println("1. " + songService.searchByAlbum("Album Z")); // Fetch from server and store in cache
        System.out.println("2. " + songService.searchByAlbum("Album Y")); // Fetch from server and store in cache
        System.out.println("3. " + songService.searchByAlbum("Album X")); // Fetch from server and store in cache
        System.out.println("4. " + songService.searchByAlbum("Album X")); // Fetch from cache
    }
}
