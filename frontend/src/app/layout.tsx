import type { Metadata } from "next";
import { Source_Sans_3 } from "next/font/google";

import "@/styles/globals.css";

import TheHeader from "@/components/layout/TheHeader";
import Footer from "@/components/layout/Footer";
import { getSession } from "@/lib/auth";

const source_sans_3 = Source_Sans_3({
	weight: ["400", "600", "700"],
	style: "normal",
	subsets: ["latin", "cyrillic"],
	variable: "--font-source-sans-3"
});

export const metadata: Metadata = {
	title:
		"Professional Web-Development Services At Affordable Price For Your Business",
	description:
		"Professional website development for your business with an expert web developer including web design with Figma"
};

export default async function RootLayout({
	children,
}: Readonly<{
	children: React.ReactNode;
}>) {
 const session = await getSession();
	return (
		<html
			lang="en"
			className={`${source_sans_3.variable} font-sans`}
		>
			<body className="min-w-[360px] w-full min-h-screen flex flex-col text-main text-base">
				<div id="overlay" />
				<div id="backdrop" />
				<TheHeader />
				{/* {session?<TheHeader />:null} */}
				<main className="container flex flex-col flex-grow justify-start items-center">
					{children}
				</main>
				{/*<Footer />*/}
				{/* {session?<Footer />:null} */}
			</body>
		</html>
	);
}
