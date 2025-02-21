package com.goblin.goandblinblog.global.util;

import com.goblin.goandblinblog.global.util.ulid.IdentifierGenerator;
import de.huxhorn.sulky.ulid.ULID;
import org.springframework.stereotype.Component;

@Component
public class ULIDGenerator implements IdentifierGenerator {

    private final ULID ulid;

    public ULIDGenerator() {
        ulid = new ULID();
    }

    @Override
    public String generate() {
        return ulid.nextULID();
    }
}
