import React from "react";


const Avatar = ({
    name = "User",
    image,
    size = "medium",
}) => {


    const initials = name
        .trim()
        .split(" ")
        .filter(Boolean)
        .map(word => word[0])
        .join("")
        .substring(0, 2)
        .toUpperCase();



    return (

        <div
            className={`avatar avatar-${size}`}
        >

            {
                image ? (

                    <img
                        src={image}
                        alt={name}
                    />

                ) : (

                    <span>

                        {initials}

                    </span>

                )
            }


        </div>

    );

};


export default Avatar;