import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Song class
class SongTest {
    @Test
    void testSongConstructor() {
        Song song = new Song(1, "Title", "Artist", "Album", 180);
        assertNotNull(song);
    }
}

// Unit tests for RealSongService class
class RealSongServiceTest {
    @Test
    void testSearchByID() {
        RealSongService realSongService = new RealSongService();
        Song song = realSongService.searchByID(1);
        assertNotNull(song);
    }

    @Test
    void testSearchByTitle() {
        RealSongService realSongService = new RealSongService();
        List<Song> songs = realSongService.searchByTitle("Track 2");
        assertNotNull(songs);
    }

    @Test
    void testSearchByAlbum() {
        RealSongService realSongService = new RealSongService();
        List<Song> songs = realSongService.searchByAlbum("Album X");
        assertNotNull(songs);
    }
}

// Unit tests for SongProxy class
class SongProxyTest {
    @Test
    void testSearchByID() {
        RealSongService realSongService = new RealSongService();
        SongProxy songProxy = new SongProxy(realSongService);
        Song song = songProxy.searchByID(1);
        assertNotNull(song);
    }

    @Test
    void testSearchByTitle() {
        RealSongService realSongService = new RealSongService();
        SongProxy songProxy = new SongProxy(realSongService);
        List<Song> songs = songProxy.searchByTitle("Track 2");
        assertNotNull(songs);
    }

    @Test
    void testSearchByAlbum() {
        RealSongService realSongService = new RealSongService();
        SongProxy songProxy = new SongProxy(realSongService);
        List<Song> songs = songProxy.searchByAlbum("Album X");
        assertNotNull(songs);
    }
}
