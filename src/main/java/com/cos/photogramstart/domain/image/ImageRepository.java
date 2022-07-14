package com.cos.photogramstart.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer>{
 //얘는 인터페이스다
}
