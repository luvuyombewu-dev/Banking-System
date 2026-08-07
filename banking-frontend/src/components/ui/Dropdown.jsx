import React, { useState } from "react";


const Dropdown = ({
    label,
    children,
    className = "",
}) => {


    const [open, setOpen] = useState(false);



    return (

        <div className={`dropdown ${className}`}>


            <button

                type="button"

                onClick={() => setOpen(!open)}

                className="dropdown-toggle"

            >

                {label}

            </button>





            {
                open && (

                    <div className="dropdown-menu">

                        {children}

                    </div>

                )
            }


        </div>

    );

};


export default Dropdown;