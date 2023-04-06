package com.example.workoutapp

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1, "Jumping Jacks", R.drawable.jumping_jacks,false,false
        )
        exerciseList.add(jumpingJacks)
        val plank = ExerciseModel(
            2,"Plank", R.drawable.plank,false,false
        )
        exerciseList.add(plank)
        val pushUps = ExerciseModel(
        3,"Push Ups", R.drawable.push_ups,false,false
        )
        exerciseList.add(pushUps)
        val squats = ExerciseModel(
            4,"Squats",R.drawable.squats,false,false
        )
        exerciseList.add(squats)
        return exerciseList
    }
}