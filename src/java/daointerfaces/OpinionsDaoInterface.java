package daointerfaces;

import java.util.ArrayList;

import dtos.Opinion;

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

    /**
     * Remove an opinion by giving user and book
     * @param userId user id
     * @param bookId book id
     * @return true if successful false otherwise
     */
    boolean removeOpinion(int userId, int bookId);
}
