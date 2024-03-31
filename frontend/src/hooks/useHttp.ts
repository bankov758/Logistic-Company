import {useCallback, useState} from "react";

import axiosInstance, {ErrorType} from "@/lib/axios";
import {AxiosError, AxiosResponse} from "axios";

type ConfigOptions = {
    url: string;
    method?: 'get' | 'post' | 'put' | 'delete';
    data?: any
}

const useHttp = () => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [error, setError] = useState<AxiosError | ErrorType | null>(null);

    const sendRequest = useCallback(async ({
       url,
       method = 'get',
       data
    }: ConfigOptions) => {
        // reset everything initially
        setIsLoading(true);
        setError(null);

        try {
            const response: AxiosResponse<any> = await axiosInstance({ url, method, data });

            if( response ) {
                setIsLoading(false);
                return response;
            }

        } catch (_err) {
            const error = _err as ErrorType | AxiosError;
            setError(error);
        }

        return null;
    }, [])

    return {
        isLoading,
        error,
        sendRequest
    }

};

export default useHttp;