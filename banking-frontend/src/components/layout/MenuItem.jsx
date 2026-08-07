import { NavLink } from "react-router-dom";


const MenuItem = ({
    to,
    icon,
    label,
}) => {


    return (

        <NavLink

            to={to}

            aria-label={label}

            className={({ isActive }) =>
                isActive
                    ? "menu-item active"
                    : "menu-item"
            }

        >

            <span className="menu-icon">

                {icon}

            </span>


            <span>

                {label}

            </span>


        </NavLink>

    );

};


export default MenuItem;