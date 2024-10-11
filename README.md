# Spring 뉴스피드 프로젝트
## 팀: 14일의 금요일(14조)
## 팀원: 정은교, 이길환, 홍기평, 양혜민, 장기현
## 👩‍💻 역할 분담
- 홍기평 : 
## 프로젝트 소개
SNS 서버 만들기
- 내용 : 유저 프로필 관리, 뉴스피드 게시글 관리, 댓글, 좋아요

#### 👩‍💻 역할 분담
- 정은교 : 회원가입, 로그인, 게시물

- 이길환 : 유저

- 홍기평 : 친구
    
- 양혜민 : 게시물 
    
- 장기현 : 댓글

**🐱‍🏍 Ground Rules**
- 캠 항상 켜놓기
- 자리 비울 때 춤추기 or 슬랙에 말하기
- 과제 중 모르는 것 있으면 바로바로 물어보기
- 새롭게 배운 정보 슬랙에 공유하기

**🎯 Goals**
1. 도전 기능 4개 이상 하기
2. 팀 컨벤션 만들어서 지키기

**📌 회의**
- 아침 회의: 10:00 ~ 10:30
- 저녁 회의: 20:00 ~ 20:30

## 프로젝트 소개
배달 어플리케이션 아웃소싱 프로젝트

### 🖼 와이어프레임
https://www.figma.com/board/OmDe3vvE1aPm5bFYX58aci/Newsfeed_project_14thFriday?node-id=0-1

### 📑 API 명세서
https://teamsparta.notion.site/17f219e3760745318b5562133339b867?v=d104324e3729418b8d8a972deb304f12&pvs=4

### 🛠 ERD 다이어그램
![image (2)](https://github.com/user-attachments/assets/c4dd1c94-90a4-4026-9734-c9b3171c54ed)

### 🦺 트러블슈팅
<img width="1362" alt="스크린샷 2024-10-11 14 52 02" src="https://github.com/user-attachments/assets/370b573a-a067-4d27-a27e-df73cbd07a7d">


### 🥇 필수 요구사항
### **1. 프로필 관리**

- **프로필 조회 기능**
    - 다른 사용자의 프로필 조회 시, 민감한 정보는 표시되지 않습니다.
- **프로필 수정 기능**
    
    로그인한 사용자는 본인의 사용자 정보를 수정할 수 있습니다.
    
    - 비밀번호 수정 조건
        - 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
        - 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.
    - **⚠️ 예외처리**
        - 비밀번호 수정 시, 본인 확인을 위해 입력한 현재 비밀번호가 일치하지 않은 경우
        - 비밀번호 형식이 올바르지 않은 경우
        - 현재 비밀번호와 동일한 비밀번호로 수정하는 경우

### **2.  뉴스피드 게시물 관리**

- **게시물 작성, 조회, 수정, 삭제 기능**
    - 조건
        - 게시물 수정, 삭제는 작성자 본인만 처리할 수 있습니다.
    - **⚠️ 예외처리**
        - 작성자가 아닌 다른 사용자가 게시물 수정, 삭제를 시도하는 경우
- **뉴스피드 조회 기능**
    - 기본 정렬은 생성일자 ****기준으로 내림차순 정렬합니다.
    - 10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.
    - **⚠️ 예외처리**
        - 다른 사람의 뉴스피드는 볼 수 없습니다.

### **3. 사용자 인증**

- **회원가입 기능**
    - 사용자 아이디
        - 사용자 아이디는 이메일 형식이어야 합니다.
    - 비밀번호
        - `Bcrypt`로 인코딩합니다.
            - 암호화를 위한 `PasswordEncoder`를 직접 만들어 사용합니다.
                - 참고 코드
                    1. `build.gradle` 에 아래의 의존성을 추가해주세요.
                        
                        ```java
                        implementation 'at.favre.lib:bcrypt:0.10.2'
                        ```
                        
                    2. `config` 패키지가 없다면 추가하고, 아래의 클래스를 추가해주세요.
                        
                        ```java
                        import at.favre.lib.crypto.bcrypt.BCrypt;
                        import org.springframework.stereotype.Component;
                        
                        @Component
                        public class PasswordEncoder {
                        
                            public String encode(String rawPassword) {
                                return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
                            }
                        
                            public boolean matches(String rawPassword, String encodedPassword) {
                                BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
                                return result.verified;
                            }
                        }
                        ```
                        
        - 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.
        - 비밀번호는 최소 8글자 이상이어야 합니다.
    - **⚠️ 예외처리**
        - 중복된 `사용자 아이디`로 가입하는 경우
        - `사용자 아이디` 이메일과 비밀번호 형식이 올바르지 않은 경우
- **회원탈퇴 기능**
    
    회원 탈퇴 방식을 어떻게 처리할지 고민해보세요.
    
    - 조건
        - 탈퇴 처리 시 `비밀번호`를 확인한 후 일치할 때 탈퇴 처리합니다.
        - 탈퇴한 사용자의 아이디는 재사용할 수 없고, 복구할 수 없습니다.
    - **⚠️ 예외처리**
        - `사용자 아이디`와 `비밀번호`가 일치하지 않는 경우
        - 이미 탈퇴한 `사용자 아이디`인 경우

### **4. 친구 관리**

- 특정 사용자를 친추 추가/삭제 할 수 있습니다.
- 친구 기능이 구현되었다면, 뉴스피드에 친구의 최신 게시물들을 최신순으로 볼 수 있습니다.
    - **⚠️ 주의사항**
        - 친구는 상대방의 수락 기능이 필요합니다. 만약 어렵다면, 관심 유저를 팔로우하는 기능으로 개발하셔도 좋습니다.

### 🏃‍♀️ 도전 요구사항
### **ex1) 업그레이드 뉴스피드**

- 정렬 기능
    - 수정일자 기준 최신순
    - 좋아요 많은 순
- **기간별 검색 기능**
    - 예) 2024.05.01 ~ 2024.05.27 동안 작성된 뉴스피드 게시물 검색

### **ex2) 댓글**

- 댓글 작성, 조회, 수정, 삭제
    - 사용자는 게시물에 댓글을 작성할 수 있고, 본인의 댓글은 **수정 및 삭제**를 할 수 있습니다.
    - **내용**만 수정이 가능합니다.
    - 댓글 수정, 삭제는 댓글의 작성자 혹은 게시글의 작성자만 가능합니다.
- 댓글 수정, 삭제는 댓글의 작성자 혹은 게시글의 작성자만 가능합니다.

### **ex3) 좋아요**

- **게시물 및 댓글 좋아요 / 좋아요 취소 기능**
    - 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있습니다.
    - 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.
    - 같은 게시물에는 사용자당 한 번만 좋아요가 가능합니다.
