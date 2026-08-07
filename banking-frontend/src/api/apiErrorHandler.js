const apiErrorHandler = (error) => {

    if (!error.response) {
        return {
            message: "Unable to connect to server. Please try again.",
            status: null,
        };
    }


    const { status, data } = error.response;


    switch (status) {

        case 400:
            return {
                message: data?.message || "Invalid request.",
                status,
            };


        case 401:
            return {
                message: "Unauthorized. Please login again.",
                status,
            };


        case 403:
            return {
                message: "Access denied.",
                status,
            };


        case 404:
            return {
                message: "Requested resource was not found.",
                status,
            };


        case 409:
            return {
                message: data?.message || "Conflict occurred.",
                status,
            };


        case 500:
            return {
                message: "Server error. Please try again later.",
                status,
            };


        default:
            return {
                message: data?.message || "An unexpected error occurred.",
                status,
            };
    }

};


export default apiErrorHandler;