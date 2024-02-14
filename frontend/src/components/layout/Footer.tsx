import React from "react";
import Link from "next/link";

import "./Footer.css";

const Footer: React.FC = () => {
	return (
		<div className="footer">
			<div className="footer_section_padding">
				<div className="footer_row">
					<div className="footer_column">
						<h4>More info:</h4>
					</div>
					<div className="footer_column">
						<Link className="footer_link" href="/about_us">
							<p>About us</p>
						</Link>
					</div>
					<div className="footer_column">
						<Link className="footer_link" href="/contact_us">
							<p>Contact us</p>
						</Link>
					</div>
				</div>
				<hr />
				<div className="footer_row">
					<p className="footer_small_column">
						&copy;{new Date().getFullYear()} Logistic Company | All rights
						reserved | Terms Of Service | Privacy
					</p>
				</div>
			</div>
		</div>
	);
};

export default Footer;