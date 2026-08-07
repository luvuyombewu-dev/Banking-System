const SectionCard = ({
    title,
    children,
    actions
}) => {


    return (

        <div className="section-card">


            <div className="section-card-header">


                <h2 className="section-card-title">

                    {title}

                </h2>


                {
                    actions && (

                        <div className="section-card-actions">

                            {actions}

                        </div>

                    )
                }


            </div>



            <div className="section-card-content">

                {children}

            </div>


        </div>

    );

};


export default SectionCard;