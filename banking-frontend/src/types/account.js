export const ACCOUNT_STATUS = {

    ACTIVE: "ACTIVE",

    INACTIVE: "INACTIVE",

    BLOCKED: "BLOCKED"

};



export const ACCOUNT_TYPE = {

    SAVINGS: "SAVINGS",

    CURRENT: "CURRENT"

};



export const defaultAccount = {

    id: null,

    accountNumber: "",

    accountHolder: "",

    balance: 0,

    status: ACCOUNT_STATUS.ACTIVE,

    type: ACCOUNT_TYPE.SAVINGS,

    transactions: []

};