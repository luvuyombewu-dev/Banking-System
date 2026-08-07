const formatDate = (date) => {

    if (!date) {

        return "-";

    }


    return new Date(date).toLocaleDateString(
        "en-ZA",
        {
            year: "numeric",
            month: "short",
            day: "numeric"
        }
    );

};


export default formatDate;