package com.goblin.goandblinblog.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String imageUrl;

    @Column(unique = true)
    private String refreshToken;

    @Builder
    private Member(String nickName, String password, String imageUrl, String refreshToken) {
        this.nickName = nickName;
        this.password = password;
        this.imageUrl = imageUrl;
        this.refreshToken = refreshToken;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updateProfileImage(String profileImageURL) {
        this.imageUrl = profileImageURL;
    }

}