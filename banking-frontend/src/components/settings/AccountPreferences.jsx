import Card from "../ui/Card";

const AccountPreferences = () => {

    return (

        <Card title="Account Preferences">

            <div className="settings-option">

                <div>

                    <h3>
                        Preferred Currency
                    </h3>

                    <p>
                        Select the currency used throughout your account.
                    </p>

                </div>

                <select defaultValue="ZAR">

                    <option value="ZAR">
                        South African Rand (ZAR)
                    </option>

                    <option value="USD">
                        US Dollar (USD)
                    </option>

                    <option value="EUR">
                        Euro (EUR)
                    </option>

                    <option value="GBP">
                        British Pound (GBP)
                    </option>

                </select>

            </div>


            <div className="settings-option">

                <div>

                    <h3>
                        Language
                    </h3>

                    <p>
                        Choose your preferred application language.
                    </p>

                </div>

                <select defaultValue="en">

                    <option value="en">
                        English
                    </option>

                    <option value="af">
                        Afrikaans
                    </option>

                    <option value="xh">
                        isiXhosa
                    </option>

                    <option value="zu">
                        isiZulu
                    </option>

                </select>

            </div>

        </Card>

    );

};

export default AccountPreferences;