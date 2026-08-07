import {
    Outlet
} from "react-router-dom";


import "../assets/styles/layout/dashboardLayout.css";


import Sidebar from "../components/navigation/Sidebar";
import Header from "../components/layout/Header";
import MainContent from "../components/layout/MainContent";



const DashboardLayout = () => {


    return (

        <div className="dashboard-layout">


            <Sidebar />


            <div className="dashboard-main">


                <Header />


                <MainContent>


                    <Outlet />


                </MainContent>


            </div>


        </div>

    );

};


export default DashboardLayout;