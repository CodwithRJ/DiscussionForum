package discussion.forum.units.service;

import java.util.ArrayList;

import com.forum.units.*;

public class UpvoteServiceImpl implements UpvoteService {
	public static ArrayList<Upvote> upvotes = new ArrayList<>();
	
	public Upvote addUpvote(Question question, User user) {
		if (question != null && user != null) {
			Upvote upvote = getUpvote(user, question, null);
			if (upvote != null) { //MARK
				System.out.println("You have already upvoted");
				return upvote;
			}
			// ATTEMPTING TO MAKE SURE USER(APART FROM ADMIN/MODERATOR) CAN NOT UPVOTE HIS/HER OWN QUESTION
			if(user.getUserRole() != UserRole.ADMIN && user.getUserRole() != UserRole.MODERATOR){ // IF THE USER IS NOT AN ADMIN AND NOT A MODERATOR
				if(question.getUser().getUsername() == user.getUsername()) { // IF LOGGED IN USER IS SAME AS QUESTION USER
					System.out.println("You cannot upvote your own question"); //PRINT CANNOT UPVOTE
					return upvote;
				}
			}
			upvote = addUpvote(user, question, null);
			question.increaseUpvoteCount();
			return upvote;
		}
		System.out.println("Any specified field can't be empty");
		return null;
	}
	
	public long upvoteCount(Reply reply) {
		int count = 0;
		for (Upvote upvote : upvotes) {
			if (upvote != null && (upvote.getReply() == reply)) {
				count++;
			}
		}
		return count;
	}
	
	public Upvote addUpvote(Reply reply, User user) {
		if (reply != null && user != null) {
			Upvote upvote = getUpvote(user, null, reply);
			if (upvote != null) {
				System.out.println("You have already upvoted");
				return upvote;
			}
			// ATTEMPTING TO MAKE SURE USER(APART FROM ADMIN/MODERATOR) CAN NOT UPVOTE HIS/HER OWN REPLY
			if(user.getUserRole() != UserRole.ADMIN && user.getUserRole() != UserRole.MODERATOR){ // IF THE USER IS NOT AN ADMIN AND NOT A MODERATOR
				if(reply.getUser().getUsername() == user.getUsername()) { // IF LOGGED IN USER IS SAME AS REPLY USER
					System.out.println("You cannot upvote your own reply"); //PRINT CANNOT UPVOTE
					return upvote;
				}
			}

			upvote = addUpvote(user, null, reply);
			return upvote;
		}
		System.out.println("Any specified field can't be empty");
		return null;
	}
	
	private Upvote getUpvote(User user, Question question, Reply reply) {
		for (Upvote upvote : upvotes) {

			/*
			  Change the below if condition such that user can not upvote the same question or same reply twice,
			  but can upvote multiple questions and replies
			*/ // CREATING IF CONDITION TO HANDLE DUPLICATE UPVOTES
			/*if ((upvote.getUser() == user && upvote.getQuestion() == question) || (upvote.getUser() == user && upvote.getReply() == reply)){*/
			if (reply == null && upvote.getUser() == user && upvote.getQuestion() == question)
				return upvote;
				else if (question == null && upvote.getUser() == user && upvote.getReply() == reply)
					return upvote;
		}
		return null;
	}
	
	private Upvote addUpvote(User user, Question question, Reply reply) {
		Upvote upvote = new Upvote();
		upvote.setQuestion(question);
		upvote.setReply(reply);
		upvote.setUser(user);
		upvote.autoGenerateId();
		upvote.setCreated();
		upvotes.add(upvote);
		return upvote;
	}
}
