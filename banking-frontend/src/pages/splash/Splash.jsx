import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import "../../assets/styles/pages/splash.css";


const Splash = () => {


    const navigate = useNavigate();




    useEffect(() => {


        const timer = setTimeout(() => {


            navigate(
                "/login",
                {
                    replace: true
                }
            );


        }, 1500);



        return () => clearTimeout(timer);


    }, [navigate]);







    return (

        <div className="splash-page">


            <div className="splash-card">


                <h1>
                    Banking System
                </h1>


                <p>
                    Loading...
                </p>



                <div className="loading-spinner"></div>



            </div>


        </div>

    );

};


export default Splash;