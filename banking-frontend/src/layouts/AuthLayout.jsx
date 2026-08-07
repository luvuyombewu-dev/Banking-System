import { Outlet } from "react-router-dom";
import "../assets/styles/layout/authLayout.css";


const AuthLayout = () => {


    return (

        <div className="auth-layout">

            <div className="auth-container">

                <Outlet />

            </div>

        </div>

    );

};


export default AuthLayout;