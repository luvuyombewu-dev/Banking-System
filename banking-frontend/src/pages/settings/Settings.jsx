import SectionCard from "../../components/common/SectionCard";

import ThemeToggle from "../../components/settings/ThemeToggle";
import NotificationToggle from "../../components/settings/NotificationToggle";
import SecuritySettings from "../../components/settings/SecuritySettings";
import AccountPreferences from "../../components/settings/AccountPreferences";

import "../../assets/styles/pages/settings.css";


const Settings = () => {


    return (

        <div className="settings-page">


            <div className="page-header">


                <h1>
                    Settings
                </h1>


                <p>
                    Customize your application preferences
                </p>


            </div>




            <SectionCard

                title="Appearance"

            >

                <ThemeToggle />

            </SectionCard>





            <SectionCard

                title="Notifications"

            >

                <NotificationToggle />

            </SectionCard>





            <AccountPreferences />





            <SecuritySettings />



        </div>

    );

};


export default Settings;