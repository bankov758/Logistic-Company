import React from "react";
<<<<<<< HEAD
import './Footer.css';
// import "./Footer.css";
=======
import "./Footer.css";
>>>>>>> b629e6f7407b22643728e3d908204e90f31d2590
// import "../../styles/Footer.css";

const Footer: React.FC = () => {

    return (
<<<<<<< HEAD
        <div className='footer'>
             <div className='footer_section_padding'>
=======
        <div className={'footer'}>
             <div className={'footer_section_padding'}>
>>>>>>> b629e6f7407b22643728e3d908204e90f31d2590
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