package br.com.guilhermeapc.goyote.dao;

/**
 * Created by guilh on 19/01/2018.
 */

public class DatabaseAcessObject {
    private String _id;
    private String _encryptedpassword;
    private String _packagelist;
    private String _random_number;
    private String _password_method;

    public String get_password_method() {
        return _password_method;
    }

    public void set_password_method(String _password_method) {
        this._password_method = _password_method;
    }
    public String get_random_number() {
        return _random_number;
    }

    public void set_random_number(String random) {
        this._random_number = random;
    }


    public void set_encryptedpassword(String _encryptedpassword){
        this._encryptedpassword = _encryptedpassword;
    }
    public String get_packagelist(){
        return _packagelist;
    }
    public void set_packageList(String _packagelist){
        this._packagelist = _packagelist;
    }
    public void set_id(String _id){
        this._id = _id;
    }
    public String get_encryptedpassword(){
        return _encryptedpassword;
    }


}
