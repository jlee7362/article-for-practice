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
            if (cmd.equals("memeber join")) {
                join();
            } else if (cmd.equals("member login")) {
                login();
            } else if (cmd.equals("member logout")) {
                logout();
            } else if (cmd.equals("article write")) {
                writeArticle();
            } else if (cmd.equals("article list")) {
                listArticle();
            } else if (cmd.equals("article detail")) {
                detailArticle(cmd);
            } else if (cmd.equals("article modify")) {
                modifyArticle(cmd);
            } else if (cmd.equals("article delete")) {
                deleteArticle(cmd);
            } else if (cmd.equals("exit")) {
                System.out.println("===프로그램 종료===");
                break;
            } else {
                System.out.println("제대로 입력해주세요.");
            }
        }

    }
    private void join(){
        System.out.print("로그인 할 때 쓸 아이디: ");
        String loginId = sc.nextLine();

        if(!memberService.isJoinable(loginId)){
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

    private void login(){
        if(loginedMember != null){
            System.out.println("이미 로그인 되어있습니다.");
            return;
        }
        System.out.print("아이디: ");
        String loginId = sc.nextLine();
        System.out.print("비밀번호: ");
        String loginPw = sc.nextLine();
        Member member = memberService.getMemberByLoginIdAndPw(loginId,loginPw);
        if(member == null){
            System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
        }else{
            loginedMember=member;
            System.out.printf("%s님 환영합니다.\n", member.getName());
        }
    }
    private void logout(){
        if(loginedMember == null){
            System.out.println("현재 로그인 상태가 아닙니다.");
        }else{
            System.out.printf("%s님 로그아웃 되었습니다\n", loginedMember.getName());
            loginedMember = null;
        }
    }
    private void writeArticle(){
        
    }
}
