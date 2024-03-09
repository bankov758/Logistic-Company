'use client';

import React, { Fragment, ReactNode, useEffect, useState } from "react";
import { createPortal } from "react-dom";
import Image from "next/image";
import { StaticImport } from "next/dist/shared/lib/get-img-props";

type emoji = {
    enabled?: boolean;
    src?: string | StaticImport;
    width?: number;
    height?: number;
}

type ModalProps = {
    children: ReactNode | string;
    title: string;
    fixed?: boolean;
    titleColor?: string;
    emoji?: emoji;
    tryClose?: () => void
}

type backdropProps = {
    tryClose?: () => void;
}

const Backdrop: React.FC<backdropProps> = ({
   tryClose,
}): React.ReactNode | null => {

    return (
			<div
				onClick={tryClose}
				className={`
                    fixed top-0 left-0 w-screen h-screen bg-[rgba(0,_0,_0,_0.5)] z-10
                    ${tryClose ? 'cursor-pointer' : 'cursor-default'}
                `}
			/>
		);
};

const Modal: React.FC<ModalProps> = ({
    children,
    title,
    fixed,
    titleColor,
    emoji,
    tryClose
}): React.ReactNode | null => {
    return (
			<Fragment>
				<dialog
					open
					className="
                        animate-showContent
                        flex flex-col justify-center items-center
                        fixed top-[10vh] left-[10%] w-[80%]
                        m-0 p-0
                        border-0 rounded-[24px] bg-white shadow-xl z-100 overflow-hidden
                    "
				>
					{!fixed && tryClose && (
						<menu className="absolute top-0 right-0 p-2">
							<a
								onClick={tryClose}
								className="
                                    relative block
                                    w-[32px] h-[32px] opacity-30 cursor-pointer
                                    hover:opacity-100
                                    after:absolute after:left-[15px] after:w-[2px] after:h-[33px] after:bg-[#333]
                                    after:-rotate-45
                                    before:absolute before:left-[15px] before:w-[2px] before:h-[33px] before:bg-[#333]
                                    before:rotate-45
                                "
							/>
						</menu>
					)}
					{emoji && emoji.enabled && emoji.src && (
						<div className="flex justify-center items-center w-full p-4 bg-green-400">
							<Image
								src={emoji.src}
								width={emoji.width ?? 160}
								height={emoji.height ?? 160}
								placeholder="blur"
								alt="Emoji Pic"
							/>
						</div>
					)}
					<header
						className={`w-full text-center pt-8 pb-2 px-4 ${titleColor ?? "bg-green-500"}`}
					>
						<h2 className="text-white text-lg lg:text-xl font-bold">{title}</h2>
					</header>
					<section className="w-full p-4">{children}</section>
				</dialog>
			</Fragment>
		);
};

const BaseDialog: React.FC<ModalProps> = ({
    children,
    title,
    fixed = true,
    titleColor,
    emoji,
    tryClose
}): React.ReactNode => {
    const [mounted, setMounted] = useState<boolean>(false);

    useEffect(() => {
        setMounted(true);

        //cleanup function
        return () => setMounted(false);
    },[]);

    let backdrop;
    let overlay;

    if( typeof document !== "undefined" ) {
        backdrop = document.querySelector("#backdrop");
        overlay = document.querySelector("#overlay");
    }

    return mounted && backdrop && overlay ? (
        <Fragment>
            {createPortal(
                <Backdrop
                    tryClose={tryClose}
                />,
                backdrop
            )}
            {createPortal(
                <Modal
                    title={title}
                    fixed={fixed}
                    emoji={emoji}
                    titleColor={titleColor}
                    tryClose={tryClose}
                >
                    <div style={{ maxHeight: "70vh", overflowY: "auto" }}>
                        {children}
                    </div>
                </Modal>,
                overlay
            )}
        </Fragment>
    ) : null;
};

export default BaseDialog;