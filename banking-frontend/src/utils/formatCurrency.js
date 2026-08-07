import appConfig from "../config/appConfig";


const formatCurrency = (amount) => {


    if (
        amount === null ||
        amount === undefined
    ) {

        return "R 0.00";

    }



    return new Intl.NumberFormat(

        appConfig?.currency?.locale || "en-ZA",

        {

            style: "currency",

            currency:
                appConfig?.currency?.code || "ZAR"

        }

    ).format(amount);


};


export default formatCurrency;