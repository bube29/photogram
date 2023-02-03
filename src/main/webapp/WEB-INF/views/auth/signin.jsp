<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Photogram</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
</head>

<body>
    <div class="container">
        <main class="loginMain">
        <!--로그인섹션-->
            <section class="login">
               <!--로그인박스-->
                <article class="login__form__container">
                   <!--로그인 폼-->
                   <div class="login__form">
                        <h1><img src="/images/logo.jpg" alt=""></h1>
                        
                        <!--로그인 인풋-->
                        <form class="login__input"  action="/auth/signin" method="POST">
                        <!-- Post는 Insert할때 쓰는건데.. 지금 로그인은 데이터베이스에 username이랑 password가 있는지를 select해서 확인하는게 아닌가요? 
                        원래 select할때는 Get. 하지만 로그인 할때는 Post로.. 이유는?
                        username과 password는 귀중한 정보라 주소창에 노출 시키지 않기 위해선 데이터를 Body에 담아야하는데.. Body에 담는 방법은 Post 밖에 없다. 
                        Get으로 하면 주소창에 데이터가 남는다. 예외적으로 로그인만 Post로... -->
                            <input type="text" name="username" placeholder="유저네임" required="required" />
                            <input type="password" name="password" placeholder="비밀번호" required="required" />
                            <button>로그인</button>
                        </form>
                        
                        <!-- 위에 처럼 만들어 놓으면, 여기에 대한 Controller를 우리가 만들거라고 착각을 하는데
                        이 로그인 과정을 우리가 제어를 하는게 아니라 Spring Security에 위임을 해야한다. 
                        회원가입에 대한 Controller는 우리가 만들었으나, 로그인은 우리가 만들지 않는다. -->
                        
                        <!--로그인 인풋end-->
                        
                        <!-- 또는 -->
                        <div class="login__horizon">
                            <div class="br"></div>
                            <div class="or">또는</div>
                            <div class="br"></div>
                        </div>
                        <!-- 또는end -->
                        
                        <!-- Oauth 소셜로그인 -->
                        <div class="login__facebook">
                            <button>
                                <i class="fab fa-facebook-square"></i>
                                <span>Facebook으로 로그인</span>
                            </button>
                        </div>
                        <!-- Oauth 소셜로그인end -->
                    </div>
                    
                    <!--계정이 없으신가요?-->
                    <div class="login__register">
                        <span>계정이 없으신가요?</span>
                        <a href="/auth/signup">가입하기</a>
                    </div>
                    <!--계정이 없으신가요?end-->
                </article>
            </section>
        </main>
        
    </div>
</body>

</html>