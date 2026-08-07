export const USER_ROLE = {

    CUSTOMER: "CUSTOMER",

    ADMIN: "ADMIN"

};



export const defaultUser = {

    id: null,

    firstName: "",

    lastName: "",

    email: "",

    role: USER_ROLE.CUSTOMER

};



export const defaultAuthState = {

    user: defaultUser,

    token: null,

    authenticated: false

};