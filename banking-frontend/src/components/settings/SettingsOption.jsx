const SettingsOption = ({
    title,
    description,
    checked,
    onChange,
    children,
}) => {

    return (

        <div className="settings-option">


            <div>

                <h3>
                    {title}
                </h3>


                <p>
                    {description}
                </p>

            </div>



            {
                children
                    ? children
                    :
                    (
                        <label className="switch">

                            <input

                                type="checkbox"

                                checked={checked}

                                onChange={onChange}

                            />


                            <span className="slider"></span>


                        </label>
                    )
            }


        </div>

    );

};


export default SettingsOption;