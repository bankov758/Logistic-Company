import React from "react";
import type {Metadata} from "next";
import {Source_Sans_3} from "next/font/google";
import "@/styles/globals.css";

const source_sans_3 = Source_Sans_3({
    weight: ["400", "600", "700"],
    style: "normal",
    subsets: ["latin", "cyrillic"],
    variable: "--font-source-sans-3"
});

export const metadata: Metadata = {
    title:
        "Logistic Company",
    description:
        "Logistic company"
};

export default async function RootLayout({
	children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    return (
        <html
            lang="en"
            className={`${source_sans_3.variable} font-sans`}
        >
			<body className="min-w-[360px] w-full min-h-screen flex flex-col text-main text-base">
				<div id="overlay"/>
				<div id="backdrop"/>
				<main className="container flex flex-col flex-grow justify-start items-center gap-y-4 w-full my-6">
					{children}
				</main>
			</body>
        </html>
    );
}
