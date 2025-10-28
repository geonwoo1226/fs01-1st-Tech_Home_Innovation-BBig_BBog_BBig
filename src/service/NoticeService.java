package service;

import java.util.List;

import dto.NoticeDTO;
import dto.UserDTO;

public interface NoticeService {
	List<NoticeDTO> getAllPosts();
	int writePost(UserDTO user, NoticeDTO notice);
	List<NoticeDTO> getPostById(String id);
	List<NoticeDTO> getAllPostsAdmin();
}
