package com.services;

import com.repositories.FilteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.repositories.LocationRepository;
import java.util.HashSet;
import java.util.Set;
import com.models.*;

//@Author s192671
@Component
public class FriendListService {
    @Autowired
    private FilteredUserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    public Set<User> getAllFriends(String userEmail) {
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

    public Set<User> getReceivedFriendRequests(String userEmail) {
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

    public Set<User> getSentFriendRequests(String userEmail) {
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

    public String addFriend(String currentUserEmail, String friendEmail) {
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

    public String acceptFriendRequest(String currentUserEmail, String friendEmail) {
        String returnMessage = "";
        if (friendEmail == null || friendEmail.isEmpty() || userRepository.findUsersByEmail(friendEmail).isEmpty())
            return String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            User currentUser = userRepository.findUserByEmail(currentUserEmail);
            if (friend != null && currentUser != null) {
                if (currentUser.getFriends().contains(friend))
                    return "That person is already part of your friend list!";
                else {
                    try {
                        currentUser.setFriend(friend);
                        userRepository.save(currentUser);
                        userRepository.save(friend);
                        returnMessage = String.format("Friend request from user %s was accepted!", friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error and the operation failed!";
                    }
                }
            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return returnMessage;
    }

    public String rejectFriendRequest(String currentUserEmail, String friendEmail) {
        String returnMessage = "";
        if (friendEmail == null || friendEmail.isEmpty() || userRepository.findUsersByEmail(friendEmail).isEmpty())
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            User currentUser = userRepository.findUserByEmail(currentUserEmail);
            if (friend != null && currentUser != null) {
                if (!currentUser.getReceivedFriendRequests().contains(friend))
                    returnMessage = "That person is not a part of your followers!";
                else {
                    try {
                        currentUser.removeFollower(friendEmail);
                        friend.removeFollowing(currentUser.getEmail());
                        userRepository.save(currentUser);
                        userRepository.save(friend);
                        returnMessage = String.format("Friend request from user with email %s was rejected",
                                friendEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        returnMessage = "There was an error and the operation failed!";
                    }
                }
            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return returnMessage;
    }

    public String deleteFriend(String currentUserEmail, String friendEmail) {
        String returnMessage = "";
        if (friendEmail == null || friendEmail.isEmpty())
            returnMessage = String.format("The user with email %s does not exist!", friendEmail);
        else {
            User friend = userRepository.findUserByEmail(friendEmail);
            User currentUser = userRepository.findUserByEmail(currentUserEmail);
            // User friend = userRepository.findUsersByEmail(friendEmail).get(0);
            if (friend != null && currentUser != null) {
                try {
                    currentUser.removeFriendFromFollowingByEmail(friendEmail);
                    friend.removeFriendFromFollowerByEmail(currentUser.getEmail());
                    userRepository.save(currentUser);
                    userRepository.save(friend);
                    returnMessage = String.format("Friend %s was removed from your list!", friendEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                    returnMessage = "There was an error when removing your friend! Check the email you input!";
                }

            } else {
                returnMessage = "Error: one of the entities is null!";
            }
        }
        return returnMessage;

    }
}