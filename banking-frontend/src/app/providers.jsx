import {
    BrowserRouter
} from "react-router-dom";


import {
    AuthStoreProvider
} from "../store/authStore";


import {
    ThemeProvider
} from "../context/ThemeContext";



const Providers = ({
    children
}) => {


    return (

        <BrowserRouter>

            <AuthStoreProvider>

                <ThemeProvider>

                    {children}

                </ThemeProvider>

            </AuthStoreProvider>

        </BrowserRouter>

    );

};


export default Providers;