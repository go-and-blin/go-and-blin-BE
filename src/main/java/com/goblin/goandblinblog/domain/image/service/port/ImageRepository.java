package com.goblin.goandblinblog.domain.image.service.port;

import com.goblin.goandblinblog.domain.image.entity.Image;

public interface ImageRepository {

    Image save(Image image);
}