package lpiemam.com.apppokecards.viewmodel

import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.model.PokemonQuestions
import lpiemam.com.apppokecards.model.Question
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.retrofit.UsersRepository
//import lpiemam.com.apppokecards.room.UserCardsRepository
import java.util.*

class QuizzViewModel : ViewModel() {


    fun generateQuestions(): PokemonQuestions {
        val question1 = Question(
            "De quelle couleur est pikachu ?",
            Arrays.asList("Bleue", "Orange", "Jaune", "Marron"),
            2
        )

        val question2 = Question(
            "Comment s'appelle le pokémon faisant parti du trio de la team Rocket ?",
            Arrays.asList("Rattata", "Smogo", "Abo", "Miaouss"),
            3
        )

        val question3 = Question(
            "De quelle couleur est le shiny de psykokwak ?",
            Arrays.asList("Orange", "Bleue", "Jaune", "Rose"),
            1
        )

        val question4 = Question(
            "Comment s'appelle l'évolution de rhinoféros ?",
            Arrays.asList("Rhinocorne", "Rhinite", "Rhinastoc", "Rhinopharyngite"),
            2
        )

        val question5 = Question(
            "De quelle génération est issu Tiplouf ?",
            Arrays.asList("Il n'existe pas", "2ème génération", "4ème génération", "3ème génération"),
            2
        )

        val question6 = Question(
            "De quel autre type est Grodrive ? Vol - ?",
            Arrays.asList("Ténèbres", "Normal", "Spectre", "Fée"),
            2
        )

        val question7 = Question(
            "Quel pokémon ressemble à un coeur ?",
            Arrays.asList("Lovdisc", "Baudrive", "Roserade", "Milobellus"),
            0
        )

        val question8 = Question(
            "Quelle pierre faut-il pour faire évoluer cornèbre ?",
            Arrays.asList("Pierre Sinnoh", "Pierre Soleil", "Améliorator", "Aucune des trois"),
            0
        )

        val question9 = Question(
            "De quelle couleur est Lainergie, l'évolution de wattouat ?",
            Arrays.asList("Blanche", "Rose", "Rouge", "Jaune"),
            1
        )

        return PokemonQuestions(
            Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9
            )
        )
    }


    fun updateUserInDB(user: User) {
        UsersRepository.patchUser(user)
    }
}