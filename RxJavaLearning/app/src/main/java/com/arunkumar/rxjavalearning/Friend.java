package com.arunkumar.rxjavalearning;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arunkumar on 14/04/18.
 */

public class Friend {
    private ArrayList<Friend> friends;
    private String email;
    int a = 0;

    public Friend(String email) {
        this.email = email;
        this.friends = new ArrayList<Friend>();
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void addFriendship(Friend friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public boolean canBeConnected(Friend friend) {
        boolean isConnected = false;

        if (check(friend.getFriends()) == 1) {
            isConnected = true;
        }

        return isConnected;
    }

    private int check(List<Friend> friendList) {
        if (friendList.contains(this)) {
            Log.d("", "yes");
            a = 1;
        } else if (friendList.size() >= 1) {
            Log.d("", "check other friend");
            check(friendList.get(0).getFriends());
        }
        return a;

    }

    public static void main() {
        Friend a = new Friend("A");
        Friend b = new Friend("B");
        Friend c = new Friend("C");
        Friend d = new Friend("D");
        Friend e = new Friend("E");
        Friend f = new Friend("F");
        Friend g = new Friend("G");
        Friend h = new Friend("H");
        Friend i = new Friend("I");

        a.addFriendship(b);
        b.addFriendship(c);
        c.addFriendship(d);

        System.out.println(a.canBeConnected(d));
    }
}