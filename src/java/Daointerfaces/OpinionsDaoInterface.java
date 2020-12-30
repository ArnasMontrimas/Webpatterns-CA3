package Daointerfaces;

import Dtos.Opinion;

import java.util.ArrayList;

public interface OpinionsDaoInterface {

    /**
     * Save an opinion
     * @param userId user that added this opinion
     * @param bookId book commented
     * @param rating rating of book
     * @param comment comment of book
     * @return true if success, false if not
     */
    boolean addOpinion(int userId, int bookId, int rating, String comment);

    /**
     * Get all opinions for a specific book
     * @param bookId Book to fetch opinions
     * @return Returns an ArrayList of Opinions
     */
    ArrayList<Opinion> getBookOpinions(int bookId);

    /**
     * Check if a user has already an opinion on a book
     * @param userId user to check
     * @param bookId Book to check
     * @return Opinion if found, null if not
     */
    Opinion checkIfUserHasOpinion(int userId, int bookId);
}
