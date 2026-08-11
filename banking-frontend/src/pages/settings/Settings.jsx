import SecuritySettings from "../../components/settings/SecuritySettings";
import AccountPreferences from "../../components/settings/AccountPreferences";

import "../../assets/styles/pages/settings.css";

const Settings = () => {

    return (

        <div className="settings-page">

            <div className="settings-header">

                <div className="settings-header-content">

                    <div>

                        <h1>
                            Settings
                        </h1>

                        <p>
                            Manage your account preferences and security settings.
                        </p>

                    </div>

                </div>

            </div>


            <div className="settings-content">

                <AccountPreferences />

                <SecuritySettings />

            </div>

        </div>

    );

};

export default Settings;