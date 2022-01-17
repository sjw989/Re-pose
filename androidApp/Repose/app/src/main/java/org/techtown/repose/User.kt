package org.techtown.repose

class User {
    var user_id : String = ""
    var user_pw : String = ""
    var pose_list = ArrayList<String>()

    constructor(user_id: String, user_pw: String, pose_list: ArrayList<String>) {
        this.user_id = user_id
        this.user_pw = user_pw
        this.pose_list = pose_list
    }
}