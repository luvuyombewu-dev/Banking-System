import { useContext } from "react";

import {
    ThemeContext
} from "../../context/ThemeContext";


const ThemeToggle = () => {


    const {
        theme,
        toggleTheme
    } = useContext(ThemeContext);



    return (

        <div className="settings-option">


            <div>

                <h3>
                    Dark Mode
                </h3>


                <p>
                    Switch between light and dark theme.
                </p>


            </div>



            <label className="switch">


                <input

                    type="checkbox"

                    checked={
                        theme === "dark"
                    }

                    onChange={toggleTheme}

                />



                <span className="slider"></span>


            </label>


        </div>

    );

};


export default ThemeToggle;