import {
    createContext,
    useContext,
    useEffect,
    useState
} from "react";


export const ThemeContext = createContext(null);



export const ThemeProvider = ({
    children
}) => {


    const [theme, setTheme] = useState(
        localStorage.getItem("theme") || "light"
    );



    useEffect(() => {

        document.documentElement.setAttribute(
            "data-theme",
            theme
        );


        localStorage.setItem(
            "theme",
            theme
        );


    }, [theme]);




    const toggleTheme = () => {

        setTheme(
            previous =>
                previous === "light"
                    ? "dark"
                    : "light"
        );

    };




    return (

        <ThemeContext.Provider

            value={{
                theme,
                toggleTheme
            }}

        >

            {children}

        </ThemeContext.Provider>

    );

};




export const useTheme = () => {

    return useContext(ThemeContext);

};