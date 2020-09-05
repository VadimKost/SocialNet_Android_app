package com.V.VSocial


import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject



object Data {
    interface VolleyCallBack {
        fun onReqestDone()
    }
    var volleyListener:VolleyCallBack?=null
    var main_queue:RequestQueue?=null
    var CurrentUser: JSONObject? =null
    fun createRequestQueue(context: Context): RequestQueue? {
        if(main_queue==null){
            main_queue=Volley.newRequestQueue(context)
        }
        volleyListener = context as VolleyCallBack
        return main_queue
    }
    fun getCurrentUser(context: Context): JSONObject? {

        var data:JSONObject?=null
        val url = "http://www.vako.ga/api/currentuser/"
        val req=object :JsonObjectRequest(Request.Method.GET, url, null, object : Response.Listener<JSONObject?> {
            override fun onResponse(response: JSONObject?) {
                CurrentUser=response
                data=response
                Log.e("",response.toString())
                volleyListener!!.onReqestDone()


            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError?) {
                // TODO Auto-generated method stub
            }
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>()
                headers["Authorization"] = "Basic dGVzdDp2YWRpbTA4MTA="
                return headers
            }
        }
        main_queue = createRequestQueue(context)
        main_queue!!.add(req)
        return data

    }
}


class MyJsonObjectRequest(method:Int, url: String?, jsonRequest: JSONObject?, listener: Response.Listener<JSONObject?>?, errorListener: Response.ErrorListener?):JsonObjectRequest(method, url, jsonRequest, listener, errorListener){
    override fun getHeaders(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Authorization"] = "Basic dGVzdDp2YWRpbTA4MTA="
        return headers
    }
}