import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.Statement
import java.sql.ResultSet

val url = "jdbc:mysql://localhost:3306/departamento"
val user = "root"
val password = "root"

fun main() {
    SwingUtilities.invokeLater {
        criarEMostrarGUI()
    }
}

fun criarEMostrarGUI() {
    val frame = JFrame("Departamento")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = BorderLayout()

    val buttonPanel = JPanel()
    buttonPanel.layout = GridLayout(1, 4)

    val cadastrarButton = JButton("Cadastrar")
    val listarButton = JButton("Listar")
    val editarButton = JButton("Editar")
    val deletarButton = JButton("Deletar")

    buttonPanel.add(cadastrarButton)
    buttonPanel.add(listarButton)
    buttonPanel.add(editarButton)
    buttonPanel.add(deletarButton)

    val textFieldPanel = JPanel()
    textFieldPanel.layout = GridLayout(7, 2)

    val idLabel = JLabel("Id: ")
    val nomeLabel = JLabel("Nome: ")
    val emailLabel = JLabel("Email: ")
    val telefoneLabel = JLabel("Telefone: ")
    val departamentoLabel = JLabel("Departamento: ")
    val formacaoLabel = JLabel("Formacao: ")

    val idField = JTextField()
    val nomeField = JTextField()
    val emailField = JTextField()
    val telefoneField = JTextField()
    val departamentoField = JTextField()
    val formacaoField = JTextField()

    textFieldPanel.add(idLabel)
    textFieldPanel.add(idField)

    textFieldPanel.add(nomeLabel)
    textFieldPanel.add(nomeField)

    textFieldPanel.add(emailLabel)
    textFieldPanel.add(emailField)

    textFieldPanel.add(telefoneLabel)
    textFieldPanel.add(telefoneField)

    textFieldPanel.add(departamentoLabel)
    textFieldPanel.add(departamentoField)

    textFieldPanel.add(formacaoLabel)
    textFieldPanel.add(formacaoField)

    val outputLabel = JLabel()

    cadastrarButton.addActionListener {
        val id = idField.text
        val nome = nomeField.text
        val email = emailField.text
        val telefone = telefoneField.text
        val departamento = departamentoField.text
        val formacao = formacaoField.text

        try{
            create(id, nome, email, telefone, departamento, formacao)
            outputLabel.text = "Criado com sucesso!"
        } catch(e: Exception) {
            outputLabel.text = e.message
        }
    }

    listarButton.addActionListener {
        try{
            read()
            outputLabel.text = "Lido com sucesso!"
        } catch(e: Exception) {
            outputLabel.text = e.message
        }
    }

    editarButton.addActionListener {
        val id = idField.text
        val nome = nomeField.text
        val email = emailField.text
        val telefone = telefoneField.text
        val departamento = departamentoField.text
        val formacao = formacaoField.text
        try{
            update(id, nome, email, telefone, departamento, formacao)
            outputLabel.text = "Editado com sucesso!"
        } catch(e: Exception) {
            outputLabel.text = e.message
        }
    }

    deletarButton.addActionListener {
        val id = idField.text

        try{
            delete(id)
            outputLabel.text = "Deletado com sucesso!"
        } catch(e: Exception) {
            outputLabel.text = e.message
        }

    }

    frame.add(buttonPanel, BorderLayout.NORTH)
    frame.add(textFieldPanel, BorderLayout.CENTER)
    frame.add(outputLabel, BorderLayout.SOUTH)
    frame.pack()
    frame.isVisible = true

}

fun create(id: String, nome: String, email: String, telefone: String, departamento: String, formacao: String) {
    val connection: Connection = DriverManager.getConnection(url, user, password)

    val insertQuery = "INSERT INTO professor (id, nome, email, telefone, departamento, formacao) VALUES (?, ?, ?, ?, ?, ?)"

    val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

    preparedStatement.setString(1, id)
    preparedStatement.setString(2, nome)
    preparedStatement.setString(3, email)
    preparedStatement.setString(4, telefone)
    preparedStatement.setString(5, departamento)
    preparedStatement.setString(6, formacao)

    preparedStatement.executeUpdate()

    preparedStatement.close()
    connection.close()
}

fun read() {
    val connection: Connection = DriverManager.getConnection(url, user, password)

    val selectQuery = "SELECT * FROM professor"

    val statement: Statement = connection.createStatement();
    val resultSet: ResultSet = statement.executeQuery(selectQuery)

    var string = ""

    while(resultSet.next()) {
        val id = resultSet.getString("id")
        val nome = resultSet.getString("nome")
        val email = resultSet.getString("email")
        val telefone = resultSet.getString("telefone")
        val departamento = resultSet.getString("departamento")
        val formacao = resultSet.getString("formacao")
        string += "${id} | ${nome} | ${email} | ${telefone} | ${departamento} | ${formacao} \n"
    }

    JOptionPane.showMessageDialog(null, string, "Consulta", JOptionPane.INFORMATION_MESSAGE)

    resultSet.close()
    statement.close()
    connection.close()
}

fun update(id: String, nome: String, email: String, telefone: String, departamento: String, formacao: String) {
    val connection: Connection = DriverManager.getConnection(url, user, password)

    val updateQuery = "UPDATE professor SET nome = ?, email = ?, telefone = ?, departamento = ?, formacao = ? WHERE id = ?"

    val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

    preparedStatement.setString(1, nome)
    preparedStatement.setString(2, email)
    preparedStatement.setString(3, telefone)
    preparedStatement.setString(4, departamento)
    preparedStatement.setString(5, formacao)
    preparedStatement.setString(6, id)

    preparedStatement.executeUpdate()

    preparedStatement.close()
    connection.close()
}

fun delete(id: String) {
    val connection: Connection = DriverManager.getConnection(url, user, password)

    val deleteQuery = "DELETE FROM professor WHERE id = ?"

    val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)

    preparedStatement.setString(1, id)

    preparedStatement.executeUpdate()

    preparedStatement.close()
    connection.close()
}