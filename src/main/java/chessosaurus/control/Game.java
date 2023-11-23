package chessosaurus.control;

import chessosaurus.base.Board;
import chessosaurus.base.Color;
import chessosaurus.base.Move;
import chessosaurus.engine.EnemyMoverContext;
import chessosaurus.engine.IEnemyMoverContext;
import chessosaurus.players.Enemy;
import chessosaurus.review.ReviewerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Class to manage the game by managing the main board and the players.
 * @version 1.0
 * @author Fabian Unger
 */
public class Game {

    private Board chessboard;
    private final Enemy enemy;
    private List<Move> moves;

    public Game(Color enemyColor, IEnemyMoverContext enemyMoverContext) {
        this.chessboard = new Board();
        this.enemy = new Enemy(enemyColor, enemyMoverContext);
        this.moves = new ArrayList<>();
    }

    public Board getChessboard() {
        return this.chessboard;
    }

    public void setChessboard(Board chessboard) {
        this.chessboard = chessboard;
    }

    public List<Move> getMoves() {
        return new ArrayList<>(this.moves);
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    /**
     * Checks the player's move and updates the chessboard accordingly
     * @param move Move of the player
     */
    public void reviewPlayerMove(Move move) {
        ReviewerContext reviewerContext = new ReviewerContext();
        boolean isLegal;

        if(move.getFrom().getPiece().isEmpty()){
            isLegal = false;
        }
        else {
            isLegal = reviewerContext.isLegalMove(move, this.chessboard);
        }

        if (isLegal) {
            this.chessboard.getChessboard()[8 - move.getTo().getFile()][move.getTo().getRankVal() - 1].setPiece(move.getFrom().getPiece().get());
            this.chessboard.getChessboard()[8 - move.getFrom().getFile()][move.getFrom().getRankVal() - 1].setPiece(Optional.empty());
        }
        else {
            // TODO: Hier hat Spieler an Frontend verloren
        }
    }

    /**
     * Access the Enemy to get the best move possible.
     * @return best move
     */
    public Move calculateBestEnemyMove() {
        return this.enemy.getBestMove(this.moves, this.chessboard);
    }
}
