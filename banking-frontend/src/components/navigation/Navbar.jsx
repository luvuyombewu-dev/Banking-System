import {
    FaBell,
    FaUserCircle,
    FaBars
} from "react-icons/fa";


import useAuth from "../../hooks/useAuth";

import "./../../assets/styles/components/navbar.css";


const Navbar = ({
    toggleSidebar
}) => {


    const {
        user
    } = useAuth();



    return (

        <header className="navbar">


            <div className="navbar-left">


                <button

                    className="menu-toggle"

                    onClick={toggleSidebar}

                >

                    <FaBars />

                </button>



                <h2>

                    Banking System

                </h2>


            </div>





            <div className="navbar-right">



                <button

                    className="notification-button"

                >

                    <FaBell />

                </button>






                <div className="navbar-profile">


                    <FaUserCircle

                        className="navbar-avatar"

                    />



                    <div>


                        <p>

                            {
                                user
                                    ? `${user.firstName} ${user.lastName}`
                                    : "Guest User"
                            }

                        </p>



                        <span>

                            {
                                user?.role || "Customer"
                            }

                        </span>


                    </div>



                </div>



            </div>



        </header>

    );

};


export default Navbar;