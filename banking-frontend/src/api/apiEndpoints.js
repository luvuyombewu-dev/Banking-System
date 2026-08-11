const API_ENDPOINTS = {

    AUTH: {

        LOGIN: "/auth/login",

        REGISTER: "/auth/register",

        FORGOT_PASSWORD: "/auth/forgot-password",

        RESET_PASSWORD: "/auth/reset-password",

        CHANGE_PASSWORD: "/auth/change-password"

    },


    ACCOUNT: {

        CREATE: "/accounts/create",

        MY_ACCOUNT: "/accounts/my-account",

        DEPOSIT: "/accounts/deposit",

        WITHDRAW: "/accounts/withdraw",

        TRANSFER: "/accounts/transfer"

    },


    TRANSACTIONS: {

        ALL: "/transactions",

        RECENT: "/transactions/recent"

    },


    PROFILE: {

        ME: "/profile"

    }

};

export default API_ENDPOINTS;