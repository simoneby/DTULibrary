package com.services;

import com.repositories.FilteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import com.models.*;

@Component
public class FriendListService
{
    @Autowired
    private FilteredUserRepository userRepository;

    public String testShit()
    {
        if(userRepository== null)
        {
            return "This is not getting injected";
        }
        return "ok";
    }
    public Set<User> getAllFriends(String userEmail)
    {
        if (userEmail == null) {
            try {
                throw new Exception("User email undefined");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<User>();
            }
        }
        return userRepository.findUserByEmail(userEmail).getFriends();
    }
    public Set<User> getReceivedFriendRequests(String userEmail)
    {
        if (userEmail == null) {
            try {
                throw new Exception("User email undefined");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<User>();
            }
        }
        return userRepository.findUserByEmail(userEmail).getReceivedFriendRequests();
    }
    public Set<User> getSentFriendRequests(String userEmail)
    {
        if (userEmail == null) {
            try {
                throw new Exception("User email undefined");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashSet<User>();
            }
        }
        return userRepository.findUserByEmail(userEmail).getSentFriendRequests();
    }
    public String addFriend(String currentUserEmail, String friendEmail)
    {
        String returnMessage;
        if (friendEmail == null || friendEmail.isEmpty() || userRepository.findUsersByEmail(friendEmail).isEmpty())
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            if (friend != null && currentUserEmail != null) {
                User user = userRepository.findUserByEmail(currentUserEmail);
                // u.getFriends();
                if (user.getFriends().contains(friend))
                    returnMessage = "That person is already part of your friend list!";
                else {
                    try {

                        friend.getFriends();
                        user.setFriend(friend);
                        userRepository.save(user);
                        returnMessage = String.format("A friend request was sent to user with email %s!", friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error and the friend request could not be sent!";
                    }
                }
            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return returnMessage;
    }
}