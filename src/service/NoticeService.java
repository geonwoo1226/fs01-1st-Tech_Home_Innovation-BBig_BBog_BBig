package service;

import java.util.List;

import dto.NoticeDTO;
import dto.UserDTO;

public interface NoticeService {
	List<NoticeDTO> getAllPosts();
	NoticeDTO getPostById(int id);
	int writePost(UserDTO user, NoticeDTO notice);
}
