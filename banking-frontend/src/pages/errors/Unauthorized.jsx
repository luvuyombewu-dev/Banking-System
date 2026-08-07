import { Link } from "react-router-dom";

import "../../assets/styles/pages/errors.css";


const Unauthorized = () => {

    return (

        <div className="error-page">

            <div className="error-card">

                <h1>
                    401
                </h1>


                <h2>
                    Unauthorized
                </h2>


                <p>
                    You do not have permission to access this page.
                </p>


                <Link
                    to="/login"
                    className="error-button"
                >
                    Return to Login
                </Link>


            </div>

        </div>

    );

};


export default Unauthorized;