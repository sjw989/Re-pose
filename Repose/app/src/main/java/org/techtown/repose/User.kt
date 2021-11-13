package org.techtown.repose

class User {
    var user_id : String = ""
    var user_pw : String = ""
    var pose_list = ArrayList<String>()
    var timer_list = mutableListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false)
    var days_list = mutableListOf<Boolean>(false,false,false,false,false,false,false)

    constructor(user_id: String, user_pw: String, pose_list: ArrayList<String>, timer_list : MutableList<Boolean>, days_list : MutableList<Boolean>) {
        this.user_id = user_id
        this.user_pw = user_pw
        this.pose_list = pose_list
        this.timer_list = timer_list
        this.days_list = days_list
    }
}