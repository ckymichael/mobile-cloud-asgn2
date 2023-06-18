package org.magnum.mobilecloud.video.service;

import lombok.extern.slf4j.Slf4j;
import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoService {

    @Autowired
    VideoRepository videoRepository;

    public Collection<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Optional<Video> getVideo(long id) {
        return videoRepository.findById(id);
    }

    public Collection<Video> getVideoByName(String name) {
        return videoRepository.findByName(name);
    }

    public Collection<Video> getVideoByDurationLessThan(long duration) {
        return getAllVideos().stream()
                .filter(video -> video.getDuration() < duration)
                .collect(Collectors.toList());
    }

    public void addVideo(Video v) {
        videoRepository.save(v);
    }

    public boolean likeVideo(long id, String user) {
        Optional<Video> video = getVideo(id);
        boolean[] valid = new boolean[] {false};
        video.ifPresent(v -> {
            Set<String> likedBy = v.getLikedBy();
            valid[0] = likedBy.add(user);
            v.setLikes(likedBy.size());
        });
        return valid[0];
    }

    public boolean unlikeVideo(long id, String user) {
        Optional<Video> video = getVideo(id);
        boolean[] valid = new boolean[] {false};
        video.ifPresent(v -> {
            Set<String> likedBy = v.getLikedBy();
            valid[0] = likedBy.remove(user);
            v.setLikes(likedBy.size());
        });
        return valid[0];
    }



}
