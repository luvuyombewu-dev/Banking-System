import { useCallback, useEffect, useState } from "react";


const useFetch = (fetchFn, dependencies = []) => {


    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);



    const execute = useCallback(async () => {


        setLoading(true);
        setError(null);


        try {

            const result = await fetchFn();

            setData(result);

            return result;

        } catch (err) {

            setError(err);

            throw err;

        } finally {

            setLoading(false);

        }


    }, [fetchFn]);





    useEffect(() => {

        execute();

    }, [execute, ...dependencies]);




    return {
        data,
        loading,
        error,
        refetch: execute
    };


};


export default useFetch;