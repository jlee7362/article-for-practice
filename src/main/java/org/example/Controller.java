package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private Scanner sc;
    private MemberService memberService;
    private ArticleService articleService;
    private Member loginedMember;
    private int lastMemberId = 0;
    private int lastArticleId = 0;

    public Controller() {
        this.sc = new Scanner(System.in);
        List<Member> memberList = new ArrayList<>();
        List<Article> articleList = new ArrayList<>();
        this.memberService = new MemberService(memberList);
        this.articleService = new ArticleService((articleList));
        this.loginedMember = null;
    }

    public void run() {
        System.out.println("===프로그램 시작===");
        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();
            if (cmd.equals("member join")) {
                join();
            } else if (cmd.equals("member login")) {
                login();
            } else if (cmd.equals("member logout")) {
                logout();
            } else if (cmd.equals("article write")) {
                writeArticle();
            } else if (cmd.equals("article list")) {
                listArticle();
            } else if (cmd.startsWith("article detail ")) {
                detailArticle(cmd);
            } else if (cmd.startsWith("article modify ")) {
                modifyArticle(cmd);
            } else if (cmd.startsWith("article delete ")) {
                deleteArticle(cmd);
            } else if (cmd.equals("exit")) {
                System.out.println("===프로그램 종료===");
                break;
            } else {
                System.out.println("제대로 입력해주세요.");
            }
        }

    }

    private void join() {
        System.out.print("로그인 할 때 쓸 아이디: ");
        String loginId = sc.nextLine();

        if (!memberService.isJoinable(loginId)) {
            System.out.println("이미 사용중인 아이디입니다.");
            return;
        }
        System.out.print("로그인 비밀번호: ");
        String loginPw = sc.nextLine();
        System.out.print("이름 또는 닉네임: ");
        String name = sc.nextLine();

        memberService.join(++lastMemberId, loginId, loginPw, name);
        System.out.println("회원가입이 완료되었습니다.");
    }

    private void login() {
        if (loginedMember != null) {
            System.out.println("이미 로그인 되어있습니다.");
            return;
        }
        System.out.print("아이디: ");
        String loginId = sc.nextLine();
        System.out.print("비밀번호: ");
        String loginPw = sc.nextLine();
        Member member = memberService.getMemberByLoginIdAndPw(loginId, loginPw);
        if (member == null) {
            System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
        } else {
            loginedMember = member;
            System.out.printf("%s님 환영합니다.\n", member.getName());
        }
    }

    private void logout() {
        if (loginedMember == null) {
            System.out.println("현재 로그인 상태가 아닙니다.");
        } else {
            System.out.printf("%s님 로그아웃 되었습니다\n", loginedMember.getName());
            loginedMember = null;
        }
    }

    private void writeArticle() {
        if (loginedMember == null) {
            System.out.println("로그인 후 이용해 주세요.");
            return;
        }
        System.out.print("제목: ");
        String title = sc.nextLine().trim();
        System.out.print("내용: ");
        String body = sc.nextLine().trim();

        articleService.write(++lastArticleId, title, body, loginedMember.getId());
        System.out.printf("%d번 글이 작성되었습니다.\n", lastArticleId);

    }

    private void listArticle() {
        List<Article> articles = articleService.getArticles();
        if (articles.isEmpty()) {
            System.out.println("작성된 게시물이 없습니다.");
            return;
        }
        System.out.println("번호  /  제목  /  내용");
        System.out.println("=".repeat(30));

        for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("%d    %s    %s\n", article.getId(), article.getTitle(), article.getBody());
        }
    }

    private void detailArticle(String cmd) {
        String[] cmdBits = cmd.split(" ");
        if (cmdBits.length != 3) {
            System.out.println("잘못된 형식입니다. 'article detail [게시글 번호]'로 입력해주세요.");
            return;
        }
        try {
            int targetId = Integer.parseInt(cmdBits[2]);
            Article article = articleService.getArticleById(targetId);
            if (article == null) {
                System.out.println("해당 게시물이 존재하지 않습니다.");
            } else {
                System.out.println("번호: " + article.getId());
                System.out.println("날짜: " + article.getRegDate());
                System.out.println("제목: " + article.getTitle());
                System.out.println("내용: " + article.getBody());
            }
        } catch (NumberFormatException e) {
            System.out.println("게시글 번호는 숫자로 입력해주세요.");

        }
    }

    private void modifyArticle(String cmd) {
        if (loginedMember == null) {
            System.out.println("로그인 후 이용해주세요.");
            return;
        }
        String[] cmdBits = cmd.split(" ");
        if (cmdBits.length != 3) {
            System.out.println("잘못된 형식입니다.'article modify [게시글 번호]'로 입력해주세요.");
            return;
        }try{
            int targetId = Integer.parseInt(cmdBits[2]);
            Article article = articleService.getArticleById(targetId);
            if(article == null){
                System.out.println("해당 게시물은 존재하지 않습니다.");
            }else if (article.getAuthorId() != loginedMember.getId()){
                System.out.println("권한이 없습니다.");
            }else{
                System.out.println("기존 제목: "+ article.getTitle());
                System.out.print("새로운 제목: ");
                String newTitle = sc.nextLine();
                System.out.println("기존 내용: "+ article.getBody());
                System.out.print("새로운 내용: ");
                String newBody = sc.nextLine();
                articleService.modify(article,newTitle,newBody);
                System.out.println("게시글이 수정되었습니다.");
            }
        }catch(NumberFormatException e){
            System.out.println("게시물 번호는 숫자로 입력해주세요.");
        }

    }
    private void deleteArticle(String cmd){
        if(loginedMember == null){
            System.out.println("로그인 후 이용해주세요.");
            return;
        }
        String[] cmdBits = cmd.split(" ");
        if(cmdBits.length != 3){
            System.out.println("잘못된 형식입니다.'article delete [게시글 번호]'로 입력해주세요.");
            return;
        }
        try{
            int targetId = Integer.parseInt(cmdBits[2]);
            Article article = articleService.getArticleById(targetId);

            if(article == null){
                System.out.println("게시글이 없습니다.");
            }else if(article.getAuthorId() != loginedMember.getId()){
                System.out.println("권한이 없습니다.");
            }else{
                System.out.println("정말 삭제하시겠습니까? y/n");
                String confirm = sc.nextLine();
                if(confirm.equals("y")){
                    articleService.delete(article);
                    System.out.println("해당 게시물이 삭제되었습니다.");
                }
                else if(confirm.equals("n")){
                    System.out.println("삭제가 취소되었습니다.");
                }else{
                    System.out.println("제대로 다시 입력해주세요.");
                    return;
                }
            }
        }catch(NumberFormatException e){
            System.out.println("게시물 번호는 숫자로 입력해주세요.");
        }
    }

}
