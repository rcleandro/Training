package br.com.leandro.training

import br.com.leandro.training.core.database.ExercisesConverter
import br.com.leandro.training.core.database.entity.Exercise
import org.junit.Assert
import org.junit.Test

class ExercisesConverterTest {

    private val converter = ExercisesConverter()

    @Test
    fun `fromExercises should convert list of integers to a string`() {
        val exercises = listOf(
            Exercise(
                name = 1,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 1 commentary Test"
            ),
            Exercise(
                name = 2,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 2 commentary Test",
                selected = true
            ),
            Exercise(
                name = 3,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 3 commentary Test"
            ),
            Exercise(
                name = 4,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 4 commentary Test"
            ),
            Exercise(
                name = 5,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 5 commentary Test",
                selected = true
            )
        )
        val expected =
            "[{\"name\":1,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 1 commentary Test\",\"selected\":false},{\"name\":2,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 2 commentary Test\",\"selected\":true},{\"name\":3,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 3 commentary Test\",\"selected\":false},{\"name\":4,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 4 commentary Test\",\"selected\":false},{\"name\":5,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 5 commentary Test\",\"selected\":true}]"
        val result = converter.fromExercises(exercises)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `toExercises should convert string to list of Exercises`() {
        val exercises = "[{\"name\":1,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 1 commentary Test\",\"selected\":false},{\"name\":2,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 2 commentary Test\",\"selected\":true},{\"name\":3,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 3 commentary Test\",\"selected\":false},{\"name\":4,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 4 commentary Test\",\"selected\":false},{\"name\":5,\"image\":\"https://github.githubassets.com/favicons/favicon-dark.png\",\"comments\":\"Exercise 5 commentary Test\",\"selected\":true}]"
        val expected = listOf(
            Exercise(
                name = 1,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 1 commentary Test"
            ),
            Exercise(
                name = 2,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 2 commentary Test",
                selected = true
            ),
            Exercise(
                name = 3,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 3 commentary Test"
            ),
            Exercise(
                name = 4,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 4 commentary Test"
            ),
            Exercise(
                name = 5,
                image = "https://github.githubassets.com/favicons/favicon-dark.png",
                comments = "Exercise 5 commentary Test",
                selected = true
            )
        )
        val result = converter.toExercises(exercises)
        Assert.assertEquals(expected, result)
    }
}