package br.fatec.smartbooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.fatec.smartbooking.model.Dialogue;

public class DialogueDAO implements IDialogue {

	@Override
	public Dialogue findAnswer(Dialogue dialogue) {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		Connection connection = databaseConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement("SELECT questions.question_text, questions.id_behaviour, "
					+ "answers.answer_text, count_occurrences(?, questions.question_text) AS occurrences "
					+ "FROM questions INNER JOIN questions_answers ON questions_answers.id_question = questions.id_question "
					+ "INNER JOIN answers ON questions_answers.id_answer = answers.id_answer "
					+ "GROUP BY questions.id_question HAVING occurrences > 0 ORDER BY occurrences DESC LIMIT 0,1");
			preparedStatement.setString(1, dialogue.getQuestion());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				dialogue.setAnswer(resultSet.getString("answer_text"));
				dialogue.setBehaviour(resultSet.getInt("id_behaviour"));
			} else {
				dialogue.setAnswer("Desculpe, eu não entendi");
			}

			connection.close();

			return dialogue;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			databaseConnection.closeConnection(connection);
		}

	}

}
