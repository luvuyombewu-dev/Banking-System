import {
    NavLink,
    useNavigate
} from "react-router-dom";

import {
    FaUserCircle,
    FaUser,
    FaExchangeAlt,
    FaHistory,
    FaCog,
    FaSignOutAlt,
    FaHome
} from "react-icons/fa";

import useAuth from "../../hooks/useAuth";

import storage from "../../utils/storage";

import STORAGE_KEYS from "../../constants/storageKeys";

import "../../assets/styles/components/sidebar.css";


const Sidebar = () => {


    const navigate = useNavigate();


    const {
        user,
        logout
    } = useAuth();



    const menuItems = [

        {
            path: "/dashboard",
            label: "Dashboard",
            icon: <FaHome />
        },

        {
            path: "/profile",
            label: "Profile",
            icon: <FaUser />
        },

        {
            path: "/transactions",
            label: "Transactions",
            icon: <FaExchangeAlt />
        },

        {
            path: "/recent-transactions",
            label: "Recent Transactions",
            icon: <FaHistory />
        },

        {
            path: "/settings",
            label: "Settings",
            icon: <FaCog />
        }

    ];



    const handleLogout = () => {


        if (logout) {

            logout();

        } else {

            storage.remove(STORAGE_KEYS.TOKEN);

            storage.remove(STORAGE_KEYS.USER);

        }


        navigate("/login");

    };




    return (

        <aside className="sidebar">


            <div className="sidebar-profile">


                <FaUserCircle className="sidebar-avatar" />


                <p className="sidebar-welcome">
                    Welcome back
                </p>


                <h3>

                    {
                        user
                            ? `${user.firstName} ${user.lastName}`
                            : "Customer"
                    }

                </h3>


            </div>





            <div className="sidebar-content">


                <nav className="sidebar-menu">


                    {
                        menuItems.map((item) => (


                            <NavLink

                                key={item.path}

                                to={item.path}

                                className={({ isActive }) =>
                                    isActive
                                        ? "sidebar-link active"
                                        : "sidebar-link"
                                }

                            >

                                <span className="sidebar-icon">

                                    {item.icon}

                                </span>


                                <span>

                                    {item.label}

                                </span>


                            </NavLink>


                        ))
                    }


                </nav>





                <button

                    className="logout-button"

                    onClick={handleLogout}

                >

                    <span className="sidebar-icon">

                        <FaSignOutAlt />

                    </span>


                    <span>
                        Logout
                    </span>


                </button>


            </div>



        </aside>

    );

};


export default Sidebar;