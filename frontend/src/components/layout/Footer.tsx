import React from "react";
import styles from './Footer.module.css';
// import "./Footer.css";
// import "../../styles/Footer.css";

const Footer: React.FC = () => {

    return (
        <div className={styles['footer']}>
             <div className={styles['footer_section_padding']}>
                 <div className="footer_row">
                     <div className="footer_column">
                         <h4>More info:</h4>
                    </div>
                    <div className="footer_column">
                         <a className="footer_link" href="/about_us">
                             <p>About us</p>
                         </a>
                    </div>
                    <div className="footer_column">
                         <a className="footer_link" href="/contact_us">
                             <p>Contact us</p>
                         </a>
                     </div>
                  </div>
                  <hr />
                  <div className="footer_row">
                    <p className="footer_small_column">
                        &copy;{new Date().getFullYear()} Logistic Company | All rights reserved | Terms Of Service | Privacy
                    </p>
                  </div>
             </div>
        </div>

    )
}

export default Footer;