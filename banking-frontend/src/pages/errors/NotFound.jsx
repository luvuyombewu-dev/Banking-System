import { Link } from "react-router-dom";

import "../../assets/styles/pages/errors.css";


const NotFound = () => {

    return (

        <div className="error-page">

            <div className="error-card">

                <h1>
                    404
                </h1>


                <h2>
                    Page Not Found
                </h2>


                <p>
                    The page you are looking for does not exist.
                </p>


                <Link
                    to="/dashboard"
                    className="error-button"
                >
                    Go Back Dashboard
                </Link>


            </div>

        </div>

    );

};


export default NotFound;