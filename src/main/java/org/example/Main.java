package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Article> articleList = new ArrayList<>();
        List<Member> memberList = new ArrayList<>();
        Member loginedMember = null;
        int lastMemberId = 0;
        int lastId = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("===프로그램 시작===");

        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();
            if(cmd.startsWith("member join")){
                System.out.print("로그일 할 때 쓸 아이디: ");
                String loginId = sc.nextLine();

                //중복 체크
                boolean isJoinable = true;
                for (Member member : memberList){
                    if(member.getLoginiD().equals(loginId)){
                        isJoinable = false;
                        break;
                    }
                }
                if(!isJoinable){
                    System.out.println("이미 사용 중인 아이디입니다.");
                    continue;
                }

                System.out.print("로그인 비밀번호: ");
                String loginPw = sc.nextLine();
                System.out.print("이름: ");
                String name = sc.nextLine();

                memberList.add(new Member(++lastMemberId, loginId, loginPw, name));
                System.out.println("회원가입이 완료되었습니다.");
                continue;
            }else if (cmd.equals("member login")){
                if(loginedMember!=null){
                    System.out.println("이미 로그인 되어있습니다.");
                    continue;
                }
                System.out.print("로그인 아이디: ");
                String loginId = sc.nextLine();
                System.out.print("로그인 비밀번호: ");
                String loginPw = sc.nextLine();

                Member foundMember = null;
                for(Member member : memberList){
                    if(member.getLoginiD().equals(loginId) && member.getLoginPw().equals(loginPw)){
                        foundMember = member;
                        break;
                    }
                }
                if(foundMember == null){
                    System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
                } else{
                    loginedMember = foundMember;
                    System.out.printf("%s님 환영합니다.\n",foundMember.getName());
                }
                continue;
            }else if (cmd.equals("member logout")){
                if(loginedMember == null){
                    System.out.println("현재 로그인 상태가 아닙니다.");
                }else {
                    System.out.printf("%s님 로그아웃 되었습니다.\n",loginedMember.getName());
                    loginedMember = null;
                }
                continue;
            }

            else if (cmd.equals("exit")) {
                System.out.println("=====프로그램 종료=====");
                break;
            } else if (cmd.equals("article write")) {
                if(loginedMember == null){
                    System.out.println("로그인 후 이용해주세요.");
                    continue;
                }
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();

                String regDate = Util.getNowDate();
                lastId++;
                int id = lastId;
                Article addArticle = new Article();

                addArticle.setId(id);
                addArticle.setBody(body);
                addArticle.setTitle(title);
                addArticle.setRegDate(regDate);

                articleList.add(addArticle);

                System.out.printf("%d번 글이 생성되었습니다.\n", id);

            } else if (cmd.startsWith("article list")) {

                if (articleList.isEmpty()) {
                    System.out.println("리스트에 글이 없습니다.");
                    continue;
                }

                System.out.println("번호  /   제목  /   내용");
                System.out.println("=".repeat(30));

                for (int i = articleList.size() - 1; i >= 0; i--) {

                    Article article = articleList.get(i);
                    System.out.printf("%d       %s      %s    \n", article.getId(), article.getTitle(), article.getBody());
                }

            } else if (cmd.startsWith("article modify")) {
                String[] cmdBits = cmd.split(" ");
                if (cmdBits.length < 3) {
                    System.out.println("잘못된 형식입니다.");
                    continue;
                }
                try {
                    int targetId = Integer.parseInt(cmdBits[2]);
                    Article found = null;
                    for (Article article : articleList) {
                        if (article.getId() == targetId) {
                            found = article;
                            break;
                        }
                    }
                    if (found == null) {
                        System.out.println("해당 게시물이 존재하지 않습니다.");
                    } else {
                        System.out.println("기존제목: " + found.getTitle());
                        System.out.println("기존내용: " + found.getBody());
                        System.out.print("새로 쓸 제목: ");
                        String newtitle = sc.nextLine();
                        System.out.print("새로 쓸 내용: ");
                        String newbody = sc.nextLine();
                        found.setTitle(newtitle);
                        found.setBody(newbody);

                        System.out.println("게시글이 수정되었습니다.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("modify뒤에 번호를 입력해주세요.");
                }

            } else if (cmd.startsWith("article detail")) {
                String[] cmdBits = cmd.split(" ");
                if (cmdBits.length < 3) {
                    System.out.println("잘못된 형식입니다.");
                    continue;
                }
                try {
                    int targetId = Integer.parseInt(cmdBits[2]);
                    Article found = null;
                    for (Article article : articleList) {
                        if (article.getId() == targetId) {
                            found = article;
                            break;
                        }
                    }
                    if (found == null) {
                        System.out.println("해당 게시물이 존재하지 않습니다.");
                    } else {
                        System.out.println("번호: " + found.getId());
                        System.out.println("날짜: " + found.getRegDate());
                        System.out.println("제목: " + found.getTitle());
                        System.out.println("내용: " + found.getBody());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("게시물 번호는 숫자로 입력해야 합니다.");
                }
            } else if (cmd.startsWith("article delete")) {
                String[] cmdBits = cmd.split(" ");
                if (cmdBits.length < 3) {
                    System.out.println("잘못된 형식입니다.");
                    continue;
                }
                try {
                    int targetId = Integer.parseInt(cmdBits[2]);
                    Article found = null;
                    for (Article article : articleList) {
                        if (article.getId() == targetId) {
                            found = article;
                            break;
                        }
                    }
                    if (found == null) {
                        System.out.println("게시글이 없습니다.");
                    } else {
                        System.out.println("정말 삭제하시겠습니까?(y/n)");
                        String confirm = sc.nextLine();
                        if (confirm.equals("y")) {
                            articleList.remove(found);
                            System.out.println("해당 게시물을 삭제했습니다.");
                        } else if (confirm.equals("n")) {
                            System.out.println("게시글 삭제가 취소되었습니다.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("delete뒤에 숫자를 입력해주세요.");
                }
                continue;
            } else {
                System.out.println("제대로 입력해주세요");
            }

        }
    }
}


