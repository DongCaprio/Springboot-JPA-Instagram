package com.cos.photogramstart.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{
 //얘는 인터페이스다
	
	@Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromuserId = :principalId) ORDER BY id DESC", nativeQuery = true)
	Page<Image> mStory(int principalId, Pageable pageable);
	
	@Query(value = "SELECT tt.id, tt.caption, tt.createDate, tt.postImageUrl, tt.userId from\r\n"
			+ "(SELECT i.* ,COUNT(imageId) AS c\r\n"
			+ "from image i \r\n"
			+ "JOIN likes l\r\n"
			+ "ON i.id = l.imageId\r\n"
			+ "GROUP BY imageId ORDER BY c desc) tt",nativeQuery = true)
	List<Image> mPopular();
}
