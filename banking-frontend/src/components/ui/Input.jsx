import React from "react";


const Input = ({
    label,
    type = "text",
    name,
    value,
    placeholder,
    onChange,
    error,
    disabled = false,
    required = false,
    className = "",
}) => {


    return (

        <div className="input-group">


            {
                label && (

                    <label htmlFor={name}>

                        {label}

                    </label>

                )
            }




            <input

                id={name}

                name={name}

                type={type}

                value={value}

                placeholder={placeholder}

                onChange={onChange}

                disabled={disabled}

                required={required}

                className={className}

            />




            {
                error && (

                    <span className="input-error">

                        {error}

                    </span>

                )
            }



        </div>

    );

};


export default Input;