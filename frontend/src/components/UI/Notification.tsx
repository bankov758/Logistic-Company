"use client";

import React, {Fragment, ReactNode, useEffect, useState} from "react";
import {createPortal} from "react-dom";

type notificationProps = {
    status: string;
    timeout?: number;
    children: ReactNode | string;
};

const NotificationTemplate: React.FC<notificationProps> = ({
   status,
   timeout,
   children
}): ReactNode | null => {
    const [show, setShow] = useState<boolean>(true);

    useEffect(() => {
        const timeoutID = setTimeout(() => {
            setShow(false);
        }, timeout);

        return () => clearTimeout(timeoutID);
    }, [timeout]);

    if (!show) {
        return null;
    }

    return (
        <label htmlFor="popup" className="relative">
            <input type="checkbox" id="popup" className='absolute opacity-0 w-0 h-0' onChange={(e: any) => {
                e.preventDefault();
                setShow(false);
            }}/>
            <div className={`
					${status === 'success' ? 'bg-green-600' : 'bg-red-600'}
					fixed top-8 right-1/2 translate-x-1/2 min-w-[250px] p-8 rounded-xl z-100 cursor-pointer text-center
				`}
            >
				<span className='text-base text-white font-bold'>
					{children}
				</span>
            </div>
        </label>
    );
};

const Notification: React.FC<notificationProps> = ({
   status,
   timeout = 5000,
   children
}): ReactNode | null => {
    const [mounted, setMounted] = useState<boolean>(false);

    useEffect(() => {
        //since useEffect is called only on the client side and only when the component is mounted
        setMounted(true);

        //cleanup function
        return () => setMounted(false);
    }, []);

    let overlay;

    if (typeof document !== "undefined") {
        overlay = document.querySelector("#overlay");
    }

    return mounted && overlay ? (
        <Fragment>
            {createPortal(
                <NotificationTemplate status={status} timeout={timeout}>
                    {children}
                </NotificationTemplate>,
                overlay
            )}
        </Fragment>
    ) : null;
};

export default Notification;