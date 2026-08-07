import STORAGE_KEYS from "../constants/storageKeys";


const storage = {


    get(key) {

        return localStorage.getItem(key);

    },


    set(key, value) {

        localStorage.setItem(
            key,
            value
        );

    },


    remove(key) {

        localStorage.removeItem(key);

    },


    getToken() {

        return localStorage.getItem(
            STORAGE_KEYS.TOKEN
        );

    },


    setToken(token) {

        localStorage.setItem(
            STORAGE_KEYS.TOKEN,
            token
        );

    },


    removeToken() {

        localStorage.removeItem(
            STORAGE_KEYS.TOKEN
        );

    },


    getUser() {

        const user = localStorage.getItem(
            STORAGE_KEYS.USER
        );


        return user
            ? JSON.parse(user)
            : null;

    },


    setUser(user) {

        localStorage.setItem(
            STORAGE_KEYS.USER,
            JSON.stringify(user)
        );

    },


    removeUser() {

        localStorage.removeItem(
            STORAGE_KEYS.USER
        );

    },


    clear() {

        localStorage.clear();

    }


};


export default storage;